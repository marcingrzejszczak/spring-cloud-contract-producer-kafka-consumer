package uk.co.dave;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.stubrunner.StubTrigger;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties.StubsMode;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.co.dave.consumer.fxrate.FxRateConsumerApplication;
import uk.co.dave.consumer.fxrate.consumer.FxRateConsumer;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {FxRateConsumerApplication.class}, webEnvironment = WebEnvironment.NONE)
@AutoConfigureStubRunner(stubsMode = StubsMode.CLASSPATH, ids = {"uk.co.dave:fx-producer:+:stubs"})
@Import({TestChannelBinderConfiguration.class})
public class ConsumerIntegartionTest {
  @Autowired
  private StubTrigger stubTrigger;

  @Autowired
  private FxRateConsumer fxRateConsumer;

  @Test
  public void testJsonFxRateBatchEvent() {
    stubTrigger.trigger("triggerJsonFxRateEvent");
    Assertions.assertNotNull(fxRateConsumer.getLastJsonFxRateEvent());
  }

  @Test
  public void testAvroFxRateBatchEvent() {
    stubTrigger.trigger("triggerAvroFxRateEvent");
    Assertions.assertNotNull(fxRateConsumer.getLastAvroFxRateEvent());
  }

}
