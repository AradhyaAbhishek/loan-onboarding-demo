package com.myloasprocess.taskservice.consumer;

import com.myloanprocess.common.event.LoanRequestEvent;
import com.myloasprocess.taskservice.service.TaskService;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class LoanEventConsumer {

	private final TaskService taskService;

	public LoanEventConsumer(TaskService taskService) {
		this.taskService = taskService;
	}

	@KafkaListener(topics = "loan-created-topic", groupId = "task-group")
	public void consume(LoanRequestEvent event) {
		log.info("Received Loan Request: " + event);
		taskService.createTask(event);

	}
}
