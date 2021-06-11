package az.code.unisubribtion.repositories;

import az.code.unisubribtion.models.Subscription;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.management.Notification;

public interface NotificationRepository extends PagingAndSortingRepository<Notification, Long> {

}
