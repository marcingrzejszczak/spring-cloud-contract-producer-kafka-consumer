package uk.co.dave.consumer.fxrate.consumer;

import java.util.function.Consumer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import uk.co.dave.consumer.fxrate.consumer.avro.AvroFxRateEvent;
import uk.co.dave.consumer.fxrate.consumer.json.JsonFxRateEvent;


@Slf4j
@Component
public class FxRateConsumer {

  @Getter
  private JsonFxRateEvent lastJsonFxRateEvent;
  @Getter
  private AvroFxRateEvent lastAvroFxRateEvent;

  @Bean
  public Consumer<Flux<JsonFxRateEvent>> consumeJson() {
    return events -> events.subscribe(jsonFxRateEvent -> {
      log.info("jsonFxRateEvent = {}", jsonFxRateEvent);
      this.lastJsonFxRateEvent = jsonFxRateEvent;
    });
  }

  @Bean
  public Consumer<Flux<AvroFxRateEvent>> consumeAvro() {
    return events -> events.subscribe(avroFxRateEvent -> {
      log.info("avroFxRateEvent = {}", avroFxRateEvent);
      this.lastAvroFxRateEvent = avroFxRateEvent;
    });
  }


}
