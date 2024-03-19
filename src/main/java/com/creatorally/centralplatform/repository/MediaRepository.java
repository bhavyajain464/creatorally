package com.creatorally.centralplatform.repository;

import com.creatorally.centralplatform.models.entities.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MediaRepository extends JpaRepository<Media, Integer> {

    @Query("select m from Media m where m.editorId = :editorId")
    List<Media> getAllMediaByEditor(Integer editorId);

    @Query("select m from Media m where m.creatorId = :creatorId")
    List<Media> getAllMediaByInfluencer(Integer creatorId);


    Optional<Media> getMediaById(Integer mediaId);
}
