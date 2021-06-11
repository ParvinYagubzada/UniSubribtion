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

    private final SubscriptionRepository repo;

    public SubscriptionServiceImpl(SubscriptionRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Subscription> getSubscriptionsByUserId(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = preparePage(pageNo, pageSize, sortBy);
        Page<Subscription> pageResult = repo.findSubscriptions(paging);
        return getResult(pageResult);
    }

    @Override
    public List<Subscription> getSubscriptionsByCategoryId(Long categoryId, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = preparePage(pageNo, pageSize, sortBy);
        Page<Subscription> pageResult = repo.findSubscriptionsByCategoryId(categoryId, paging);
        return getResult(pageResult);
    }

    @Override
    public List<Subscription> getSubscriptionsByGroupId(Long groupId, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = preparePage(pageNo, pageSize, sortBy);
        Page<Subscription> pageResult = repo.findSubscriptionsByGroupId(groupId, paging);
        return getResult(pageResult);
    }

    private Pageable preparePage(Integer pageNo, Integer pageSize, String sortBy) {
        return PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
    }

    private List<Subscription> getResult(Page<Subscription> pageResult) {
        if (pageResult.hasContent()) {
            return pageResult.getContent();
        } else {
            return new LinkedList<>();
        }
    }
}
