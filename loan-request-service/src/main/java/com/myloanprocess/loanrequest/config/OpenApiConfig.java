package com.myloanprocess.loanrequest.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI loanApi() {
		return new OpenAPI()
				.info(new Info()
						.title("Loan Onboarding API")
						.version("1.0")
						.description("Demo microservice for loan onboarding workflow"));
	}
}