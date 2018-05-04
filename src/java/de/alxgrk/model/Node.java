package de.alxgrk.model;

import lombok.Value;

@Value
public class Node {

    int count;

    boolean visible;

    public static Node invisible() {
        return new Node(-1);
    }

    public static Node of(int count) {
        if (count < 0)
            throw new IllegalArgumentException("Node value must be >= 0");

        return new Node(count);
    }

    private Node(int count) {
        this.count = count;
        this.visible = count >= 0;
    }

    @Override
    public String toString() {
        if (!visible)
            return "";

        return Integer.toString(count);
    }

}
