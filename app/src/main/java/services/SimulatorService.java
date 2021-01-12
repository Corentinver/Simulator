package services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import dto.internal.*;

@Service
public class SimulatorService {
    
    public SimulatorService(){
        restTemplate = new RestTemplate();
    }
    
    @Autowired
    public RestTemplate restTemplate;
    
    public List<FireStationDTO> getAllFireStation()
    {
		ResponseEntity<FireStationDTO[]> responseEntity = restTemplate.getForEntity("http://localhost:8080/allFireStation",FireStationDTO[].class);
        return Arrays.asList(responseEntity.getBody());
    }

    public List<FireFighterDTO> getAllFireFighter()
    {
        ResponseEntity<FireFighterDTO[]> responseEntity = restTemplate.getForEntity("http://localhost:8080/allFireFighter",FireFighterDTO[].class);
        return Arrays.asList(responseEntity.getBody());
    }

    public List<VehicleDTO> getAllVehicle()
    {
        ResponseEntity<VehicleDTO[]> responseEntity = restTemplate.getForEntity("http://localhost:8080/allVehicle",VehicleDTO[].class);
        return Arrays.asList(responseEntity.getBody());
    }
    
    public List<SensorDTO> getAllSensor()
    {
        ResponseEntity<SensorDTO[]> responseEntity = restTemplate.getForEntity("http://localhost:8080/allSensor",SensorDTO[].class);
        return Arrays.asList(responseEntity.getBody());
    }

    public List<TypeFireDTO> getAllTypeFire()
    {
        ResponseEntity<TypeFireDTO[]> responseEntity = restTemplate.getForEntity("http://localhost:8080/allTypeFire", TypeFireDTO[].class);
        return Arrays.asList(responseEntity.getBody());
    }
}
