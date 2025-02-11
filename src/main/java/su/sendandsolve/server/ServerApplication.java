package su.sendandsolve.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "su.sendandsolve.server.data.repository")
@EntityScan(basePackages = "su.sendandsolve.server.data.domain")
public class ServerApplication  {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
}
