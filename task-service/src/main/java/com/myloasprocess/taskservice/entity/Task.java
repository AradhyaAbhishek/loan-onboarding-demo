package com.myloasprocess.taskservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "loan-task")
public class Task {
	@Id
	private String taskId;
	private String loanRequestId;
	private String status; // e.g. PENDING, APPROVED, REJECTED
	private double lonaAmount;
	private String customerName;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
