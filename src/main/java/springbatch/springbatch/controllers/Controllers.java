package springbatch.springbatch.controllers;


import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/load")
public class Controllers {

    @Autowired // needed to launch the job
    JobLauncher jobLauncher;

    @Autowired // Autowired from SpringBatchingConfiguration
    Job job;


    @GetMapping
    public BatchStatus getAllUsers() throws JobParametersInvalidException
            , JobExecutionAlreadyRunningException,
            JobRestartException,
            JobInstanceAlreadyCompleteException {

        //we create an obj ( hashMap) of key value pairs to hold all the job params
        Map<String, JobParameter> myMap = new HashMap<>();
        myMap.put("time", new JobParameter(System.currentTimeMillis()));

        // we create job params via hash map
         JobParameters jobParameters = new JobParameters(myMap);

         // we receive the job execution result
         JobExecution jobExecution = jobLauncher.run(job, jobParameters);


         // for demoing the bash if it is running we loop thru job execution
        while (jobExecution.isRunning())
            System.out.println(" === job is running =====");

        System.out.println("=== job execution status =====> "+ jobExecution.getStatus());

        return jobExecution.getStatus();
    }






}
