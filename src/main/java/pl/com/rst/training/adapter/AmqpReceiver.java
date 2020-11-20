package pl.com.rst.training.adapter;

import org.camunda.bpm.engine.ProcessEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.rst.training.ProcessConstants;
import pl.com.rst.training.entity.AmqpMessage;

@Component
@Profile("!test")
public class AmqpReceiver {

  @Autowired
  private ProcessEngine camunda;

  private static final Logger LOG = LoggerFactory.getLogger(AmqpReceiver.class);

  public AmqpReceiver() {
  }
  
  public AmqpReceiver(ProcessEngine camunda) {
    this.camunda = camunda;
  }

  @RabbitListener(bindings = @QueueBinding(
      value = @Queue(value = "camundaMessageQueue", durable = "true"),
      exchange = @Exchange(value = "camundaMessage", type = "topic", durable = "true"),
      key = "*"))
  @Transactional
  public void receiveAMQPResponse(AmqpMessage message) {

    LOG.debug("Receive AMQP message");
    handleGoodsShippedEvent(message.getBusinessKey(), message.getMessageBody());
  }

  public void handleGoodsShippedEvent(String businessKey, String responseBody) {

    LOG.debug("Correlate message");
    camunda.getRuntimeService().createMessageCorrelation(ProcessConstants.MSG_NAME_AMQPReply)
            .processInstanceBusinessKey(businessKey)
            .setVariable(ProcessConstants.VAR_NAME_responseBody, responseBody)
            .correlateWithResult();
  }
  
}
