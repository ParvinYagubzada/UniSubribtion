package az.code.unisubribtion.services;

import az.code.unisubribtion.models.Notification;
import az.code.unisubribtion.models.Paging;
import az.code.unisubribtion.repositories.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;

import static az.code.unisubribtion.utils.Util.getResult;
import static az.code.unisubribtion.utils.Util.preparePage;

@Service
public class NotificationServiceImpl implements NotificationService{
    NotificationRepository notificationRepo;

    public NotificationServiceImpl(NotificationRepository notificationRepo) {
        this.notificationRepo = notificationRepo;
    }

    @Override
    public Notification createNotification(Long userId, Long subscriptionId, Notification notification) {
        return notificationRepo.save(notification.toBuilder()
                .userId(userId)
                .subscriptionId(subscriptionId)
                .context("Your subscription will be updated in one day")
                .build());
    }

    @Override
    public Paging<Notification> getAllNotifications(Long userId, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = preparePage(pageNo, pageSize, sortBy);
        Page<Notification> pageResult = notificationRepo.getAllByUserId(userId, paging);
        return new Paging<Notification>().toBuilder()
                .pageCount((long) pageResult.getTotalPages())
                .pageSize(pageResult.getTotalElements())
                .subscriptions(getResult(pageResult))
                .build();
    }

    @Override
    public Long deleteNotification(Long userId, Long notificationId) {
        Notification result = notificationRepo.getNotificationByUserIdAndId(userId, notificationId);
        notificationRepo.delete(result);
        return result.getId();
    }
}
