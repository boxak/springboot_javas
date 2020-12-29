package com.exercise.javas.repository;

import com.exercise.javas.config.MongoDBConfig;
import com.exercise.javas.dto.UserDTO;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Import(MongoDBConfig.class)
public interface UserRepository extends MongoRepository<UserDTO, String> {
    long countAllByUserType(String userType);

    List<UserDTO> deleteAllByUserType(String userType);
}
