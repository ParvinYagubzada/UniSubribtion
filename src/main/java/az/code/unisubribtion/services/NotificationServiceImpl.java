package az.code.unisubribtion.services;

import az.code.unisubribtion.exceptions.UserDoesNotExists;
import az.code.unisubribtion.models.Notification;
import az.code.unisubribtion.models.Subscription;
import az.code.unisubribtion.models.SubscriptionUser;
import az.code.unisubribtion.repositories.NotificationRepository;
import az.code.unisubribtion.repositories.SubscriptionRepository;
import az.code.unisubribtion.repositories.UserRepository;
import az.code.unisubribtion.utils.Paging;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static az.code.unisubribtion.utils.Util.*;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepo;
    private final SubscriptionRepository subRepo;
    private final UserRepository userRepo;
    private final JavaMailSender sender;

    public NotificationServiceImpl(NotificationRepository notificationRepo, SubscriptionRepository subRepo, UserRepository userRepo, JavaMailSender sender) {
        this.notificationRepo = notificationRepo;
        this.subRepo = subRepo;
        this.userRepo = userRepo;
        this.sender = sender;
    }

    @Scheduled(cron = "0 0 8 * * ?")
    public void createNotifications() {
        List<Subscription> subscriptions = subRepo.findSubscriptionsByActiveTrueAndHasNotificationTrue();
        List<Notification> notifications = new LinkedList<>();
        subscriptions.forEach(subscription -> {
            int period = Period.between(subscription.getNextPaymentDay().toLocalDate(),
                    subscription.getLastPaymentDay().toLocalDate()).getDays();
            if (period <= 5) {
                Notification basic = Notification.builder()
                        .userId(subscription.getUserId())
                        .context("You have " + period + " days left on your " + subscription.getName() + " subscription.")
                        .subscriptionId(subscription.getId())
                        .hasSeen(false)
                        .time(LocalDateTime.now())
                        .build();
                if (period > 1) {
                    basic = basic.toBuilder().name("Upcoming").build();
                } else if (period == 1) {
                    basic = basic.toBuilder().name("Urgent").build();
                } else if (period == 0) {
                    basic = basic.toBuilder().name("Payment day").build();
                } else {
                    LocalDateTime next = subscription.getNextPaymentDay().plus(subscription
                                    .getDuration()
                                    .getValue(),
                            convertUnit(subscription.getDuration().getUnit()));
                    subscription.setLastPaymentDay(subscription.getNextPaymentDay());
                    subscription.setNextPaymentDay(next);
                    basic.toBuilder().name("Expired").build();
                }
                notifications.add(basic);
                sendNotificationEmail(basic);
            }
        });
        notificationRepo.saveAll(notifications);
    }

    @Override
    public Pair<Long, List<Notification>> getSimpleNotifications(Long userId, Long limit) {
        List<Notification> notifications = notificationRepo.getByUserIdOrderByTime(userId);
        return Pair.of((long) notifications.size(), notifications.stream()
                .sorted((base, second) -> -base.getTime().compareTo(second.getTime()))
                .limit(limit)
                .collect(Collectors.toList()));
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

    @Override
    public void sendNotificationEmail(Notification notification) {
        Optional<SubscriptionUser> user = userRepo.findById(notification.getUserId());
        if (user.isEmpty())
            throw new UserDoesNotExists();
        String email = user.get().getEmail();
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setSubject("Subscription tracker notification: " + notification.getName());
        mail.setText(notification.getContext());
        sender.send(mail);
    }

    @Override
    public void setAllNotifications(Long userId) {
        notificationRepo.saveAll(notificationRepo
                .getByUserIdOrderByTime(userId)
                .stream()
                .peek(notification -> notification.setHasSeen(true))
                .collect(Collectors.toList()));
    }

    public void setSingleNotification(Long userId, Long notfId) {
        Optional<Notification> notification = notificationRepo.findById(notfId);
        if (notification.isPresent()) {
            notification.get().setHasSeen(true);
            notificationRepo.save(notification.get());
        }
    }
}
