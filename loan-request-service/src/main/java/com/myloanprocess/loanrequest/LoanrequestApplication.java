package com.myloanprocess.loanrequest;

import com.myloanprocess.common.utility.CommonUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class LoanrequestApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanrequestApplication.class, args);
		System.out.println(CommonUtil.greet("Loan Request Service"));

	}

}
