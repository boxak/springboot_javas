package com.exercise.javas.repository;

import com.exercise.javas.config.MongoDBConfig;
import com.exercise.javas.dto.UserDTO;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Import(MongoDBConfig.class)
public interface FindRepository extends MongoRepository<UserDTO, String> {
    boolean existsByName(String name);

    boolean existsByNameAndPhone(String name,String phone);

    UserDTO findFirstByNameAndPhone(String name, String phone);

    UserDTO findFirstByIdAndNameAndEmail(String id, String name, String email);

    UserDTO findFirstByEmailAndPassword(String email, String password);
}
