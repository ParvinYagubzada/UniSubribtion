package az.code.unisubribtion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UniSubribtionApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniSubribtionApplication.class, args);
    }

}
