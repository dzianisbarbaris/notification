package by.savik.notification.client;

import by.savik.notification.dto.FarmRequest;
import by.savik.notification.dto.FarmResponse;
import by.savik.notification.dto.FruitResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FarmApiClient {

    private final RestTemplate restTemplate;

    @Value("${farm.api.base-url:http://localhost:8080}")
    private String farmApiBaseUrl;

    public FarmApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public FarmResponse createFarm(FarmRequest farmRequest){
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<FarmRequest> requestHttpEntity = new HttpEntity<>(farmRequest, headers);
            ResponseEntity<FarmResponse> response = restTemplate.exchange(
                    farmApiBaseUrl + "/api/farms",
                    HttpMethod.POST,
                    requestHttpEntity,
                    FarmResponse.class
            );

            FarmResponse farmResponse = response.getBody();
            System.out.println("Farm created successfully: " + farmResponse.getName());
            return farmResponse;
        } catch (Exception error) {
            System.err.println("Error creating farm: " + error.getMessage());
            throw new RuntimeException("Failed to create farm", error);
        }
    }

    public FruitResponse createRandomFruit(Long farmId){
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Long> requestHttpEntity = new HttpEntity<>(farmId);
            ResponseEntity<FruitResponse> response = restTemplate.exchange(
                    farmApiBaseUrl + "/api/fruits/randomFruit",
                    HttpMethod.POST,
                    requestHttpEntity,
                    FruitResponse.class
            );

            FruitResponse fruitResponse = response.getBody();
            System.out.println("Random fruit created successfully: " + fruitResponse.getName());
            return fruitResponse;
        } catch (Exception error) {
            System.err.println("Error creating random fruit: " + error.getMessage());
            throw new RuntimeException("Failed to create random fruit", error);
        }
    }

}
