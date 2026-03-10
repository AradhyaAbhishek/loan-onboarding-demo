package com.myloasprocess.taskservice.repository;

import com.myloasprocess.taskservice.entity.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task,String> {
	List<Task> findByLoanRequestId(String loanRequestId);
	List<Task> findByStatus(String status);
//	List<Task> findByAssignee(String assignee);
}
