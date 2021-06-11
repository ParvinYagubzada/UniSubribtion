package az.code.unisubribtion.services;

import az.code.unisubribtion.dtos.CategoryDTO;
import az.code.unisubribtion.dtos.GroupDTO;
import az.code.unisubribtion.models.Category;
import az.code.unisubribtion.models.Group;
import az.code.unisubribtion.models.Subscription;

import java.util.List;

public interface SubscriptionService {

    List<Subscription> getSubscriptionsByUserId(Long userId, Integer pageNo, Integer pageSize, String sortBy);

    List<Subscription> getSubscriptionsByCategoryId(Long userId, Long categoryId, Integer pageNo, Integer pageSize, String sortBy);

    List<Subscription> getSubscriptionsByGroupId(Long userId, Long groupId, Integer pageNo, Integer pageSize, String sortBy);

    List<Group> getAllGroups();

//    List<Category> getAllCategories(Long userId);

    List<GroupDTO> getAllGroupDTOs();

//    List<CategoryDTO> getAllCategoryDTOs(Long userId);

    Subscription createSubscription(Subscription subscription);

    Group createGroup(Group group);
}
