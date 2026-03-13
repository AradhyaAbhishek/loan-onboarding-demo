package com.myloanprocess.workflow.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myloanprocess.common.constants.KafkaTopics;
import com.myloanprocess.common.event.EventEnvelope;
import com.myloanprocess.common.event.LoanCreatedEvent;
import com.myloanprocess.workflow.service.WorkflowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class WorkflowConsumer {

    private final WorkflowService workflowService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = KafkaTopics.LOAN_CREATED, groupId = "workflow-group")
    public void consume(String message) {
        log.info("Received loan created message: {}", message);
        try {
            EventEnvelope<Object> envelope = objectMapper.readValue(message, new TypeReference<EventEnvelope<Object>>() {});
            
            if (envelope.getPayload() instanceof Map) {
                LoanCreatedEvent event = objectMapper.convertValue(envelope.getPayload(), LoanCreatedEvent.class);
                workflowService.startWorkflow(event);
            } else {
                log.warn("Received event with unexpected payload type: {}", envelope.getPayload().getClass());
            }
        } catch (Exception e) {
            log.error("Error processing message: {}", message, e);
        }
    }
}
