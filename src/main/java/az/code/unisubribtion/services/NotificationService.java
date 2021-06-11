package az.code.unisubribtion.services;

import az.code.unisubribtion.models.Notification;

import java.util.List;

public interface NotificationService {

    Notification createNotification(Long userId, Long subscriptionId, Notification notification);

    List<Notification> getAllNotifications(Long userId);

    Long deleteNotification(Long userId, Long subscriptionId, Long notificationId);
}
