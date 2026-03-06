package com.myloasprocess.taskservice.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "loan-task")
public class Task {
	@Id
	private String taskId;
	private String loanRequestId;
	private String status; // e.g. PENDING, APPROVED, REJECTED
	private String description;
	private String assignee;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
