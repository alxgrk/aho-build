package de.alxgrk.model;

public class Node {

    private final int count;

    private final boolean visible;

    public static Node invisible() {
        return new Node();
    }

    public static Node of(int count) {
        return new Node(count);
    }

    private Node(int count) {
        this.count = count;
        this.visible = true;
    }

    private Node() {
        this.count = -1;
        this.visible = false;
    }

    public int getCount() {
        return count;
    }

    public boolean isVisible() {
        return visible;
    }

    // BOILERPLATE

    @Override
    public String toString() {
        if (!visible)
            return "";

        return Integer.toString(count);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + count;
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
        Node other = (Node) obj;
        if (count != other.count)
            return false;
        return true;
    }

}
