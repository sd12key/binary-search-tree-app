package org.alvio.bst.repository;

import org.alvio.bst.model.TreeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreeRepository extends JpaRepository<TreeEntity, Long> {
    // all trees sorted by creation date (newest first)
    List<TreeEntity> findAllByOrderByCreatedAtDesc();
}