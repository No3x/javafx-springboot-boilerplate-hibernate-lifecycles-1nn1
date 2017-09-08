package hello.data.repository;

import hello.data.model.PersonTeam;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by No3x on 08.09.2017.
 */
public interface PersonTeamRepository extends CrudRepository<PersonTeam, Long> {
}
