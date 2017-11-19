package statikk.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DomainApplication {

    public static void main(String[] args) {
        SpringApplication.run(DomainApplication.class, args);
    }
}
