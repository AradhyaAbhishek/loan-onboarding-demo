package com.myloanprocess.loanrequest.producer;

import com.myloanprocess.common.event.LoanRequestEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class LoanEventProducer {

	private final KafkaTemplate<String, Object> kafkaTemplate;


	public LoanEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void publishLoanCreatedEvent(LoanRequestEvent event) {
		log.info("Publishing LoanCreatedEvent {}", event);
		kafkaTemplate.send("loan-created-topic", event);
		log.info("Loan Event Sent to Kafka: " + event);
	}

}
