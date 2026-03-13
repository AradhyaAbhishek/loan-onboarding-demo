package com.myloanprocess.loanrequest.service;

import com.myloanprocess.common.event.LoanRequestEvent;
import com.myloanprocess.common.event.LoanStatusUpdatedEvent;
import com.myloanprocess.loanrequest.model.Loan;
import com.myloanprocess.loanrequest.producer.LoanEventProducer;
import com.myloanprocess.loanrequest.producer.LoanStatusEventProducer;
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
	private final LoanStatusEventProducer loanStatusEventProducer;

	public Loan createLoan(LoanRequestEvent event){

		log.info("Saving loan request {}", event);

		Loan loan = Loan.builder()
				.requestId(event.getRequestId())
				.customerName(event.getCustomerName())
				.amount(event.getAmount())
				.status("IN_PROGRESS")
				.build();
		Loan savedLoan = loanRepository.save(loan);

		log.info("Loan saved to PostgreSQL {}", savedLoan);

		// Send LoanCreatedEvent
		loanEventProducer.publishLoanCreatedEvent(event);

		// ALSO Send LoanStatusUpdatedEvent for the initial status
		LoanStatusUpdatedEvent statusEvent = LoanStatusUpdatedEvent.builder()
				.requestId(savedLoan.getRequestId())
				.status(savedLoan.getStatus())
				.updatedBy("system")
				.build();
		loanStatusEventProducer.sendStatusUpdate(statusEvent);

		return savedLoan;

	}
	public Loan updateLoanStatus(LoanStatusUpdatedEvent event) {
		return updateLoanStatus(event, true);
	}

	public Loan updateLoanStatus(LoanStatusUpdatedEvent event, boolean publishEvent) {

		Loan loan = loanRepository.findByRequestId(event.getRequestId())
				.orElseThrow(() -> new RuntimeException("Loan not found"));

		loan.setStatus(event.getStatus());
		loanRepository.save(loan);
		log.info("Loan status updated to {}",loan.getStatus());
		if (publishEvent) {
			loanStatusEventProducer.sendStatusUpdate(event);
		}
		return loan;
	}

	public Loan getLoanStatus(String requestId) {
		return loanRepository.findByRequestId(requestId)
				.orElseThrow(() -> new RuntimeException("Loan with request id " + requestId + " not found"));
	}
}
