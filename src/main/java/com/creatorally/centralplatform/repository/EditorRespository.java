package com.creatorally.centralplatform.repository;

import com.creatorally.centralplatform.models.entities.Editor;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.events.Event;

import java.util.Optional;

@Repository
public interface EditorRespository extends JpaRepository<Editor, Integer> {
    Optional<Editor> findById(int id);

}
