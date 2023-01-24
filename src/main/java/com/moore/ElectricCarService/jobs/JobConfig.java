package com.moore.ElectricCarService.jobs;

import org.springframework.context.annotation.*;
import org.jobrunr.storage.*;
import org.jobrunr.jobs.mappers.JobMapper;
import org.jobrunr.utils.mapper.gson.*;
import com.google.gson.GsonBuilder;

@Configuration
public class JobConfig {

  @Bean
  public StorageProvider get() {
    InMemoryStorageProvider p = new InMemoryStorageProvider();
    p.setJobMapper(new JobMapper(new GsonJsonMapper(new GsonBuilder())));
    return p;
  }
}