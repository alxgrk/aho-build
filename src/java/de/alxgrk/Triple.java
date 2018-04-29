package de.alxgrk;

public class Triple {

    private final Edge edge;

    private final Node node;

    public Triple(Edge e, Node v) {
        this.edge = e;
        this.node = v;
    }

    public Edge getEdge() {
        return edge;
    }

    public Node getNode() {
        return node;
    }

    // BOILERPLATE

    @Override
    public String toString() {
        return "(" + edge + "|" + node + ")";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((edge == null) ? 0 : edge.hashCode());
        result = prime * result + ((node == null) ? 0 : node.hashCode());
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
        Triple other = (Triple) obj;
        if (edge == null) {
            if (other.edge != null)
                return false;
        } else if (!edge.equals(other.edge))
            return false;
        if (node == null) {
            if (other.node != null)
                return false;
        } else if (!node.equals(other.node))
            return false;
        return true;
    }

}
