package com.moore.ElectricCarService.jobs;

import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.jobs.context.JobContext;
import org.jobrunr.jobs.context.JobDashboardProgressBar;
import org.jobrunr.jobs.context.JobRunrDashboardLogger;
import org.jobrunr.spring.annotations.Recurring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.jobrunr.scheduling.JobScheduler;

@Component
public class MyJobs {

    private static final Logger LOGGER = new JobRunrDashboardLogger(LoggerFactory.getLogger(MyJobs.class));

    @Autowired
    JobScheduler jobScheduler;

    @Recurring(id = "my-recurring-job", cron = "0 0/15 * * *")
    @Job(name = "My recurring job")
    public void scheduleJob() {
        System.out.println("Doing some work without arguments");
    }
}

/*
@Component
class ElectricPriceJob{
    @Autowired
    JobScheduler jobScheduler;

    @PostConstruct
    public void schedule(){
        this.jobScheduler.enqueue(()->{
            System.out.println("Test");
        });
    }
}
*/

//@Service
//public class ElectricPriceJob {
//
//    @Autowired
//    ElectricRateRepository electricRateRepository;
//
//    @Autowired
//    JobScheduler jobScheduler;
//
//    public void priceChange(){
//        Random r = new Random();
//        double price = r.nextDouble()*3;
//        ElectricRate electricRate = new ElectricRate(0, OffsetDateTime.now(),price);
//        electricRateRepository.save(electricRate);
//    }
//
//    @Scheduled(fixedRate = 60_000)
//    @PostConstruct
//    public void scheduleJob(){
//        jobScheduler.enqueue(this::priceChange);
//    }
//}
