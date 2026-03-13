package com.myloanprocess.loanrequest.controller;

import com.myloanprocess.common.event.LoanRequestEvent;
import com.myloanprocess.common.event.LoanStatusUpdatedEvent;
import com.myloanprocess.loanrequest.model.Loan;
import com.myloanprocess.loanrequest.producer.LoanEventProducer;
import com.myloanprocess.loanrequest.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		return ResponseEntity.ok(loan);
	}

	@PostMapping("/update")
	public  ResponseEntity<Loan> updateLoanStatus(@RequestBody LoanStatusUpdatedEvent request) {
		Loan loan = loanService.updateLoanStatus(request);
		return ResponseEntity.ok(loan);
	}

	@GetMapping("/{requestId}")
	public ResponseEntity<Loan> getLoanStatus(@PathVariable String requestId) {
		Loan loan = loanService.getLoanStatus(requestId);
		return ResponseEntity.ok(loan);
	}
}
