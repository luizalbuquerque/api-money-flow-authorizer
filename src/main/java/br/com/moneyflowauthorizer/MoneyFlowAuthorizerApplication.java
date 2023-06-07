package br.com.internetbanking;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@OpenAPIDefinition(info = @Info(title = "Money Flow Authorizer", version = "1.0.0"))
@SpringBootApplication
public class MoneyFlowAuthorizerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyFlowAuthorizerApplication.class, args);
	}

}
