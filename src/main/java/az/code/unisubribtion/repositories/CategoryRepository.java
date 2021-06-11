package az.code.unisubribtion.repositories;

import az.code.unisubribtion.models.Category;
import az.code.unisubribtion.models.Group;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
