package np.com.debid.restrouserservice;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan( basePackages = {
		"np.com.debid.restrocommons", "np.com.debid.restrouserservice"
})
public class RestroUserServiceApplication {
	@Bean
	ModelMapper modelMapper(){ return new ModelMapper(); }

	public static void main(String[] args) {
		SpringApplication.run(RestroUserServiceApplication.class, args);
	}

}
