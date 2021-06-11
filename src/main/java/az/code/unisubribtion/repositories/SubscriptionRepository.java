package az.code.unisubribtion.repositories;

import az.code.unisubribtion.models.Subscription;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SubscriptionRepository extends PagingAndSortingRepository<Subscription, Long> {

}
