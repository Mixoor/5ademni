package com.mixoor.khademni;

import com.mixoor.khademni.property.FileProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.convert.Jsr310Converters;

@SpringBootApplication
@EntityScan(basePackageClasses = {
        KhademniApplication.class,
        Jsr310Converters.class,
        FileProperties.class

})
@EnableConfigurationProperties({
        FileProperties.class
})
public class KhademniApplication {

    public static void main(String[] args) {
        SpringApplication.run(KhademniApplication.class, args);
    }
}
