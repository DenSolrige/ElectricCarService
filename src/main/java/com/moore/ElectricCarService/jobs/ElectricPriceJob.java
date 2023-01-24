package com.moore.ElectricCarService.jobs;

import com.moore.ElectricCarService.entities.ElectricRate;
import com.moore.ElectricCarService.repos.ElectricRateRepository;
import jakarta.annotation.PostConstruct;
import lombok.val;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Random;

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
