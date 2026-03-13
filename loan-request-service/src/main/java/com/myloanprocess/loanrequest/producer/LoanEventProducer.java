package com.myloanprocess.loanrequest.producer;

import com.myloanprocess.common.constants.EventType;
import com.myloanprocess.common.constants.KafkaTopics;
import com.myloanprocess.common.event.EventEnvelope;
import com.myloanprocess.common.event.LoanRequestEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Log4j2
@Service
public class LoanEventProducer {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	public LoanEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void publishLoanCreatedEvent(LoanRequestEvent event) {
		EventEnvelope<LoanRequestEvent> envelope =
				EventEnvelope.<LoanRequestEvent>builder()
						.eventId(UUID.randomUUID().toString())
						.eventType(EventType.LOAN_CREATED)
						.source("loan-request-service")
						.timestamp(LocalDateTime.now())
						.payload(event)
						.build();
		log.info("Publishing LoanCreatedEvent {}", envelope);
		kafkaTemplate.send(KafkaTopics.LOAN_CREATED, envelope);
		log.info("Loan Event Sent to Kafka: " + event);
	}

}
