package com.moore.ElectricCarService;

import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.jobs.mappers.JobMapper;
import org.jobrunr.storage.InMemoryStorageProvider;
import org.jobrunr.storage.StorageProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.moore.ElectricCarService"})
@EntityScan(basePackages = {"com.moore.ElectricCarService.entities"})
@EnableJpaRepositories(basePackages = {"com.moore.ElectricCarService.repos"})
public class ElectricCarServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectricCarServiceApplication.class, args);
	}
}

