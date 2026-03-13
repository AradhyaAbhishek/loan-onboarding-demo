package com.myloanprocess.workflow.controller;

import com.myloanprocess.workflow.service.WorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workflow")
@RequiredArgsConstructor
public class WorkflowController {

    private final WorkflowService workflowService;

    @GetMapping("/{loanRequestId}")
    public ResponseEntity<String> getWorkflowStatus(@PathVariable String loanRequestId) {
        String status = workflowService.getWorkflowStatus(loanRequestId);
        return ResponseEntity.ok(status);
    }
}
