package com.myloasprocess.taskservice.repository;

import com.myloasprocess.taskservice.entity.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task,String> {
	List<Task> findByLoanRequestId(String loanRequestId);
	List<Task> findByStatus(String status);
	List<Task> findByAssignee(String assignee);
}
