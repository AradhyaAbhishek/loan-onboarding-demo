package com.myloasprocess.taskservice.controller;

import com.myloanprocess.common.event.LoanRequestEvent;
import com.myloasprocess.taskservice.entity.Task;
import com.myloasprocess.taskservice.service.TaskService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

	private final TaskService taskService;

	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@PostMapping("/create")
	public Task createTask(@RequestBody LoanRequestEvent event) {
		return taskService.createTask(event);
	}
	@GetMapping("/loan/{loanRequestId}")
	public List<Task> getTasksByLoanRequestId(@PathVariable String loanRequestId) {
		return taskService.getTasksByLoanRequestId(loanRequestId);
	}

	@PutMapping("/{taskId}/status")
	public Task updateStatus(@PathVariable String taskId,
			@RequestParam String status) {
		return taskService.updateTaskStatus(taskId, status);
	}

	@GetMapping("/status/{status}")
	public List<Task> getTasksByStatus(@PathVariable String status) {
		return taskService.getTasksByStatus(status);
	}

}

