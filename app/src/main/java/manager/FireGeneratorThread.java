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
        fires = new ArrayList<FireDTO>();
        fireStations = simulatorService.getAllFireStation();
        fireFighters = simulatorService.getAllFireFighter();
        vehicles = simulatorService.getAllVehicle();
        sensors = simulatorService.getAllSensor();
        typeFires = simulatorService.getAllTypeFire();
    }

    @Override
    public void run() {
        while (!generated) {
            try {
                Thread.sleep(3 * 1000);

                if (generateFire()) {
                    PointDTO locationFire = getRandomLocationFromSensor(sensors);
                    Random rand = new Random();

                    String idTypeFire = typeFires.get(rand.nextInt(typeFires.size())).id;
                    System.out.println(locationFire);

                    FireDTO fire = new FireDTO();
                    fire.setLocation(locationFire);
                    fire.setTypeFire(idTypeFire);
                    fire.setState(FireDTO.stateFire.Initialize);
                    fire.setIntensity(10);
                    fire.setSize(2);
                    String id = restService.newFire(fire);
                    fire.setId(id);
                    fires.add(fire);
                    FireManager.getInstance();
                    Thread thread = new Thread(new FireManagerThread(fire));
                   
                    thread.start();
                    thread.join();
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
            generated = false;
            result = true;
        }
        return result;
    }

}
