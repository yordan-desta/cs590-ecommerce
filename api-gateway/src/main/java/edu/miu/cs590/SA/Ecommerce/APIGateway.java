package edu.miu.cs590.SA.Ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class APIGateway {

	public static void main(String[] args) {
		SpringApplication.run(APIGateway.class, args);
	}

}
