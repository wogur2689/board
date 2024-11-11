package com.hyeok.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //JPA Auditing을 활성화하는 어노테이션
@SpringBootApplication
public class BoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}
	//@SpringBootApplication은 @Configuration, @EnableAutoConfiguration, @ComponentScan 어노테이션이 합쳐진 것
	//스프링부트 기반의 애플리케이션

}
