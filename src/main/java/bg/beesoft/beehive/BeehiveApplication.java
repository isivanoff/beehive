package bg.beesoft.beehive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class BeehiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeehiveApplication.class, args);
	}

}
