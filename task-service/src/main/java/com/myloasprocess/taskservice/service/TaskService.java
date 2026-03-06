package com.myloasprocess.taskservice.service;




import com.myloasprocess.taskservice.entity.Task;
import com.myloasprocess.taskservice.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

	private final TaskRepository taskRepository;

	public Task createTask(String loanRequestId, String description, String assignee) {
		Task task = Task.builder()
				.taskId(UUID.randomUUID().toString())
				.loanRequestId(loanRequestId)
				.description(description)
				.assignee(assignee)
				.status("PENDING")
				.createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.build();
		return taskRepository.save(task);
	}

	public Task updateTaskStatus(String taskId, String status) {
		Optional<Task> taskOptional = taskRepository.findById(taskId);
		if (taskOptional.isPresent()) {
			Task task = taskOptional.get();
			task.setStatus(status);
			task.setUpdatedAt(LocalDateTime.now());
			return taskRepository.save(task);
		}
		throw new RuntimeException("Task not found with id: " + taskId);
	}

	public List<Task> getTasksByLoanRequestId(String loanRequestId) {
		return taskRepository.findByLoanRequestId(loanRequestId);
	}

	public List<Task> getTasksByStatus(String status) {
		return taskRepository.findByStatus(status);
	}

	public List<Task> getTasksByAssignee(String assignee) {
		return taskRepository.findByAssignee(assignee);
	}
}

