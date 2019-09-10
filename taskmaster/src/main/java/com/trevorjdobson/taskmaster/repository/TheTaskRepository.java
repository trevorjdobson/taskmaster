package com.trevorjdobson.taskmaster.repository;


import com.trevorjdobson.taskmaster.models.Task;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableScan
public interface TheTaskRepository extends CrudRepository<Task, String> {
    Optional<Task> findById(String id);
}

