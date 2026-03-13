package com.myloanprocess.loanrequest.producer;

import com.myloanprocess.common.constants.EventType;
import com.myloanprocess.common.constants.KafkaTopics;
import com.myloanprocess.common.event.EventEnvelope;
import com.myloanprocess.common.event.LoanStatusUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class LoanStatusEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendStatusUpdate(LoanStatusUpdatedEvent event) {
        EventEnvelope<LoanStatusUpdatedEvent> envelope =
                EventEnvelope.<LoanStatusUpdatedEvent>builder()
                        .eventId(UUID.randomUUID().toString())
                        .eventType(EventType.LOAN_STATUS_UPDATED)
                        .source("loan-request-service")
                        .timestamp(LocalDateTime.now())
                        .payload(event)
                        .build();
        log.info("Publishing LoanStatusUpdatedEvent {}", envelope);
        kafkaTemplate.send(KafkaTopics.LOAN_STATUS_UPDATED, envelope);
        log.info("Loan Status Update Event Sent to Kafka: " + event);
    }
}
