package az.code.unisubribtion.services;

import az.code.unisubribtion.dtos.GroupDTO;
import az.code.unisubribtion.dtos.JsonSubDTO;
import az.code.unisubribtion.models.Group;
import az.code.unisubribtion.utils.Paging;
import az.code.unisubribtion.models.Subscription;
import az.code.unisubribtion.models.SubscriptionUser;

import javax.mail.MessagingException;
import java.util.List;

public interface SubscriptionService {

    Paging<Subscription> getSubscriptionsByUserId(Long userId, Integer pageNo, Integer pageSize, String sortBy);

    Paging<Subscription> getSubscriptionsByGroupId(Long userId, Long groupId, Integer pageNo, Integer pageSize, String sortBy);

    List<Group> getAllGroups(Long userId);

    List<GroupDTO> getAllGroupDTOs(Long userId);

    Long deleteSub(Long userId, Long subscriptionId);

    Long deleteGroup(Long userId, Long groupId);

    Subscription createSubscription(Long userId, JsonSubDTO subscription);

    Group createGroup(Long userId, Group group);

    Subscription updateSubscription(Long userId, Long subId, Subscription subscription);

    Group updateGroup(Long userId, Long groupId, Group group);

    Long stopSubscription(Long userId, Long subscriptionId);

    Long deleteForceGroup(Long userId, Long groupId);

    List<Subscription> search(Long userId, String name);

    void sendMail(SubscriptionUser user) throws MessagingException;
}
