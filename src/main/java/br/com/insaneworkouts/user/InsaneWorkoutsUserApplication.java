package br.com.insaneworkouts.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class , ManagementWebSecurityAutoConfiguration.class})
public class InsaneWorkoutsUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsaneWorkoutsUserApplication.class, args);
	}

}
