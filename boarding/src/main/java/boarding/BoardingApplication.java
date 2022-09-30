package boarding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;

@SpringBootApplication(exclude= {MultipartAutoConfiguration.class})
public class BoardingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardingApplication.class, args);
	}

}
