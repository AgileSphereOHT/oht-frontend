package uk.doh.oht.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by peterwhitehead on 05/05/2017.
 */
@Configuration
@ConfigurationProperties("oht-database")
@Data
public class DataProperties {
    private String url;
    private String port;
    private String rootPath;

    public String buildRootPath() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(url).append(":").append(port).append(rootPath).toString();
    }
}
