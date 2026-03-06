package com.myloanprocess.common.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanRequestEvent {
	private String requestId;
	private String customerName;
	private double amount;
	private String status; // e.g. "PENDING_VALIDATION", "APPROVED", "REJECTED"
}

