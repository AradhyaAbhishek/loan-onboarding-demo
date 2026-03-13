package com.myloanprocess.workflow.service;

import com.myloanprocess.common.event.LoanCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class WorkflowService {

    private final RuntimeService runtimeService;
    private final HistoryService historyService;

    public void startWorkflow(LoanCreatedEvent event) {
        log.info("Starting workflow for loan request: {}", event.getRequestId());

        String businessKey = event.getRequestId();
        Map<String, Object> variables = new HashMap<>();
        variables.put("requestId", event.getRequestId());
        variables.put("customerName", event.getCustomerName());
        variables.put("amount", event.getAmount());

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("loanOnboarding", businessKey, variables);

        log.info("Started process instance {} for business key {}", processInstance.getId(), businessKey);
    }

    public String getWorkflowStatus(String loanRequestId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceBusinessKey(loanRequestId)
                .singleResult();

        if (processInstance != null) {
            return "RUNNING";
        }

        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceBusinessKey(loanRequestId)
                .singleResult();

        if (historicProcessInstance != null) {
            return historicProcessInstance.getState();
        }

        return "NOT_FOUND";
    }
}
