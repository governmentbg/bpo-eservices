package bg.duosoft.taxcalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
@ImportResource({"classpath*:/service.xml"})
public class TaxCalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaxCalculatorApplication.class, args);
    }

}
