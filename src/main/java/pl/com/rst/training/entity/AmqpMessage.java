package pl.com.rst.training.entity;

import java.io.Serializable;

public class AmqpMessage implements Serializable {
    private String businessKey;
    private String messageBody;

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
}
