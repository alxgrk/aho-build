package de.alxgrk.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tree {

    private final List<Tree> subTrees;

    private final List<Node> leafs;

    public Tree(List<Node> leafs) {
        if (leafs.size() < 1)
            throw new RuntimeException("Unable to create tree without any node.");

        this.leafs = leafs;
        this.subTrees = new ArrayList<>();
    }

    public Tree(Tree... subtrees) {
        if (subtrees.length < 1)
            throw new RuntimeException("Unable to create tree without any node.");

        this.leafs = new ArrayList<>();
        this.subTrees = Arrays.asList(subtrees);
    }

    public List<Node> getNodes() {
        List<Node> nodes = new ArrayList<>();

        nodes.addAll(leafs);

        subTrees.forEach(t -> {
            nodes.addAll(t.getNodes());
        });

        return nodes;
    }

    public Tree addSubTree(Tree t) {
        subTrees.add(t);
        return this;
    }

    public String toNewickNotation() {
        if (subTrees.isEmpty() && leafs.size() == 1)
            return leafs.get(0).toString();

        String nodes = leafs.stream()
                .filter(Node::isVisible)
                .map(Node::toString)
                .reduce("(", (u, n) -> u + n + ",");
        String result = subTrees.stream()
                .map(Tree::toNewickNotation)
                .reduce(nodes, (i, s) -> i + s + ",");

        return result
                .substring(0, result.length() - 1)
                .concat(")");
    }

    // BOILERPLATE

    @Override
    public String toString() {
        return toNewickNotation();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((leafs == null) ? 0 : leafs.hashCode());
        result = prime * result + ((subTrees == null) ? 0 : subTrees.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tree other = (Tree) obj;
        if (leafs == null) {
            if (other.leafs != null)
                return false;
        } else if (!leafs.equals(other.leafs))
            return false;
        if (subTrees == null) {
            if (other.subTrees != null)
                return false;
        } else if (!subTrees.equals(other.subTrees))
            return false;
        return true;
    }

}
