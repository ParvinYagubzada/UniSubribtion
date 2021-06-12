package az.code.unisubribtion.services;

import az.code.unisubribtion.models.Notification;
import az.code.unisubribtion.models.Paging;
import az.code.unisubribtion.models.Subscription;
import az.code.unisubribtion.repositories.NotificationRepository;
import az.code.unisubribtion.repositories.SubscriptionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static az.code.unisubribtion.utils.Util.getResult;
import static az.code.unisubribtion.utils.Util.preparePage;

@Service
public class NotificationServiceImpl implements NotificationService {
    NotificationRepository notificationRepo;
    SubscriptionRepository subRepo;

    public NotificationServiceImpl(NotificationRepository notificationRepo, SubscriptionRepository subRepo) {
        this.notificationRepo = notificationRepo;
        this.subRepo = subRepo;
    }

    //TODO: hasSeen endpoint!
    @Scheduled(cron = "0 0 12 * * ?")
    public void createNotifications() {
        List<Subscription> subscriptions = subRepo.findSubscriptionsHasNotificationTrueAndActiveTrue();
        List<Notification> notifications = new LinkedList<>();
        subscriptions.forEach(subscription -> {
            int period = Period.between(subscription.getNextPaymentDay().toLocalDate(),
                    subscription.getLastPaymentDay().toLocalDate()).getDays();
            if (period <= 5) {
                Notification basic = Notification.builder()
                        .context("You have " + period + "")
                        .subscriptionId(subscription.getId())
                        .subscriptionId(subscription.getUserId())
                        .time(LocalDateTime.now())
                        .build();
                if (period > 1) {
                    basic.toBuilder().name("Upcoming").build();
                } else if (period == 1) {
                    basic.toBuilder().name("Urgent").build();
                } else if (period == 0) {
                    basic.toBuilder().name("Payment day").build();
                } else {
                    basic.toBuilder().name("Expired").build();
                }
                notifications.add(basic);
            }
        });
        notificationRepo.saveAll(notifications);
    }

    //TODO: Create endpoint!
    @Override
    public Pair<Long, List<Notification>> getSimpleNotifications(Long userId, Long limit) {
        List<Notification> notifications = notificationRepo.getByUserIdAndHasSeenFalse(userId);
        return Pair.of((long) notifications.size(), notifications.stream().limit(limit).collect(Collectors.toList()));
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
