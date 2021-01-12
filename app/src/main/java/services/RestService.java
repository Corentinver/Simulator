package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.internal.FireDTO;
import dto.internal.OperationDTO;
import dto.internal.PointDTO;
import dto.internal.RideDTO;

@Component
public class RestService {

	public RestService() {
		restTemplate = new RestTemplate();
	}

	@Autowired
	public RestTemplate restTemplate;

	public String newFire(FireDTO fire) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ObjectMapper objectMapper = new ObjectMapper();
		HttpEntity<String> request;
		String id = "";
		try {
			request = new HttpEntity<String>(objectMapper.writeValueAsString(fire), headers);
			ResponseEntity<FireDTO> fEntity = restTemplate.postForEntity("http://localhost:8080/newFire", request,
					FireDTO.class);
			id = fEntity.getBody().id;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return id;
	}

	public void updateFire(FireDTO fire) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ObjectMapper objectMapper = new ObjectMapper();
		HttpEntity<String> request;
		try {
			request = new HttpEntity<String>(objectMapper.writeValueAsString(fire), headers);
			restTemplate.postForEntity("http://localhost:8080/updateFire", request, FireDTO.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public void newOperation(OperationDTO operationDTO) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ObjectMapper objectMapper = new ObjectMapper();
		HttpEntity<String> request;
		try {
			request = new HttpEntity<String>(objectMapper.writeValueAsString(operationDTO), headers);
			restTemplate.postForEntity("http://localhost:8080/newOperation", request, OperationDTO.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public void updateOperation(OperationDTO operationDTO) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ObjectMapper objectMapper = new ObjectMapper();
		HttpEntity<String> request;
		try {
			request = new HttpEntity<String>(objectMapper.writeValueAsString(operationDTO), headers);
			restTemplate.postForEntity("http://localhost:8080/updateOperation", request, OperationDTO.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public void sendRide(RideDTO ride) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ObjectMapper objectMapper = new ObjectMapper();
		HttpEntity<String> request;
		try {
			request = new HttpEntity<String>(objectMapper.writeValueAsString(ride), headers);
			restTemplate.postForEntity("http://localhost:8080/sendRide", request, RideDTO.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public List<PointDTO> getRouteLocations(double startLatitude, double startLongitude, double endLatitude, double endLongitude)
			throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("startLatitude", String.valueOf(startLatitude));
		headers.add("startLongitude", String.valueOf(startLongitude));
		headers.add("endLatitude", String.valueOf(endLatitude));
		headers.add("endLongitude", String.valueOf(endLongitude));
		HttpEntity<String> request;
		ResponseEntity<PointDTO[]> responseEntity = null;

		request = new HttpEntity<String>(null, headers);
		responseEntity = restTemplate.exchange("http://localhost:8080/getRouteLocations", HttpMethod.GET, request, PointDTO[].class);
		return Arrays.asList(responseEntity.getBody());
	}

	public double getRouteDuration(double startLatitude, double startLongitude, double endLatitude, double endLongitude)
			throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("startLatitude", String.valueOf(startLatitude));
		headers.add("startLongitude", String.valueOf(startLongitude));
		headers.add("endLatitude", String.valueOf(endLatitude));
		headers.add("endLongitude", String.valueOf(endLongitude));
		HttpEntity<String> request;
		ResponseEntity<Double> responseEntity = null;

		request = new HttpEntity<String>(null, headers);
		responseEntity = restTemplate.exchange("http://localhost:8080/getRouteDuration", HttpMethod.GET, request, Double.class);
		return responseEntity.getBody();
	}

	public PointDTO getLocationByVehicule(String idVehicle)
    {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("idVehicle", idVehicle);
		HttpEntity<String> request;
		ResponseEntity<PointDTO> responseEntity = null;

		request = new HttpEntity<String>(null, headers);
		responseEntity = restTemplate.exchange("http://localhost:8080/vehicleLocation", HttpMethod.GET, request, PointDTO.class);
        return responseEntity.getBody();
	}
	
}
