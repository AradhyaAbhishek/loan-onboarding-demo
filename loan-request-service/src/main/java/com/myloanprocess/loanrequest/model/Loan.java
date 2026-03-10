package com.myloanprocess.loanrequest.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "loans" )
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

	@Id
	private String requestId;

	private String customerName;
	private Double amount;
	private String status;
}
