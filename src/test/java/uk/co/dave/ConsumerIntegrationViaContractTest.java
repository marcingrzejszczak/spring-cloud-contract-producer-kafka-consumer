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
public class ConsumerIntegrationViaContractTest {
  @Autowired
  private StubTrigger stubTrigger;

  @Autowired
  private FxRateConsumer fxRateConsumer;

  /**
   * Does not work
   */
  @Test
  public void testJsonFxRateEventViaSpringCloudContract() {
    JsonFxRateEvent expected = new JsonFxRateEvent("GBP", "USD", BigDecimal.valueOf(1.23));
    stubTrigger.trigger("triggerJsonFxRateEvent");
    Assertions.assertEquals(expected, fxRateConsumer.getLastJsonFxRateEvent());
  }
  
  /**
   * Does not work
   */
  @Test
  public void testAvroFxRateEventViaSpringCloudContract() {
    AvroFxRateEvent expected = AvroFxRateEvent.newBuilder().setFrom("GBP").setTo("USD").setRate(BigDecimal.valueOf(1.23)).build();
    stubTrigger.trigger("triggerAvroFxRateEvent");
    Assertions.assertEquals(expected, fxRateConsumer.getLastAvroFxRateEvent());
  }
  
}
