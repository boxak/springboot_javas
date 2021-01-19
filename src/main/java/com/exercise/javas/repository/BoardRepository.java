package com.exercise.javas.repository;

import com.exercise.javas.config.MongoDBConfig;
import com.exercise.javas.dto.BoardDTO;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Import(MongoDBConfig.class)
public interface BoardRepository extends MongoRepository<BoardDTO, String> {

    List<BoardDTO> findAllByBoardType(String boardType, Pageable pageable);

    @Query("{$and : [{boardType : ?0},{$or : [{title:{$regex:?1}},{content:{$regex:?1}}]}]}")
    List<BoardDTO> findAllByBoardTypeAndTitleContainsOrContentContains(String boardType, String key, Pageable pageable);

    List<BoardDTO> findAllByBoardTypeAndTitleContains(String boardType, String title, Pageable pageable);

    List<BoardDTO> findAllByBoardTypeAndContentContains(String boardType, String content, Pageable pageable);

    List<BoardDTO> findAllByBoardTypeAndUserId(String boardType, String userId, Pageable pageable);

    long countAllByBoardType(String boardType);

    @Query(value = "{$and : [{boardType : ?0},{$or : [{title:{$regex:?1}},{content:{$regex:?1}}]}]}", count = true)
    long countAllByBoardTypeAndTitleContainsOrContentContains(String boardType, String key);

    long countAllByBoardTypeAndTitleContains(String boardType, String title);

    long countAllByBoardTypeAndContentContains(String boardType, String content);

    long countAllByBoardTypeAndUserId(String boardType, String userId);
}
