package az.code.unisubribtion.services;

import az.code.unisubribtion.dtos.GroupDTO;
import az.code.unisubribtion.models.Group;
import az.code.unisubribtion.models.Subscription;
import az.code.unisubribtion.repositories.GroupRepository;
import az.code.unisubribtion.repositories.SubscriptionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subRepo;
    private final GroupRepository groupRepo;

    public SubscriptionServiceImpl(SubscriptionRepository subRepo, GroupRepository groupRepo) {
        this.subRepo = subRepo;
        this.groupRepo = groupRepo;
    }

    @Override
    public List<Subscription> getSubscriptionsByUserId(Long userId, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = preparePage(pageNo, pageSize, sortBy);
        Page<Subscription> pageResult = subRepo.findSubscriptionsByUserId(userId, paging);
        return getResult(pageResult);
    }

//    @Override
//    public List<Subscription> getSubscriptionsByCategoryId(Long userId, Long categoryId, Integer pageNo, Integer pageSize, String sortBy) {
//        Pageable paging = preparePage(pageNo, pageSize, sortBy);
//        Page<Subscription> pageResult = subRepo.findSubscriptionsByUserIdAndCategoryId(userId, categoryId, paging);
//        return getResult(pageResult);
//    }

    @Override
    public List<Subscription> getSubscriptionsByGroupId(Long userId, Long groupId, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = preparePage(pageNo, pageSize, sortBy);
        Page<Subscription> pageResult = subRepo.findSubscriptionsByUserIdAndGroupId(userId, groupId, paging);
        return getResult(pageResult);
    }


    @Override
    public List<Group> getAllGroups(Long userId) {
        return Streamable.of(groupRepo.getGroupsByUserId(userId)).toList();
    }

    @Override
    public List<GroupDTO> getAllGroupDTOs(Long userId) {
        List<GroupDTO> groupDTOS = new LinkedList<>();
        for (Group group : groupRepo.findAll()) {
            GroupDTO dto = new GroupDTO();
            dto.setId(group.getId());
            dto.setName(group.getName());
            List<Subscription> subs = subRepo.findSubscriptionsByUserIdAndGroupId(userId, group.getId());
            dto.setSubscriptionCount(subs.size());
            subs.sort(Comparator.comparing(Subscription::getSubscriptionTime));
            dto.setShortestDeadline(subs.get(0).getSubscriptionTime());
            dto.setTotalPrices(subs.stream().mapToDouble(Subscription::getPrice).sum());
            groupDTOS.add(dto);
        }
        return groupDTOS;
    }

    @Override
    public Long deleteByUserIdAndGroupId(Long userId, Long groupId) {
        Group result = groupRepo.getGroupByUserIdAndGroupId(userId,groupId);
        groupRepo.deleteByUserIdAndGroupId(userId, groupId);
        return result.getId();
    }


    @Override
    public Subscription createSubscription(Subscription subscription) {
        return subRepo.save(subscription);
    }

    @Override
    public Group createGroup(Group group) {
        return groupRepo.save(group);
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
