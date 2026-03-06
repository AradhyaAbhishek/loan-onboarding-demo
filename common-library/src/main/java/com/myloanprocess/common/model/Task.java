package com.myloanprocess.common.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
	private String id;
	private String requestId;    // link back to LoanRequest
	private String taskType;     // e.g. "1LOD_ASSESSMENT"
	private TaskStatus status;
}

