package pl.com.rst.training.delegates.orchestration;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ProcessResults implements JavaDelegate {

  private static final Logger LOG = LoggerFactory.getLogger(ProcessResults.class);
  
  @Override
  public void execute(DelegateExecution ctx) throws Exception {
    List<String> responseKeys = Arrays.asList("responseA", "responseB", "responseC", "responseD", "responseE");
    responseKeys.forEach(
            responseKey -> LOG.info("{}: {}", responseKey, ((ctx.hasVariableLocal(responseKey)) ? ctx.getVariableLocal(responseKey) : ""))
    );
  }
}
