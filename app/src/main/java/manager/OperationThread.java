package manager;

import dto.internal.OperationDTO;

public class OperationThread implements Runnable {

    public OperationDTO operationDTO = null;

    public OperationThread(OperationDTO operation) {
        this.operationDTO = operation;
    }

    @Override
    public void run() {
        synchronized(FireManager.getInstance().getLockFire()){
            try {
                System.out.println("Run operation thread");
                Thread.sleep(10000);
                FireManager.getInstance().getLockFire().notify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
