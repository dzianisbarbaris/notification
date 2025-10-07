package by.savik.notification.controller;

import by.savik.notification.dto.FarmResponse;
import by.savik.notification.dto.FruitResponse;
import by.savik.notification.dto.VegetableResponse;
import by.savik.notification.service.NotificationManagerService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationManagerService notificationManagerService;

    @Autowired
    public NotificationController(NotificationManagerService notificationManagerService) {
        this.notificationManagerService = notificationManagerService;
    }

    @PostMapping("/email")
    public ResponseEntity<Map<String, String>> sendEmailNotification(
            @RequestBody Map<String, String> request){
        String message = request.get("message");
        notificationManagerService.sendEmailNotification(message);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("type", "email");
        response.put("message", "Email notification set");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/sms")
    public ResponseEntity<Map<String, String>> sendSmsNotification(
            @RequestBody Map<String, String> request){
        String message = request.get("message");
        notificationManagerService.sendSmsNotification(message);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("type", "sms");
        response.put("message", "Sms notification set");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/push")
    public ResponseEntity<Map<String, String>> sendPushNotification(
            @RequestBody Map<String, String> request){
        String message = request.get("message");
        notificationManagerService.sendPushNotification(message);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("type", "push");
        response.put("message", "Push notification set");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/all")
    public ResponseEntity<Map<String, String>> sendAllNotification(
            @RequestBody Map<String, String> request){
        String message = request.get("message");
        notificationManagerService.sendNotificationToAll(message);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("type", "all");
        response.put("message", "Notifications send to all services");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/by-type/{serviceType}")
    public ResponseEntity<Map<String, String>> sendNotificationByType(
            @PathVariable String serviceType,
            @RequestBody Map<String, String> request){
        String message = request.get("message");
        notificationManagerService.sendNotificationByType(serviceType, message);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("type", serviceType);
        response.put("message", "Notification send to " + serviceType);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/create-farm")
    public ResponseEntity<Map<String, Object>> createFarmAndNotify(
            @RequestBody Map<String, String> request){
        String name = request.get("name");
        String location = request.get("location");
        String message = request.get("message");

        if (name == null || location == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "farmName and location is required");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        try {
            FarmResponse farmResponse = notificationManagerService.createFarm(name, location, message);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Notification send to all services");
            response.put("farm", farmResponse);

            return ResponseEntity.ok(response);
        } catch (Exception error){
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Failed to create farm or send notification: " + error.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @PostMapping("/randomFruit")
    public ResponseEntity<Map<String, Object>> createRandomFruitAndNotify(
            @Parameter(description = "Farm ID to assign the random fruit to", required = true)
            @RequestParam Long farmId,
            @RequestBody Map<String, String> request){
        String message = request.get("message");
        try {
            FruitResponse fruitResponse = notificationManagerService.createRandomFruit(farmId, message);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Notification send to all services");
            response.put("fruit", fruitResponse);

            return ResponseEntity.ok(response);
        } catch (Exception error) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Failed to create fruit or send notification:" + error.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @PostMapping("/randomCountFruit")
    public ResponseEntity<Map<String, Object>> createRandomCountFruitAndNotify(
            @Parameter(description = "Farm ID to assign the random fruits to", required = true)
            @RequestParam Long farmId,
            @RequestBody Map<String, String> request){
        String message = request.get("message");
        try {
            List<FruitResponse> fruitResponse = notificationManagerService.createRandomCountFruit(farmId, message);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Notification send to all services");
            response.put("fruits", fruitResponse);

            return ResponseEntity.ok(response);
        } catch (Exception error) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Failed to create fruits or send notification:" + error.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @PostMapping("/randomVegetable")
    public ResponseEntity<Map<String, Object>> createRandomVegetableAndNotify(
            @Parameter(description = "Farm ID to assign the random vegetable to", required = true)
            @RequestParam Long farmId,
            @RequestBody Map<String, String> request){
        String message = request.get("message");
        try {
            VegetableResponse vegetableResponse = notificationManagerService.createRandomVegetable(farmId, message);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Notification send to all services");
            response.put("vegetable", vegetableResponse);

            return ResponseEntity.ok(response);
        } catch (Exception error) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Failed to create vegetable or send notification:" + error.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @PostMapping("/randomCountVegetable")
    public ResponseEntity<Map<String, Object>> createRandomCountVegetableAndNotify(
            @Parameter(description = "Farm ID to assign the random vegetables to", required = true)
            @RequestParam Long farmId,
            @RequestBody Map<String, String> request){
        String message = request.get("message");
        try {
            List<VegetableResponse> vegetableResponse = notificationManagerService.createRandomCountVegetable(farmId, message);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Notification send to all services");
            response.put("vegetables", vegetableResponse);

            return ResponseEntity.ok(response);
        } catch (Exception error) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Failed to create vegetable or send notification:" + error.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}
