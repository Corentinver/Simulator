package manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dto.internal.*;
import services.RestService;
import services.SimulatorService;

@Component
public class FireGeneratorThread implements Runnable {
    public List<FireDTO> fires;

    public List<FireStationDTO> fireStations;

    public List<FireFighterDTO> fireFighters;

    public List<VehicleDTO> vehicles;

    public List<SensorDTO> sensors;

    public List<TypeFireDTO> typeFires;

    public SimulatorService simulatorService;

    public RestService restService;

    private Boolean generated = false;

    public FireGeneratorThread() {
        simulatorService = new SimulatorService();
        restService = new RestService();
        fireStations = simulatorService.getAllFireStation();
        fireFighters = simulatorService.getAllFireFighter();
        vehicles = simulatorService.getAllVehicle();
        sensors = simulatorService.getAllSensor();
        typeFires = simulatorService.getAllTypeFire();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000*(FireManager.getInstance().getNumberFireThread()+1));

                if (generateFire() && FireManager.getInstance().getNumberFireThread() < 2) {
                    PointDTO locationFire = getRandomLocationFromSensor(sensors);
                    Random rand = new Random();

                    String idTypeFire = typeFires.get(rand.nextInt(typeFires.size())).id;
                    System.out.println(locationFire);

                    FireDTO fire = new FireDTO();
                    fire.setLocation(locationFire);
                    fire.setTypeFire(idTypeFire);
                    fire.setState(FireDTO.stateFire.Initialize);
                    fire.setIntensity(20);
                    fire.setSize(3);
                    String id = restService.newFire(fire);
                    fire.setId(id);
                    Thread fireThread = new Thread(new FireManagerThread(fire));
                    FireManager.getInstance().addThread(fireThread, id);
                    FireManager.getInstance().addResources(new Object(), id);
                    FireManager.getInstance().getFireThreads(id).start();
                    //FireManager.getInstance().getFireThreads(id).join();
                    // On attends que le feu soit eteint pour en refaire un.
                    // On pourra enlever le thread join quand on voudra continuer la génération des feu pendant une opération
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public PointDTO getRandomLocationFromSensor(List<SensorDTO> sensors) {
        Random rand = new Random();

        return sensors.get(rand.nextInt(sensors.size())).location;
    }

    public boolean generateFire() {
        Random rand = new Random();
        boolean result = false;
        int number = rand.nextInt(100); // Pourcentage
        System.out.println(number);
        if (number > 70) {
            result = true;
        }
        return result;
    }

}
