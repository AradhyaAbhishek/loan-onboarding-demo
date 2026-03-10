package com.myloanprocess.loanrequest.repository;

import com.myloanprocess.loanrequest.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan,String> {
	Optional<Loan> findByRequestId(String requestId);
}
