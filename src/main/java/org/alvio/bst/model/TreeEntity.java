package org.alvio.bst.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bst_trees")
public class TreeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    // store the original input numbers as a comma-separated string
    private String inputNumbers;

    // store the JSON representation of the binary search tree
    @Column(columnDefinition = "TEXT", nullable = false)
    private String treeJson;

    @Column(nullable = false, updatable = false)
    private boolean isBalanced = false;

    // timestamp for when the tree was created
    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Constructors
    public TreeEntity() {}

    public TreeEntity(String inputNumbers, String treeJson) {
        this.inputNumbers = inputNumbers;
        this.treeJson = treeJson;
    }

    public Long getId() {
        return id;
    }

    public String getInputNumbers() {
        return inputNumbers;
    }

    public void setInputNumbers(String inputNumbers) {
        this.inputNumbers = inputNumbers;
    }

    public boolean isBalanced() {
        return this.isBalanced;
    }

    public void setBalanced(boolean balanced) {
        this.isBalanced = balanced;
    }

    public String getTreeJson() {
        return treeJson;
    }

    public void setTreeJson(String treeJson) {
        this.treeJson = treeJson;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}