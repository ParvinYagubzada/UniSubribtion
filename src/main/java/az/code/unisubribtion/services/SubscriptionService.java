package az.code.unisubribtion.services;

import az.code.unisubribtion.models.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import java.util.List;

public interface SubscriptionService {

    List<Subscription> getSubscriptionsByUserId(Long userId, Integer pageNo, Integer pageSize, String sortBy);

    List<Subscription> getSubscriptionsByCategoryId(Long userId, Long categoryId, Integer pageNo, Integer pageSize, String sortBy);

    List<Subscription> getSubscriptionsByGroupId(Long userId, Long groupId, Integer pageNo, Integer pageSize, String sortBy);
}
