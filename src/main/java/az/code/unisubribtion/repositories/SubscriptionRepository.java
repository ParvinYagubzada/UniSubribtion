package az.code.unisubribtion.repositories;

import az.code.unisubribtion.models.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SubscriptionRepository extends PagingAndSortingRepository<Subscription, Long> {
    Page<Subscription> findSubscriptionsByUserId(Long id, Pageable pageable);

    Page<Subscription> findSubscriptionsByUserIdAndGroupId(Long userId, Long groupId, Pageable pageable);

    List<Subscription> findSubscriptionsByUserIdAndGroupId(Long userId, Long groupId);

    Subscription findSubscriptionByUserIdAndId(Long userId, Long id);

    Subscription findByUserIdAndId(Long id, Long subId);

    List<Subscription> findSubscriptionsByActiveTrueAndHasNotificationTrue();

    List<Subscription> findAllByUserIdAndNameLikeIgnoreCase(Long userId, String name);
}
