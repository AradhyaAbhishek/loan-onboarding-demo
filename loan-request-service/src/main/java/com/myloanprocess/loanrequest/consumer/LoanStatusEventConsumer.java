package com.myloanprocess.loanrequest.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myloanprocess.common.constants.KafkaTopics;
import com.myloanprocess.common.event.EventEnvelope;
import com.myloanprocess.common.event.LoanStatusUpdatedEvent;
import com.myloanprocess.loanrequest.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoanStatusEventConsumer {

	private final LoanService loanService;
	private final ObjectMapper objectMapper;

	@KafkaListener(topics = KafkaTopics.LOAN_STATUS_UPDATED, groupId = "loan-group")
	public void consume(String message) {
		log.info("Received Loan Status Event message: {}", message);

		try {
			// Manually deserialize the string message to EventEnvelope
			EventEnvelope<Object> event = objectMapper.readValue(message, new TypeReference<EventEnvelope<Object>>() {});

			if (event == null || event.getPayload() == null) {
				log.warn("Received null event or payload. Skipping consumption.");
				return;
			}

			Object payload = event.getPayload();
			LoanStatusUpdatedEvent statusEvent;

			log.debug("Attempting to deserialize payload: {}", payload);
			if (payload instanceof Map) {
				statusEvent = objectMapper.convertValue(payload, LoanStatusUpdatedEvent.class);
			} else if (payload instanceof LoanStatusUpdatedEvent) {
				statusEvent = (LoanStatusUpdatedEvent) payload;
			} else {
				statusEvent = objectMapper.convertValue(payload, LoanStatusUpdatedEvent.class);
			}
			log.debug("Payload deserialized successfully to: {}", statusEvent);

			log.info("Calling updateLoanStatus for requestId: {}", statusEvent.getRequestId());
			loanService.updateLoanStatus(statusEvent, false);
			log.info("Successfully processed status update for requestId: {}", statusEvent.getRequestId());

		} catch (Exception e) {
			log.error("Caught exception while processing loan status event. Message: {}", message, e);
		}
	}
}
