package org.alvio.bst.service;

import org.alvio.bst.repository.TreeRepository;
import org.antlr.v4.runtime.tree.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.alvio.bst.model.TreeEntity;
import org.alvio.bst.model.BinarySearchTree;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TreeService {
    @Autowired
    private TreeRepository treeRepository;

    public TreeEntity buildAndSaveTree(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return null;
        }

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        numbers.forEach(bst::insert);

        TreeEntity entity = new TreeEntity();

        entity.setInputNumbers(numbers.toString());
        entity.setTreeJson(bst.toJson());
        return treeRepository.save(entity);

    }

    public TreeEntity buildAndSaveBalancedTree(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return null;
        }

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        // sorting and inserting numbers in a balanced way
        List<Integer> sortedNumbers = numbers.stream()
                .sorted()
                .collect(Collectors.toList());
        insertMiddle(sortedNumbers, 0, sortedNumbers.size() - 1, bst);

        TreeEntity entity = new TreeEntity();
        entity.setInputNumbers(numbers.toString());
        entity.setTreeJson(bst.toJson());
        entity.setBalanced(true);
        return treeRepository.save(entity);
    }

    private void insertMiddle(List<Integer> sortedNumbers, int start, int end,
                              BinarySearchTree<Integer> bst) {
        if (start > end) return;

        int mid = (start + end) / 2;
        bst.insert(sortedNumbers.get(mid));

        insertMiddle(sortedNumbers, start, mid - 1, bst);
        insertMiddle(sortedNumbers, mid + 1, end, bst);
    }

    public List<TreeEntity> getAllTrees() {
        return treeRepository.findAllByOrderByCreatedAtDesc();
    }

    public void deleteAllTrees() {
        treeRepository.deleteAll();
    }
}