package manager;

import dto.internal.FireDTO;
import services.RestService;

public class FireManagerThread implements Runnable {

  public FireDTO fire;
  private RestService restService;

  public FireManagerThread(FireDTO fire) {
    this.fire = fire;
    restService = new RestService();
  }

  @Override
  public void run() {
    try {
      synchronized (FireManager.getInstance().getLockFire()) {
        FireManager.getInstance().getLockFire().wait();
        fire.state = FireDTO.stateFire.InOperation;
        restService.updateFire(fire);
        while (this.fire.getIntensity() == 0 & this.fire.getSize() == 0) {
          System.out.println("Run firemanager thread");
          Thread.sleep(1000);
          if(this.fire.getIntensity() != 0){
            this.fire.setIntensity(this.fire.getIntensity() -1);
          }
          if(this.fire.getSize() != 0){
            this.fire.setSize(this.fire.getSize() -1);
          }
        }
        fire.state = FireDTO.stateFire.Completed;
        restService.updateFire(fire);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
