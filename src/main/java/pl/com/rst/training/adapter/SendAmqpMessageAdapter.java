package pl.com.rst.training.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.com.rst.training.ProcessConstants;
import pl.com.rst.training.entity.AmqpMessage;

@Component
public class SendAmqpMessageAdapter implements JavaDelegate {

  @Autowired
  protected RabbitTemplate rabbitTemplate;

  private static final Logger LOG = LoggerFactory.getLogger(SendAmqpMessageAdapter.class);
  
  @Override
  public void execute(DelegateExecution ctx) throws Exception {
    String businessKey = ctx.getProcessBusinessKey();
    String messageBody = "";
    if (ctx.hasVariableLocal(ProcessConstants.VAR_NAME_messageBody)) {
      messageBody = (String) ctx.getVariableLocal(ProcessConstants.VAR_NAME_messageBody);
    }

    String exchange = "camundaMessage";
    String routingKey = "camundaMessagePass";

    AmqpMessage message = new AmqpMessage();
    message.setBusinessKey(businessKey);
    message.setMessageBody(messageBody);

    LOG.debug("Send AMQP message");
    rabbitTemplate.convertAndSend(exchange, routingKey, message);
  }

}
