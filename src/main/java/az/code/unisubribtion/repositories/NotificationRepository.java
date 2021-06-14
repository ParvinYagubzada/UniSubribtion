package az.code.unisubribtion.repositories;


import az.code.unisubribtion.models.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface NotificationRepository extends PagingAndSortingRepository<Notification, Long> {

    Page<Notification> getAllByUserId(Long userId, Pageable page);

    Notification getNotificationByUserIdAndId(Long userId, Long id);

    List<Notification> getByUserIdOrderByTime(Long id);
}
