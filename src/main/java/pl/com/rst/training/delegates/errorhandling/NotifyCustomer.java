package pl.com.rst.training.delegates.errorhandling;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NotifyCustomer implements JavaDelegate {

  private static final Logger LOG = LoggerFactory.getLogger(NotifyCustomer.class);
  
  @Override
  public void execute(DelegateExecution ctx) throws Exception {
    if (ctx.hasVariableLocal("message")) {
      LOG.info((String) ctx.getVariableLocal("message"));
    }
  }
}
