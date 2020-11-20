package pl.com.rst.training.delegates.errorhandling;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RollbackOrder implements JavaDelegate {

  private static final Logger LOG = LoggerFactory.getLogger(RollbackOrder.class);
  
  @Override
  public void execute(DelegateExecution ctx) throws Exception {

    LOG.info("Rollback stock level!");
  }
}
