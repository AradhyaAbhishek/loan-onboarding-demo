package com.myloasprocess.taskservice.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myloanprocess.common.constants.KafkaTopics;
import com.myloanprocess.common.event.EventEnvelope;
import com.myloanprocess.common.event.LoanRequestEvent;
import com.myloanprocess.common.event.LoanStatusUpdatedEvent;
import com.myloasprocess.taskservice.service.TaskService;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Log4j2
@Component
public class LoanEventConsumer {

	private final TaskService taskService;
	private final ObjectMapper objectMapper;

	public LoanEventConsumer(TaskService taskService, ObjectMapper objectMapper) {
		this.taskService = taskService;
		this.objectMapper = objectMapper;
	}

	@KafkaListener(topics = KafkaTopics.LOAN_CREATED, groupId = "task-group")
	public void consume(EventEnvelope<LoanRequestEvent> event) {
		log.info("Received Loan Status Event {}", event);

		if (event == null || event.getPayload() == null) {
			log.warn("Received null event or payload. Skipping consumption.");
			return;
		}

		Object payload = event.getPayload();
		LoanRequestEvent createEvent;

		try {
			if (payload instanceof Map) {
				createEvent = objectMapper.convertValue(payload, LoanRequestEvent.class);
			} else if (payload instanceof LoanRequestEvent) {
				createEvent = (LoanRequestEvent) payload;
			} else {
				// Fallback conversion
				createEvent = objectMapper.convertValue(payload, LoanRequestEvent.class);
			}

			taskService.createTask(createEvent);
		} catch (Exception e) {
			log.error("Error processing loan status event", e);
		}



	}
}
