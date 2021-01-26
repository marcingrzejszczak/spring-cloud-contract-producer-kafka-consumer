package uk.co.dave;

import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.stubrunner.StubTrigger;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties.StubsMode;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.co.dave.consumer.fxrate.FxRateConsumerApplication;
import uk.co.dave.consumer.fxrate.consumer.FxRateConsumer;
import uk.co.dave.consumer.fxrate.consumer.avro.AvroFxRateEvent;
import uk.co.dave.consumer.fxrate.consumer.json.JsonFxRateEvent;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {FxRateConsumerApplication.class}, webEnvironment = WebEnvironment.NONE)
@AutoConfigureStubRunner(stubsMode = StubsMode.CLASSPATH, ids = {"uk.co.dave:fx-producer:+:stubs"})
@Import({TestChannelBinderConfiguration.class})
public class ConsumerIntegrationViaChannelTest {


  @Autowired
  private FxRateConsumer fxRateConsumer;

  @Autowired
  @Qualifier("consumeJson-in-0")
  private SubscribableChannel consumeJsonInChannel;


  @Autowired
  @Qualifier("consumeAvro-in-0")
  private SubscribableChannel consumeAvroInChannel;

  /**
   * Works
   */
  @Test
  public void testJsonFxRateEvent() {
    JsonFxRateEvent expected = new JsonFxRateEvent("GBP", "USD", BigDecimal.TEN);
    consumeJsonInChannel.send(MessageBuilder.withPayload(expected).build());
    Assertions.assertEquals(expected, fxRateConsumer.getLastJsonFxRateEvent());
  }
  /**
   * Works
   */
  @Test
  public void testAvroFxRateEvent() {
    AvroFxRateEvent expected = AvroFxRateEvent.newBuilder().setFrom("GBP").setTo("USD").setRate(BigDecimal.TEN).build();
    consumeAvroInChannel.send(MessageBuilder.withPayload(expected).build());
    Assertions.assertEquals(expected, fxRateConsumer.getLastAvroFxRateEvent());
  }
  
}
