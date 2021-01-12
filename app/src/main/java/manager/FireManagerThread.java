package manager;

import dto.internal.FireDTO;
import services.RestService;

public class FireManagerThread implements Runnable {

  public FireDTO fire;
  private RestService restService;
  private Boolean inOperation = false;
  public Thread fireEvolution;
  public Thread waitingOperation;

  public FireManagerThread(FireDTO fire) {
    this.fire = fire;
    restService = new RestService();

    fireEvolution = new Thread(new Runnable() {

      @Override
      public void run() {
        try {
          fire.state = FireDTO.stateFire.InOperation;
          restService.updateFire(fire);
          while (fire.getIntensity() != 0 || fire.getSize() != 0) {
            System.out.println("Run fire");
            System.out.println("int : " + fire.getIntensity());
            System.out.println("size : " + fire.getSize());
            Thread.sleep(1000);
            if (inOperation) {
              if (fire.getIntensity() != 0) {
                fire.setIntensity(fire.getIntensity() - 1);
              }
              if (fire.getSize() != 0) {
                fire.setSize(fire.getSize() - 1);
              }
            }
          }
          System.out.println("Fire completed");
          fire.state = FireDTO.stateFire.Completed;
          restService.updateFire(fire);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

      }

    });

    waitingOperation = new Thread(new Runnable() {

      @Override
      public void run() {
        synchronized (FireManager.getInstance().getLockOperation()) {
          try {
            System.out.println("Waiting");
            FireManager.getInstance().getLockOperation().wait();
            inOperation = true;
            System.out.println("good");
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }

    });
  }

  @Override
  public void run() {
    fireEvolution.start();
    waitingOperation.start();

    try {
      fireEvolution.join();
      waitingOperation.join();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }
}
