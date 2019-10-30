package martin.dev.pricer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PricerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PricerApplication.class, args);
    }

}
