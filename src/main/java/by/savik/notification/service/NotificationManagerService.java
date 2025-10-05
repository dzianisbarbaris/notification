package by.savik.notification.service;

import by.savik.notification.client.FarmApiClient;
import by.savik.notification.dto.FarmRequest;
import by.savik.notification.dto.FarmResponse;
import by.savik.notification.dto.FruitResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NotificationManagerService {

    private final NotificationServiceInterface smsService;
    private final NotificationServiceInterface emailService;
    private final NotificationServiceInterface pushService;
    private final List<NotificationServiceInterface> allNotificationServices;
    private final Map<String, NotificationServiceInterface> notificationServiceMap;
    private final FarmApiClient farmApiClient;

    @Autowired
    public NotificationManagerService(@Qualifier("smsNotificationService") NotificationServiceInterface smsService,
                                      @Qualifier("emailNotificationService") NotificationServiceInterface emailService,
                                      @Qualifier("pushNotificationService") NotificationServiceInterface pushService,
                                      List<NotificationServiceInterface> allNotificationServices,
                                      Map<String, NotificationServiceInterface> notificationServiceMap, FarmApiClient farmApiClient) {
        this.smsService = smsService;
        this.emailService = emailService;
        this.pushService = pushService;
        this.allNotificationServices = allNotificationServices;
        this.notificationServiceMap = notificationServiceMap;
        this.farmApiClient = farmApiClient;
    }

    public void sendSmsNotification(String message) {
        smsService.sendNotification(message);
    }

    public void sendEmailNotification(String message) {
        emailService.sendNotification(message);
    }

    public void sendPushNotification(String message) {
        pushService.sendNotification(message);
    }

    public void sendNotificationToAll(String message) {
        System.out.println("Sending notification to all services");
        allNotificationServices.forEach(service -> {
            System.out.println("Service type: " + service.getServiceType());
            service.sendNotification(message);
        });
    }

    public void sendNotificationByType(String serviceType, String message) {
        NotificationServiceInterface service = notificationServiceMap.get(serviceType);
        if (service != null) {
            service.sendNotification(message);
        } else
            System.out.println("Service type " + serviceType + " not found");
    }

    public FarmResponse createFarm(String name, String location, String message) {
        try {
            FarmRequest farmRequest = new FarmRequest(name, location);
            FarmResponse farmResponse = farmApiClient.createFarm(farmRequest);

            System.out.println("Farm created successfully: " + farmResponse.getName() + " at " + farmResponse.getLocation());
            String fullMessage = message + " - Farm: " + farmResponse.getName() + " (ID: " + farmResponse.getId() + ")";
            sendNotificationToAll(fullMessage);
            return farmResponse;
        } catch (Exception e) {
            System.err.println("Failed to create farm: " + e.getMessage());
            sendNotificationToAll("Failed to create farm: " + e.getMessage());
            throw e;
        }
    }

    public FruitResponse createRandomFruit(Long farmId, String message){
        try{
            FruitResponse fruitResponse = farmApiClient.createRandomFruit(farmId);

            System.out.println("Fruit created successfully: " + fruitResponse.getName() + " color: " + fruitResponse.getColor()
            + "weight: " + fruitResponse.getWeight());
            String fullMessage = message + " - Random Fruit: " + fruitResponse.getName() + " (ID: " + fruitResponse.getId() + ")";
            sendNotificationToAll(fullMessage);
            return fruitResponse;
        } catch (Exception e) {
            System.err.println("Failed to create random fruit: " + e.getMessage());
            sendNotificationToAll("Failed to create random fruit" + e.getMessage());
            throw e;
        }
    }


}
