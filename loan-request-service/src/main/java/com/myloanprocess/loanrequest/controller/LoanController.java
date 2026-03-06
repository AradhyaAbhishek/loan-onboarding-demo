package com.myloanprocess.loanrequest.controller;

import com.myloanprocess.common.event.LoanRequestEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loan")
public class LoanController {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	public LoanController(KafkaTemplate<String, Object> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@PostMapping("/create")
	public String createLoan(@RequestBody LoanRequestEvent request) {

		kafkaTemplate.send("loan-created-topic", request);

		return "Loan request published to Kafka";
	}
}
