package az.code.unisubribtion.services;

import az.code.unisubribtion.models.Subscription;
import az.code.unisubribtion.repositories.SubscriptionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    SubscriptionRepository repo;

    public SubscriptionServiceImpl(SubscriptionRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Subscription> getAllSubscriptions(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Subscription> pageResult = repo.findAll(paging);
        if (pageResult.hasContent()) {
            return pageResult.getContent();
        } else {
            return new LinkedList<>();
        }
    }
}
