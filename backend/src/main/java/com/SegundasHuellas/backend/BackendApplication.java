package com.SegundasHuellas.backend;

import com.SegundasHuellas.backend.shared.error.DomainException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.SegundasHuellas.backend.shared.error.DomainException.ErrorCode.RESOURCE_NOT_FOUND;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);

		throw new DomainException(RESOURCE_NOT_FOUND);
	}

}
