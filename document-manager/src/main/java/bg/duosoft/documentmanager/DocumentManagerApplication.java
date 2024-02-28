package bg.duosoft.documentmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath*:/service.xml"})
public class DocumentManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocumentManagerApplication.class, args);
	}

}
