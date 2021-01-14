package manager;

import java.util.List;

import dto.internal.OperationDTO;
import dto.internal.RideDTO;
import services.RestService;

public class OperationThread implements Runnable {

    public OperationDTO operationDTO = null;
    public List<RideDTO> ridesDTO;
    public RestService restService;

    
    public OperationThread(OperationDTO operation, List<RideDTO> rideDTOs) {
        this.operationDTO = operation;
        this.ridesDTO = rideDTOs;
        this.restService = new RestService();
    }

    @Override
    public void run() {
        synchronized(FireManager.getInstance().getLockFireThreads(operationDTO.idFire)){
            try {
                for(RideDTO ride : this.ridesDTO){
                    Thread.sleep(300);
                    restService.sendRide(ride);
                }
                System.out.println("Run operation thread");
                System.out.println(getMeanDuration());
                Thread.sleep(getMeanDuration()*10);
                FireManager.getInstance().getLockFireThreads(operationDTO.idFire).notify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private long getMeanDuration(){
        return (long)Math.round(ridesDTO.stream().mapToDouble(ride -> ride.duration).sum() / ridesDTO.size());
    }

}
