package az.code.unisubribtion.services;

import az.code.unisubribtion.models.Notification;
import az.code.unisubribtion.utils.Paging;
import org.springframework.data.util.Pair;

import java.util.List;

public interface NotificationService {

    Pair<Long, List<Notification>> getSimpleNotifications(Long userId, Long limit);

    Paging<Notification> getAllNotifications(Long userId, Integer pageNo, Integer pageSize, String sortBy);

    Long deleteNotification(Long userId, Long notificationId);

    void sendNotificationEmail(Notification notification);

    void setAllNotifications(Long userId);

    void setSingleNotification(Long userId, Long notfId);
}
