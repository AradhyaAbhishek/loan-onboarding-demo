package com.myloanprocess.loanrequest.controller;

import com.myloanprocess.common.event.LoanRequestEvent;
import com.myloanprocess.loanrequest.model.Loan;
import com.myloanprocess.loanrequest.producer.LoanEventProducer;
import com.myloanprocess.loanrequest.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loan")
public class LoanController {

	private final LoanEventProducer loanEventProducer;
	private final LoanService loanService;

	private final KafkaTemplate<String, Object> kafkaTemplate;

	public LoanController(KafkaTemplate<String, Object> kafkaTemplate,LoanEventProducer loanEventProducer,
			LoanService loanService) {
		this.kafkaTemplate = kafkaTemplate;
		this.loanEventProducer = loanEventProducer;
		this.loanService = loanService;
	}

	@PostMapping("/create")
	public ResponseEntity<Loan> createLoan(@RequestBody LoanRequestEvent request) {
		Loan loan = loanService.createLoan(request);
//		loanEventProducer.sendLoanEvent(request);
		return ResponseEntity.ok(loan);
	}
}
