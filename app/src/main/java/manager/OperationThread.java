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
        for(RideDTO ride : this.ridesDTO){
            restService.sendRide(ride);
        }
        synchronized(FireManager.getInstance().getLockOperation()){
            try {
                System.out.println("Run operation thread");
                System.out.println(getMeanDuration());
                Thread.sleep(getMeanDuration()*1000);
                FireManager.getInstance().getLockOperation().notify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private long getMeanDuration(){
        return (long)Math.round(ridesDTO.stream().mapToDouble(ride -> ride.duration).sum() / ridesDTO.size());
    }

}
