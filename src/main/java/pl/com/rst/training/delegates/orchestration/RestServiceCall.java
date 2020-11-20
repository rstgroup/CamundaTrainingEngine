package pl.com.rst.training.delegates.orchestration;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestServiceCall implements JavaDelegate {

  @Autowired
  protected RestTemplate restTemplate;

  private static final Logger LOG = LoggerFactory.getLogger(RestServiceCall.class);
  
  @Override
  public void execute(DelegateExecution ctx) throws Exception {
    if (!ctx.hasVariableLocal("url")) {
      throw new IllegalArgumentException();
    }
    String url = (String) ctx.getVariableLocal("url");

    LOG.info("Invoke rest service: " + url);

    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    ctx.setVariableLocal("response", response.getBody());
  }
}
