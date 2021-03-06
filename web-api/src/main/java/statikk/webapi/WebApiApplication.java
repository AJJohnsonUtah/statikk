package statikk.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@PropertySource(value = "riot-api-key.properties", ignoreResourceNotFound = true)
@SpringBootApplication(scanBasePackages = {"statikk.domain", "statikk.webapi"})
@EnableCaching
@EnableScheduling
public class WebApiApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(WebApiApplication.class, args);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}
