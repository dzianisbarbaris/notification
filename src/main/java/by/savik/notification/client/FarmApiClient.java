package by.savik.notification.client;

import by.savik.notification.dto.FarmRequest;
import by.savik.notification.dto.FarmResponse;
import by.savik.notification.dto.FruitResponse;
import by.savik.notification.dto.VegetableResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;

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
            ResponseEntity<FruitResponse> response = restTemplate.exchange(
                    farmApiBaseUrl + "/api/fruits/randomFruit?farmId=" + farmId,
                    HttpMethod.POST,
                    HttpEntity.EMPTY,
                    FruitResponse.class
            );

            FruitResponse fruitResponse = response.getBody();
            assert fruitResponse != null;
            System.out.println("Random fruit created successfully: " + fruitResponse.getName());
            return fruitResponse;
        } catch (Exception error) {
            System.err.println("Error creating random fruit: " + error.getMessage());
            throw new RuntimeException("Failed to create random fruit", error);
        }
    }

    public List<FruitResponse> createRandomCountFruit(Long farmId){
        try {
            ResponseEntity<List<FruitResponse>> response = restTemplate.exchange(
                    farmApiBaseUrl + "/api/fruits/randomCountFruit?farmId=" + farmId,
                    HttpMethod.POST,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<List<FruitResponse>>() {
                    }
            );

            List<FruitResponse> fruitResponse = response.getBody();
            assert fruitResponse != null;
            for (FruitResponse v : fruitResponse) {
                System.out.println("Random fruits created successfully: " + v.getName());
            }
            return fruitResponse;
        } catch (Exception error) {
            System.err.println("Error creating random fruits: " + error.getMessage());
            throw new RuntimeException("Failed to create random fruits", error);
        }
    }

    public VegetableResponse createRandomVegetable(Long farmId){
        try {
            ResponseEntity<VegetableResponse> response = restTemplate.exchange(
                    farmApiBaseUrl + "/api/vegetables/randomVegetable?farmId=" + farmId,
                    HttpMethod.POST,
                    HttpEntity.EMPTY,
                    VegetableResponse.class
            );

            VegetableResponse vegetableResponse = response.getBody();
            assert vegetableResponse != null;
            System.out.println("Random vegetable created successfully: " + vegetableResponse.getName());
            return vegetableResponse;
        } catch (Exception error) {
            System.err.println("Error creating random vegetable: " + error.getMessage());
            throw new RuntimeException("Failed to create random vegetable", error);
        }
    }

    public List<VegetableResponse> createRandomCountVegetable(Long farmId){
        try {
            ResponseEntity<List<VegetableResponse>> response = restTemplate.exchange(
                    farmApiBaseUrl + "/api/vegetables/randomCountVegetable?farmId=" + farmId,
                    HttpMethod.POST,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<List<VegetableResponse>>() {
                    }
            );

            List<VegetableResponse> vegetableResponse = response.getBody();
            assert vegetableResponse != null;
            for (VegetableResponse v : vegetableResponse) {
                System.out.println("Random vegetables created successfully: " + v.getName());
            }

            return vegetableResponse;
        } catch (Exception error) {
            System.err.println("Error creating random vegetables: " + error.getMessage());
            throw new RuntimeException("Failed to create random vegetables", error);
        }
    }
}
