package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.internal.FireDTO;
import dto.internal.OperationDTO;

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
			ResponseEntity<FireDTO> fEntity = restTemplate.postForEntity("http://localhost:8080/newFire", request, FireDTO.class);
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

	public void newOperation(OperationDTO operationDTO){
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

}
