package az.code.unisubribtion.repositories;

import az.code.unisubribtion.models.Group;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GroupRepository extends CrudRepository<Group, Long> {
    List<Group> getGroupsByUserId(Long id);

    Group getGroupByUserIdAndId(Long userId, Long groupId);
}
