package de.alxgrk;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import de.alxgrk.model.Node;
import de.alxgrk.model.Tree;
import de.alxgrk.model.Triple;

public class AhoBuild {

    public static Tree build(final Set<Triple> tripleSetR, List<Node> leaveSetL) {

        // if there is only one leave, return a tree containing only this leave
        if (leaveSetL.size() == 1)
            return new Tree(leaveSetL);

        // split tree and determine connected components
        List<Tree> connectedComponents = ConnectedComponents.construct(tripleSetR, leaveSetL);

        // exit if tree is no phylogenetic one
        if (connectedComponents.size() == 1)
            throw new RuntimeException("no phylogenetic tree");

        // create invisible root node
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

    private static Set<Triple> filter(List<Node> subLeaveSet, Set<Triple> tripleSetR) {
        return tripleSetR.stream()
                .filter(t -> subLeaveSet.contains(t.getEdge().getFirst())
                        && subLeaveSet.contains(t.getEdge().getSecond())
                        && subLeaveSet.contains(t.getNode()))
                .collect(Collectors.toSet());
    }

}
