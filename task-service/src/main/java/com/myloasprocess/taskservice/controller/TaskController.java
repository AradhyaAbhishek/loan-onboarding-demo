package com.myloasprocess.taskservice.controller;

import com.myloasprocess.taskservice.entity.Task;
import com.myloasprocess.taskservice.service.TaskService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

	private final TaskService taskService;

	@PostMapping("/create")
	public Task createTask(@RequestParam String loanRequestId,
			@RequestParam String description,
			@RequestParam String assignee) {
		return taskService.createTask(loanRequestId, description, assignee);
	}

	@PutMapping("/{taskId}/status")
	public Task updateStatus(@PathVariable String taskId,
			@RequestParam String status) {
		return taskService.updateTaskStatus(taskId, status);
	}

	@GetMapping("/loan/{loanRequestId}")
	public List<Task> getTasksByLoanRequestId(@PathVariable String loanRequestId) {
		return taskService.getTasksByLoanRequestId(loanRequestId);
	}

	@GetMapping("/status/{status}")
	public List<Task> getTasksByStatus(@PathVariable String status) {
		return taskService.getTasksByStatus(status);
	}

	@GetMapping("/assignee/{assignee}")
	public List<Task> getTasksByAssignee(@PathVariable String assignee) {
		return taskService.getTasksByAssignee(assignee);
	}
}

