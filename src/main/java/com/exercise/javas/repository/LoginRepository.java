package com.exercise.javas.repository;

import com.exercise.javas.config.MongoDBConfig;
import com.exercise.javas.dto.UserDTO;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Import(MongoDBConfig.class)
public interface LoginRepository extends MongoRepository<UserDTO, String> {
    UserDTO findFirstByIdAndPassword(String id, String password);

}
