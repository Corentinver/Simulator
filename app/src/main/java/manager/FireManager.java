package manager;

public class FireManager {

    private Object lockFire;

    private Object lockOperation;

    public FireManager() {
        this.lockFire = new Object();
        this.lockOperation = new Object();
    }

    private static FireManager INSTANCE = null;

    public static FireManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FireManager();
        }
        return INSTANCE;
    }

    public Object getLockFire() {
        return this.lockFire;
    }

    public Object getLockOperation() {
        return this.lockOperation;
    }

}
