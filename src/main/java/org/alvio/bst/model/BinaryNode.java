package org.alvio.bst.model;

public class BinaryNode<T extends Comparable<T>> {
    private T value;
    private BinaryNode<T> left;
    private BinaryNode<T> right;

    public BinaryNode(T value) {
        this(value, null, null);
    }

    public BinaryNode(T value, BinaryNode<T> left, BinaryNode<T> right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }
	
	public T getValue() { return this.value; }
    public void setValue(T value) { this.value = value; }

    public BinaryNode<T> getLeft() { return this.left; }
    public void setLeft(BinaryNode<T> left) { this.left = left; }

    public BinaryNode<T> getRight() { return this.right; }
    public void setRight(BinaryNode<T> right) { this.right = right; }

    public String toJson() {
        return String.format(
                "{\"value\":%s,\"left\":%s,\"right\":%s}",
                toJsonValue(value),  // Handles both strings and numbers
                left != null ? left.toJson() : "null",
                right != null ? right.toJson() : "null"
        );
    }

    // format values correctly
    private String toJsonValue(T value) {
        if (value instanceof String) {
            return "\"" + escapeJson((String) value) + "\"";
        }
        return value.toString();
    }

    private String escapeJson(String input) {
        return input.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n");
    }
}