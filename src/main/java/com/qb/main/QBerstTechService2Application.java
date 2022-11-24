package com.qb.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = {"com.qb"})
@EnableJdbcRepositories(basePackages = {"com.qb"})
@EnableJpaRepositories(basePackages = {"com.qb.Dao"})
@EntityScan(basePackages = {"com.qb"})
public class QBerstTechService2Application {

	public static void main(String[] args) {
		SpringApplication.run(QBerstTechService2Application.class, args);
	}

}
