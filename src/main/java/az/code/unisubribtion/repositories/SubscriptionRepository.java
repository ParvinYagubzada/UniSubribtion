package az.code.unisubribtion.repositories;

import az.code.unisubribtion.models.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SubscriptionRepository extends PagingAndSortingRepository<Subscription, Long> {
    List<Subscription> findSubscriptionsByUserId (Long userId, Pageable pageable);
    List<Subscription> findSubscriptionsByCategoryId (Long categoryId, Pageable pageable);
    List<Subscription> findSubscriptionsByGroupId (Long groupId, Pageable pageable);

}
