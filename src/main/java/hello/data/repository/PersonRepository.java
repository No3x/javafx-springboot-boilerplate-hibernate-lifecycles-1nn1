package hello.data.repository;

import hello.data.model.Person;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by No3x on 11.08.2017.
 */
public interface PersonRepository extends CrudRepository<Person, Long> {
}
