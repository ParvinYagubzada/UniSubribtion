package az.code.unisubribtion.services;

import az.code.unisubribtion.models.Notification;
import az.code.unisubribtion.models.Paging;

import java.util.List;

public interface NotificationService {

    Notification createNotification(Long userId, Long subscriptionId, Notification notification);

    Paging<Notification> getAllNotifications(Long userId, Integer pageNo, Integer pageSize, String sortBy);

    Long deleteNotification(Long userId, Long notificationId);
}
