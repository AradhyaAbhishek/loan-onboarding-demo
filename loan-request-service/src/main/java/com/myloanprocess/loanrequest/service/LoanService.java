package com.myloanprocess.loanrequest.service;

import com.myloanprocess.common.event.LoanRequestEvent;
import com.myloanprocess.common.event.LoanStatusUpdatedEvent;
import com.myloanprocess.loanrequest.model.Loan;
import com.myloanprocess.loanrequest.producer.LoanEventProducer;
import com.myloanprocess.loanrequest.repository.LoanRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor
public class LoanService {

	private final LoanRepository loanRepository;
	private final LoanEventProducer loanEventProducer;

	public Loan createLoan(LoanRequestEvent event){

		log.info("Saving loan request {}", event);

		Loan savedLoan = Loan.builder()
				.requestId(event.getRequestId())
				.customerName(event.getCustomerName())
				.amount(event.getAmount())
				.status("IN_PROGRESS")
				.build();


		// Send event to Kafka
		loanEventProducer.publishLoanCreatedEvent(event);
		return savedLoan;

	}
	public void updateLoanStatus(LoanStatusUpdatedEvent event) {

		Loan loan = loanRepository.findByRequestId(event.getRequestId())
				.orElseThrow(() -> new RuntimeException("Loan not found"));

		loan.setStatus(event.getStatus());

		loanRepository.save(loan);
	}
}
