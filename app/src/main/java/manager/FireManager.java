package manager;

import java.util.HashMap;

public class FireManager {

    private HashMap<String, Thread> fireThreads;
    private HashMap<String, Object> lockFireThreads;
    private Object lockFire;

    private Object lockOperation;

    public FireManager() {
        this.fireThreads = new HashMap<>();
        this.lockFireThreads = new HashMap<>();
    }

    private static FireManager INSTANCE = null;

    public static FireManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FireManager();
        }
        return INSTANCE;
    }

    public Object getLockFireThreads(String idFire) {
        return this.lockFireThreads.get(idFire);
    }

    public void addResources(Object lock, String id){
        this.lockFireThreads.put(id, lock);
    }

    public void removeResources(String id){
        this.lockFireThreads.remove(id);
    }

    public Thread getFireThreads(String idFire) {
        return this.fireThreads.get(idFire);
    }

    public void addThread(Thread thread, String id){
        this.fireThreads.put(id, thread);
    }

    public void removeThread(String id){
        this.fireThreads.remove(id);
    }

    public int getNumberFireThread(){
        return this.fireThreads.size();
    }

    

}
