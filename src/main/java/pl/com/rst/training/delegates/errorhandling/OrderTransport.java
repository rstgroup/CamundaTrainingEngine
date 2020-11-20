package pl.com.rst.training.delegates.errorhandling;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class OrderTransport implements JavaDelegate {

  private static final Long ITEMS_LIMIT = 100L;
  private static final List<String> AVAILABLE_TRANSPORT_COMPANIES = Collections.singletonList("postman");
  private static final Logger LOG = LoggerFactory.getLogger(OrderTransport.class);
  
  @Override
  public void execute(DelegateExecution ctx) throws Exception {
    String transportCompanyName = null;
    if (ctx.hasVariable("transportCompany")) {
      transportCompanyName = (String) ctx.getVariable("transportCompany");
    }

    if (!AVAILABLE_TRANSPORT_COMPANIES.contains(transportCompanyName)) {
      LOG.error("TRANSPORT_OPERATOR_NOT_AVAILABLE");
      throw new BpmnError("TRANSPORT_OPERATOR_NOT_AVAILABLE");
    }

    Long itemsNumber = null;
    if (ctx.hasVariable("itemsNumber")) {
      itemsNumber = (Long) ctx.getVariable("itemsNumber");
    }

    ctx.setVariable("needMultipleVehicles", (itemsNumber != null && ITEMS_LIMIT < itemsNumber));
    ctx.setVariable("shipmentNumber", new ObjectIdGenerators.UUIDGenerator().generateId(this));
    LOG.info("Transport in preparation");
  }
}
