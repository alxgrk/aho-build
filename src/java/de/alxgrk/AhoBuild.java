package de.alxgrk;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AhoBuild {

    public static Tree build(Set<Triple> tripleSetR, List<Node> leaveSetL) {

        // if there is only one leave, return a tree containing only this leave
        if (leaveSetL.size() == 1)
            return new Tree(leaveSetL);

        // split tree and determine connected components
        List<Tree> connectedComponents = construct(tripleSetR, leaveSetL);

        if (connectedComponents.size() == 1)
            throw new RuntimeException("no phylogenetic tree");

        List<Node> root = new ArrayList<>();
        root.add(Node.invisible());

        return connectedComponents.stream()
                .map(component -> {

                    List<Node> subLeaveSet = component.getNodes();

                    Set<Triple> subTripleSet = filter(subLeaveSet, tripleSetR);

                    Tree subTree = build(subTripleSet, subLeaveSet);

                    return subTree;
                })
                .reduce(new Tree(root), (i, t) -> {
                    Tree newTree = i.addSubTree(t);
                    return newTree;
                });
    }

    private static List<Tree> construct(Set<Triple> tripleSetR, List<Node> leaveSetL) {
        List<Tree> subTrees = new ArrayList<>();

        List<Node> leftOvers = leaveSetL;
        while (!leftOvers.isEmpty()) {
            leftOvers = findSubTree(tripleSetR, leftOvers, subTrees);
        }

        return subTrees;
    }

    private static List<Node> findSubTree(Set<Triple> tripleSetR, List<Node> leaveSetL,
            List<Tree> subTrees) {
        List<Node> subTree = new ArrayList<>();
        for (Node leave : leaveSetL) {
            if (subTree.isEmpty()) {
                subTree.add(leave);
            } else {
                for (Triple t : tripleSetR) {
                    Node first = t.getEdge().getFirst();
                    Node second = t.getEdge().getSecond();

                    boolean egdeForLeftNode = first.equals(leave)
                            && subTree.contains(second);
                    boolean egdeForRightNode = second.equals(leave)
                            && subTree.contains(first);

                    if (egdeForLeftNode && !subTree.contains(first))
                        subTree.add(first);
                    if (egdeForRightNode && !subTree.contains(second))
                        subTree.add(second);
                }
            }
        }

        subTrees.add(new Tree(subTree));

        return leaveSetL.stream()
                .filter(n -> !subTree.contains(n))
                .collect(Collectors.toList());
    }

    private static Set<Triple> filter(List<Node> subLeaveSet, Set<Triple> tripleSetR) {
        return tripleSetR.stream()
                .filter(t -> subLeaveSet.contains(t.getEdge().getFirst())
                        && subLeaveSet.contains(t.getEdge().getSecond())
                        && subLeaveSet.contains(t.getNode()))
                .collect(Collectors.toSet());
    }

}
