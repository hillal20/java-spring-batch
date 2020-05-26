package springbatch.springbatch;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBatchApplication {



    public static void main(String[] args) {
        SpringApplication.run(SpringBatchApplication.class, args);
        System.out.println("===== spring batch is running =====");
    }

}
