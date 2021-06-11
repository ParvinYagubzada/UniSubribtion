package az.code.unisubribtion.services;

import az.code.unisubribtion.models.Notification;
import az.code.unisubribtion.repositories.NotificationRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService{
    NotificationRepository notificationRepo;

    public NotificationServiceImpl(NotificationRepository notificationRepo) {
        this.notificationRepo = notificationRepo;
    }

    @Override
    public Notification createNotification(Long userId, Long subscriptionId, Notification notification) {
        return notificationRepo.save(notification.toBuilder().userId(userId).subscriptionId(subscriptionId).context("Your subscription will be updated in one day").build());
    }

    @Override
    public List<Notification> getAllNotifications(Long userId) {
        return Streamable.of(notificationRepo.getAllByUserId(userId)).toList();
    }

    @Override
    public Long deleteNotification(Long userId, Long subscriptionId, Long notificationId) {
        Notification result = notificationRepo.getNotificationByUserIdAndSubscriptionIdAndId(userId, subscriptionId, notificationId);
        notificationRepo.delete(result);
        return result.getId();
    }
}
