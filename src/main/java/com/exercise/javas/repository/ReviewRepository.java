package com.exercise.javas.repository;

import com.exercise.javas.config.MongoDBConfig;
import com.exercise.javas.dto.BoardDTO;
import com.exercise.javas.dto.ReviewDTO;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Import(MongoDBConfig.class)
public interface ReviewRepository extends MongoRepository<ReviewDTO, String> {
    List<ReviewDTO> findAllByPostId(String postId);

    List<ReviewDTO> findAllByTargetId(String targetId);
}
