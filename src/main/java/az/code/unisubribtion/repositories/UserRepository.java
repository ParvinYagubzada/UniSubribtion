package az.code.unisubribtion.repositories;

import az.code.unisubribtion.models.Subscription;
import az.code.unisubribtion.models.SubscriptionUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<SubscriptionUser, Long> {

    SubscriptionUser findUserByUsername(String username);

    SubscriptionUser findUserByEmail(String email);
}
