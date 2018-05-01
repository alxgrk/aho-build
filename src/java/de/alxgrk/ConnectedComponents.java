package de.alxgrk;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import de.alxgrk.model.Node;
import de.alxgrk.model.Tree;
import de.alxgrk.model.Triple;

public class ConnectedComponents {

    private ConnectedComponents() {
        // not supposed to be instantiated
    }

    public static List<Tree> construct(Set<Triple> tripleSetR, List<Node> leaveSetL) {
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

}
