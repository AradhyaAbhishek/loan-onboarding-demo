package com.myloanprocess.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanCreatedEvent {

	private String requestId;
	private String customerName;
	private double amount;
	private String status;

}
