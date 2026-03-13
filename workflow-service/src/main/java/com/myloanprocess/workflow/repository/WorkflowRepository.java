package com.myloanprocess.workflow.repository;

import com.myloanprocess.workflow.entity.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkflowRepository extends JpaRepository<Workflow, String> {
    Optional<Workflow> findByLoanRequestId(String loanRequestId);
}
