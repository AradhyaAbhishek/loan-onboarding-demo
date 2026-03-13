package com.myloasprocess.taskservice.producer;

import com.myloanprocess.common.constants.EventType;
import com.myloanprocess.common.constants.KafkaTopics;
import com.myloanprocess.common.event.EventEnvelope;
import com.myloanprocess.common.event.LoanStatusUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoanStatusEventProducer {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	public void sendStatusUpdate(LoanStatusUpdatedEvent event) {
		EventEnvelope<LoanStatusUpdatedEvent> envelope =
				EventEnvelope.<LoanStatusUpdatedEvent>builder()
						.eventId(UUID.randomUUID().toString())
						.eventType(EventType.LOAN_STATUS_UPDATED)
						.source("task-service")
						.timestamp(LocalDateTime.now())
						.payload(event)
						.build();
		log.info("Publishing LoanStatusUpdatedEvent {}", envelope);
		kafkaTemplate.send(KafkaTopics.LOAN_STATUS_UPDATED, envelope);
		log.info("Loan Event Sent to Kafka: {} ", event);
	}
}
