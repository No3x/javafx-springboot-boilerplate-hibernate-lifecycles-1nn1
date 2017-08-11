package hello.data.repository;

/**
 * Created by No3x on 08.08.2017.
 */
import hello.data.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findByLastName(String lastName);
}
