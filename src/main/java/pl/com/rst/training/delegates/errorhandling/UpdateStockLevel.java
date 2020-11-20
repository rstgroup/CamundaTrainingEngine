package pl.com.rst.training.delegates.errorhandling;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UpdateStockLevel implements JavaDelegate {

  private static final Map<String, Long> AVAILABLE_PRODUCTS;
  private static final Logger LOG = LoggerFactory.getLogger(UpdateStockLevel.class);

  static {
    AVAILABLE_PRODUCTS = new HashMap<>();
    AVAILABLE_PRODUCTS.put("anvil", 1000L);
    AVAILABLE_PRODUCTS.put("vice", 100L);
    AVAILABLE_PRODUCTS.put("pipe", 100L);
  }
  
  @Override
  public void execute(DelegateExecution ctx) throws Exception {
    String productName = null;
    if (ctx.hasVariable("product")) {
      productName = (String) ctx.getVariable("product");
    }

    if (!AVAILABLE_PRODUCTS.containsKey(productName)) {
      LOG.error("UNKNOWN_PRODUCT");
      throw new BpmnError("UNKNOWN_PRODUCT");
    }

    Long itemsNumber = null;
    if (ctx.hasVariable("itemsNumber")) {
      itemsNumber = (Long) ctx.getVariable("itemsNumber");
    }

    if (itemsNumber == null || AVAILABLE_PRODUCTS.get(productName) < itemsNumber) {
      LOG.error("INSUFFICIENT_STOCK_LEVEL");
      throw new BpmnError("INSUFFICIENT_STOCK_LEVEL");
    }

    ProcessEngine processEngine = ctx.getProcessEngine();

    List<Job> failedJobs = processEngine.getManagementService().createJobQuery().withException().list();
    for (Job failedJob : failedJobs) {
      processEngine.getManagementService().setJobRetries(failedJob.getId(), 1);
    }


  }
}
