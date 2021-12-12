package tourguide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("tourguide")
public class Api_Tourguide {

	public static void main(String[] args) {
		SpringApplication.run(Api_Tourguide.class, args);
	}

}
