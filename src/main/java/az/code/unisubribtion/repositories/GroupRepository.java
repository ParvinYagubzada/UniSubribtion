package az.code.unisubribtion.repositories;

import az.code.unisubribtion.models.Group;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GroupRepository extends CrudRepository<Group, Long> {
    List<Group> getGroupsByUserId(Long id);
    Long deleteByUserIdAndGroupId(Long userId, Long groupId);
    Group getGroupByUserIdAndGroupId(Long userId, Long groupId);
}
