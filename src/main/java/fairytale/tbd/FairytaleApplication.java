package fairytale.tbd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FairytaleApplication {

	public static void main(String[] args) {
		SpringApplication.run(FairytaleApplication.class, args);
	}

}
