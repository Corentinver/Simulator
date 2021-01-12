package jms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import dto.internal.OperationDTO;
import dto.internal.PointDTO;
import dto.internal.RideDTO;
import manager.OperationThread;
import services.RestService;

@Component
public class Receiver {

  @Autowired
  public RestService restService;

  @JmsListener(destination = "queue.start.operation")
  public void receiveMessage(OperationDTO operation) throws InterruptedException, IOException {
    System.out.println("Received <" + operation + ">");
    restService.newOperation(operation);
    List<RideDTO> rides = new ArrayList<>();
    for(String idVehicle : operation.idVehicle){
      PointDTO locationVehicle = restService.getLocationByVehicule(idVehicle);
      List<PointDTO> listLocalisations = restService.getRouteLocations(locationVehicle.latitude, locationVehicle.longitude, operation.location.latitude, operation.location.longitude);
      double duration = restService.getRouteDuration(locationVehicle.latitude, locationVehicle.longitude, operation.location.latitude, operation.location.longitude);
      rides.add(new RideDTO(operation.id, idVehicle, listLocalisations, duration));
    }
    Thread operationThread = new Thread(new OperationThread(operation, rides));
    operationThread.start();
  }

}