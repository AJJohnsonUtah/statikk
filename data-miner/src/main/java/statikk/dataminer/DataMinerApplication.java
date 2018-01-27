package statikk.dataminer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import statikk.dataminer.main.MiningManager;

@SpringBootApplication
@PropertySource(value = "riot-api-key.properties", ignoreResourceNotFound = true)
@ComponentScan({"statikk.dataminer", "statikk.domain"})
@EnableCaching
public class DataMinerApplication implements CommandLineRunner {

    @Autowired
    MiningManager miningManager;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DataMinerApplication.class);
        app.run(args);
    }

    @Override
    public void run(String[] strings) throws Exception {
        miningManager.begin();
    }

}
