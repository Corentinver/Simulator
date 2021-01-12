package jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import dto.internal.OperationDTO;
import manager.FireManager;
import manager.OperationThread;
import services.RestService;

@Component
public class Receiver {
  
  @Autowired
  public RestService restService;

  @JmsListener(destination = "queue.start.operation")
  public void receiveMessage(OperationDTO operation) throws InterruptedException {
    System.out.println("Received <" + operation + ">");
    restService.newOperation(operation);
    Thread operationThread = new Thread(new OperationThread(operation));
    operationThread.start();
  }

}