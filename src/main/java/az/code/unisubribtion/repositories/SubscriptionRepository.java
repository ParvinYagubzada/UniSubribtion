package az.code.unisubribtion.repositories;

import az.code.unisubribtion.models.Category;
import az.code.unisubribtion.models.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SubscriptionRepository extends PagingAndSortingRepository<Subscription, Long> {
    Page<Subscription> findSubscriptionsByUserId(Long id, Pageable pageable);

    Page<Subscription> findSubscriptionsByUserIdAndCategoryId(Long userId, Long category, Pageable pageable);

    Page<Subscription> findSubscriptionsByUserIdAndGroupId(Long userId, Long groupId, Pageable pageable);

}
