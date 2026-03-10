package com.myloanprocess.loanrequest.consumer;

import com.myloanprocess.common.event.LoanStatusUpdatedEvent;
import com.myloanprocess.loanrequest.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoanStatusEventConsumer {

	private final LoanService loanService;

	@KafkaListener(topics = "loan-status-topic", groupId = "loan-group")
	public void consume(LoanStatusUpdatedEvent event) {

		log.info("Received Loan Status Event {}", event);

		loanService.updateLoanStatus(event);
	}
}
