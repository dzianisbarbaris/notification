package by.savik.notification.service;

import org.springframework.stereotype.Service;

@Service("smsNotificationService")
public class SmsNotificationService implements NotificationServiceInterface{
    @Override
    public void sendNotification(String message) {
        System.out.println("Sending sms Notification " + message);
    }

    @Override
    public String getServiceType() {
        return "SMS";
    }
}
