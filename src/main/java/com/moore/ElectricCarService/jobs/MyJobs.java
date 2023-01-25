package com.moore.ElectricCarService.jobs;

import com.moore.ElectricCarService.entities.Charge;
import com.moore.ElectricCarService.entities.ElectricRate;
import com.moore.ElectricCarService.entities.Transaction;
import com.moore.ElectricCarService.repos.ElectricRateRepository;
import com.moore.ElectricCarService.repos.TransactionRepository;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.jobs.context.JobRunrDashboardLogger;
import org.jobrunr.spring.annotations.Recurring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.jobrunr.scheduling.JobScheduler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.*;

@Component
public class MyJobs {

    private static final Logger LOGGER = new JobRunrDashboardLogger(LoggerFactory.getLogger(MyJobs.class));

    @Autowired
    ElectricRateRepository electricRateRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    JobScheduler jobScheduler;

    @Recurring(id = "price-fluctuations", cron = "0 * * * * *")
    @Job(name = "Price fluctuations")
    public void changeRate() {
        Random r = new Random();
        double price = r.nextDouble()*3;
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        price = Double.parseDouble(df.format(price));
        ElectricRate electricRate = new ElectricRate(0, OffsetDateTime.now(),price);
        electricRateRepository.save(electricRate);
        System.out.println("Electric rate changed to "+price);
    }

    @Recurring(id = "record-keeping", cron = "0 * * * *")
    @Job(name = "Payment record keeping")
    public void record() {
        List<Transaction> transactions = transactionRepository.findByIsProcessedEquals(false);

        if(transactions.size() == 0){
            // no point in adding lines to the file with no information in them
            return;
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss'Z'", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String isoDate = dateFormat.format(new Date());
        String filename = "C:\\Users\\Sunny\\Programming\\Java\\ElectricCarService\\Payment\\paymentdetails."+ isoDate +".txt";
        File file = new File(filename);
        try{file.createNewFile();}catch(IOException e){
            e.printStackTrace();
        }

        try(FileWriter report = new FileWriter(filename)){

            for(Transaction t:transactions){

                List<Charge> charges = t.getChargeList();
                double initialCharge = charges.stream().min(Comparator.comparing(Charge::getChargeId)).get().getChargeAmount();
                double finalCharge = charges.stream().max(Comparator.comparing(Charge::getChargeId)).get().getChargeAmount();
                DecimalFormat df = new DecimalFormat("#.##");
                df.setRoundingMode(RoundingMode.HALF_UP);
                String kwhTotal = df.format((finalCharge-initialCharge)/1000);

                double companyPayment = Double.parseDouble(kwhTotal)+t.getCompanyRate() + t.getCompanyFlatCharge();
                companyPayment = Double.parseDouble(df.format(companyPayment));

                double espPayment = 0;
                for(Charge c:charges){
                    Optional<ElectricRate> optionalElectricRate = electricRateRepository.findTopByTimestampLessThanOrderByTimestampDesc(c.getTimestamp());
                    if(optionalElectricRate.isPresent()){
                        espPayment += optionalElectricRate.get().getValue()*c.getChargeAmount()/1000;
                    }
                }
                espPayment = Double.parseDouble(df.format(espPayment));

                report.write("Charging station identifier: "+t.getChargingStation().getIdentifier()
                        +"   Transaction id: "+t.getTransactionId()+"\n"
                        +"Start time: "+t.getStartTime()
                        +"   Stop time: "+t.getStopTime()+"\n"
                        +"KWH total charged: "+kwhTotal+"\n"
                        +"Company payment due: "+companyPayment
                        +"   Electric service provider payment due: "+espPayment+"\n"
                        +"----------------------------------------------------------------------"+"\n"
                );

                t.setProcessed(true);
                transactionRepository.save(t);
            }

        }catch(IOException e){
            System.out.println("Error creating report");
            e.printStackTrace();
        }
    }
}