package org.alvio.bst.model;

import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable<T>> {
    private BinaryNode<T> root;

    public BinarySearchTree() {
        this.root = null;
    }

    private BinaryNode<T> insert(BinaryNode<T> currentNode, T value) {
        if (currentNode == null) {
            return new BinaryNode<>(value);
        } else if (value.compareTo(currentNode.getValue()) <= 0) {
            currentNode.setLeft(insert(currentNode.getLeft(), value));
        } else {
            currentNode.setRight(insert(currentNode.getRight(), value));
        }
        return currentNode;
    }

    public void insert(T value) {
        this.root = insert(this.root, value);
    }

    public void preOrder(BinaryNode<T> node) {
        if (node == null) return;
        System.out.print(node.getValue() + " ");
        preOrder(node.getLeft());
        preOrder(node.getRight());
    }

    public void inOrder(BinaryNode<T> node) {
        if (node == null) return;
        inOrder(node.getLeft());
        System.out.print(node.getValue() + " ");
        inOrder(node.getRight());
    }

    public void postOrder(BinaryNode<T> node) {
        if (node == null) return;
        postOrder(node.getLeft());
        postOrder(node.getRight());
        System.out.print(node.getValue() + " ");
    }

    public void levelOrder() {
        if (root == null) return;
        Queue<BinaryNode<T>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            BinaryNode<T> current = queue.remove();
            System.out.print(current.getValue() + " ");
            if (current.getLeft() != null) queue.add(current.getLeft());
            if (current.getRight() != null) queue.add(current.getRight());
        }
    }

    public BinaryNode<T> search(BinaryNode<T> node, T value) {
        if (node == null) {
            System.out.println("Value: " + value + " not found in BST");
            return null;
        } else if (value.compareTo(node.getValue()) == 0) {
            System.out.println("Value: " + value + " found in BST");
            return node;
        } else if (value.compareTo(node.getValue()) < 0) {
            return search(node.getLeft(), value);
        } else {
            return search(node.getRight(), value);
        }
    }

    private BinaryNode<T> minimumNode(BinaryNode<T> node) {
        return (node.getLeft() == null) ? node : minimumNode(node.getLeft());
    }

    public BinaryNode<T> deleteNode(BinaryNode<T> node, T value) {
        if (node == null) {
            System.out.println("Value not found in BST");
            return null;
        }

        if (value.compareTo(node.getValue()) < 0) {
            node.setLeft(deleteNode(node.getLeft(), value));
        } else if (value.compareTo(node.getValue()) > 0) {
            node.setRight(deleteNode(node.getRight(), value));
        } else {
            if (node.getLeft() != null && node.getRight() != null) {
                BinaryNode<T> minNode = minimumNode(node.getRight());
                node.setValue(minNode.getValue());
                node.setRight(deleteNode(node.getRight(), minNode.getValue()));
            } else if (node.getLeft() != null) {
                node = node.getLeft();
            } else if (node.getRight() != null) {
                node = node.getRight();
            } else {
                node = null;
            }
        }

        return node;
    }

    public void deleteBST() {
        this.root = null;
        System.out.println("BST has been deleted successfully");
    }

    public BinaryNode<T> getRoot() {
        return this.root;
    }

    public String toJson() {
        return root != null ?
                String.format("{\"root\":%s}", root.toJson()) :
                "null";
    }


}
