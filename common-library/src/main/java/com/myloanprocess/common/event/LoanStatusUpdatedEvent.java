package com.myloanprocess.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanStatusUpdatedEvent {

	private String requestId;
	private String status;
	private String updatedBy;
}
