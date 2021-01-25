package uk.co.dave.consumer.fxrate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.schema.registry.client.EnableSchemaRegistryClient;


/**
 * Two options to make a Spring Cloud Stream 2 project work in Spring Cloud Stream 3 <br/>
 * 1) add @SpringBootApplication(exclude = FunctionConfiguration.class) <br/>
 * 2) add a Bean called output()
 */
@SpringBootApplication
@EnableSchemaRegistryClient
public class FxRateConsumerApplication {
  public static void main(String[] args) {
    SpringApplication.run(FxRateConsumerApplication.class, args);
  }

}
