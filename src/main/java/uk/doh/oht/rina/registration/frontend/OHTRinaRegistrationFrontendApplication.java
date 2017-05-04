package uk.doh.oht.rina.registration.frontend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by peterwhitehead on 28/04/2017.
 */
@SpringBootApplication
@ComponentScan(basePackages = { "uk.doh.oht" })
@Slf4j
public class OHTRinaRegistrationFrontendApplication {
    public static void main(final String... args) throws Exception {
        log.info("Starting oht-rina-registration-frontend");
        SpringApplication.run(OHTRinaRegistrationFrontendApplication.class, args);
    }
}
