package az.code.unisubribtion.repositories;


import org.springframework.data.repository.PagingAndSortingRepository;

import az.code.unisubribtion.models.Notification;
import java.util.List;

public interface NotificationRepository extends PagingAndSortingRepository<Notification, Long> {
    List<Notification> getAllByUserId(Long userId);
    Notification getNotificationByUserIdAndSubscriptionIdAndId(Long userId, Long subscriptionId, Long notificationId);
}
