package az.code.unisubribtion.services;

import az.code.unisubribtion.dtos.GroupDTO;
import az.code.unisubribtion.exceptions.GroupIsNotEmpty;
import az.code.unisubribtion.models.*;
import az.code.unisubribtion.repositories.GroupRepository;
import az.code.unisubribtion.repositories.SubscriptionRepository;
import com.github.javafaker.Faker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static az.code.unisubribtion.utils.Util.*;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subRepo;
    private final GroupRepository groupRepo;

    public SubscriptionServiceImpl(SubscriptionRepository subRepo, GroupRepository groupRepo) {
        this.subRepo = subRepo;
        this.groupRepo = groupRepo;

        Faker faker = new Faker();
        for (int i = 1; i <= 10; i++) {
            Group group = new Group();
            group.setName(faker.name().username());
            group.setUserId(i+100L);
            groupRepo.save(group);
            for (int j = 0; j < 10; j++) {
                Duration duration = Duration.builder()
                        .unit(DateUnit.values()[faker.number().numberBetween(0, 4)])
                        .value((long) faker.number().numberBetween(1, 15))
                        .build();
                Subscription subscription = Subscription.builder()
                        .lastPaymentDay(LocalDateTime.now())
                        .userId(i + 100L)
                        .name(faker.animal().name())
                        .duration(duration)
                        .group(group)
                        .price(faker.number().randomDouble(3, 5, 100))
                        .hasNotification(false)
                        .active(true)
                        .build();
                subRepo.save(subscription);
            }
            //groupRepo.save(group);
        }
    }

    @Override
    public Paging<Subscription> getSubscriptionsByUserId(Long userId, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = preparePage(pageNo, pageSize, sortBy);
        Page<Subscription> pageResult = subRepo.findSubscriptionsByUserId(userId, paging);
        return new Paging<Subscription>().toBuilder()
                .pageCount((long) pageResult.getTotalPages())
                .pageSize(pageResult.getTotalElements())
                .subscriptions(getResult(pageResult))
                .build();
    }

    @Override
    public Paging<Subscription> getSubscriptionsByGroupId(Long userId, Long groupId, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = preparePage(pageNo, pageSize, sortBy);
        Page<Subscription> pageResult = subRepo.findSubscriptionsByUserIdAndGroupId(userId, groupId, paging);
        return new Paging<Subscription>().toBuilder()
                .pageCount((long) pageResult.getTotalPages())
                .pageSize(pageResult.getTotalElements())
                .subscriptions(getResult(pageResult))
                .build();
    }

    @Override
    public List<Group> getAllGroups(Long userId) {
        return Streamable.of(groupRepo.getGroupsByUserId(userId)).toList();
    }

    @Override
    public List<GroupDTO> getAllGroupDTOs(Long userId) {
        List<GroupDTO> groupDTOS = new LinkedList<>();
        for (Group group : groupRepo.getGroupsByUserId(userId)) {
            GroupDTO dto = new GroupDTO();
            dto.setId(group.getId());
            dto.setName(group.getName());
            List<Subscription> subs = subRepo.findSubscriptionsByUserIdAndGroupId(userId, group.getId());
            dto.setSubscriptionCount(subs.size());
            subs.sort(Comparator.comparing(Subscription::getLastPaymentDay));
            dto.setShortestDeadline(subs.get(0).getNextPaymentDay());
            dto.setTotalPrices(subs.stream().mapToDouble(Subscription::getPrice).sum());
            groupDTOS.add(dto);
        }
        return groupDTOS;
    }

    @Override
    public Long deleteSub(Long userId, Long subscriptionId) {
        Subscription result = subRepo.findSubscriptionByUserIdAndId(userId, subscriptionId);
        subRepo.delete(result);
        return result.getId();
    }

    @Override
    public Long deleteGroup(Long userId, Long groupId) {
        Group result = groupRepo.getGroupByUserIdAndId(userId,groupId);
        if (subRepo.findSubscriptionsByUserIdAndGroupId(userId, groupId).size() == 0) {
            groupRepo.delete(result);
            return result.getId();
        }
        throw new GroupIsNotEmpty();
    }

    @Override
    public Long deleteForceGroup(Long userId, Long groupId) {
        Group result = groupRepo.getGroupByUserIdAndId(userId,groupId);
        subRepo.deleteAll(subRepo.findSubscriptionsByUserIdAndGroupId(userId, groupId));
        groupRepo.delete(result);
        return result.getId();
    }

    @Override
    public Subscription createSubscription(Long userId, Subscription subscription) {
        LocalDateTime last = subscription.getLastPaymentDay().plus(subscription
                        .getDuration()
                        .getValue(),
                        convertUnit(subscription.getDuration().getUnit()));
        return subRepo.save(subscription.toBuilder().userId(userId).lastPaymentDay(last).build());
    }

    @Override
    public Group createGroup(Long userId, Group group) {
        return groupRepo.save(group.toBuilder().userId(userId).build());
    }

    @Override
    public Subscription updateSubscription(Long userId, Long subId, Subscription subscription) {
        return subRepo.save(subscription.toBuilder().id(userId).userId(userId).build());
    }

    @Override
    public Group updateGroup(Long userId, Long groupId, Group group) {
        return groupRepo.save(group.toBuilder().id(groupId).userId(userId).build());
    }

    @Override
    public Long stopSubscription(Long userId, Long subscriptionId) {
        Subscription result = subRepo.findByUserIdAndId(userId, subscriptionId);
        result.setActive(false);
        return result.getId();
    }
}
