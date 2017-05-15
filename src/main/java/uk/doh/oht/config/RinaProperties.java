package uk.doh.oht.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by peterwhitehead on 28/04/2017.
 */
@Configuration
@ConfigurationProperties("rina")
@Data
public class RinaProperties {
    private String registrationUrl;
    private String registrationPort;
    private String registrationRootPath;

    public String buildRootPath() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(registrationUrl).append(":").append(registrationPort).append(registrationRootPath).toString();
    }
}
