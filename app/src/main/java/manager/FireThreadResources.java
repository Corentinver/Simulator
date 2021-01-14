package manager;

public class FireThreadResources {

    private Object lock;
    private int vehicleNumbers;

    public Object getLock(){
        return lock;
    }

    public void setLock(Object lock){
        this.lock = lock;
    }

    public Object getVehicleNumbers(){
        return vehicleNumbers;
    }

    public void setVehicleNumbers(int vehicleNumbers){
        this.vehicleNumbers = vehicleNumbers;
    }

    public void addToVehicleNumbers(int number){
        this.vehicleNumbers += number;
    }


}
