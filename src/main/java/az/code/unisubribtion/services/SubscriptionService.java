package az.code.unisubribtion.services;

import az.code.unisubribtion.dtos.GroupDTO;
import az.code.unisubribtion.models.Group;
import az.code.unisubribtion.models.Subscription;

import java.util.List;

public interface SubscriptionService {

    List<Subscription> getSubscriptionsByUserId(Long userId, Integer pageNo, Integer pageSize, String sortBy);

    List<Subscription> getSubscriptionsByGroupId(Long userId, Long groupId, Integer pageNo, Integer pageSize, String sortBy);

    List<Group> getAllGroups(Long userId);

    List<GroupDTO> getAllGroupDTOs(Long userId);

    Long deleteSub(Long userId, Long subscriptionId);

    Long deleteGroup(Long userId, Long groupId);

    Subscription createSubscription(Long userId, Subscription subscription);

    Group createGroup(Long userId, Group group);

    Subscription updateSubscription(Long userId, Subscription subscription);

    Group updateGroup(Long userId, Group group);

    Long stopSubscription(Long subscriptionId);
}
