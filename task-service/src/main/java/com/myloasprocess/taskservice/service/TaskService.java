package com.myloasprocess.taskservice.service;




import com.myloanprocess.common.event.LoanRequestEvent;
import com.myloanprocess.common.event.LoanStatusUpdatedEvent;
import com.myloasprocess.taskservice.entity.Task;
import com.myloasprocess.taskservice.producer.LoanStatusEventProducer;
import com.myloasprocess.taskservice.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class TaskService {

	private final TaskRepository taskRepository;
	private final LoanStatusEventProducer loanStatusEventProducer;

	public Task createTask(LoanRequestEvent event) {
		log.debug("Inside Create task service ");
		Task task = Task.builder()
				.taskId(UUID.randomUUID().toString())
				.loanRequestId(event.getRequestId())
				.customerName(event.getCustomerName())
				.lonaAmount(event.getAmount())
				.status("PENDING")
				.createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.build();
		log.info("Persisting task to db {}",task);
		return taskRepository.save(task);
	}
	public Task updateTaskStatus(String taskId, String status) {
		Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> new RuntimeException("Task not found"));

		task.setStatus(status);
		task.setUpdatedAt(LocalDateTime.now());

		Task updatedTask = taskRepository.save(task);

		LoanStatusUpdatedEvent event = LoanStatusUpdatedEvent.builder()
				.requestId(task.getLoanRequestId())
				.status(status)
				.updatedBy("loan-officer")
				.build();

		loanStatusEventProducer.sendStatusUpdate(event);

		return updatedTask;
	}
	public List<Task> getTasksByLoanRequestId(String loanRequestId) {
		return taskRepository.findByLoanRequestId(loanRequestId);
	}
	public List<Task> getTasksByStatus(String status) {
		return taskRepository.findByStatus(status);
	}
}