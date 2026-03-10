package com.myloasprocess.taskservice.producer;

import com.myloanprocess.common.event.LoanStatusUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoanStatusEventProducer {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	public void sendStatusUpdate(LoanStatusUpdatedEvent event) {
		kafkaTemplate.send("loan-status-topic", event);
		log.info("Loan Status Event Sent: {}", event);
	}
}
