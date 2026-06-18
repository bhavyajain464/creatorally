package com.creatorally.centralplatform.repository;

import com.creatorally.centralplatform.models.entities.Influencer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfluencerRepository extends JpaRepository<Influencer, Integer>{

    Optional<Influencer> findById(Integer id);

}
