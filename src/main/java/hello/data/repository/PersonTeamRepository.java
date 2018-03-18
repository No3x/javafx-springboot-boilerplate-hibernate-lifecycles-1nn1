package hello.data.repository;

import hello.data.model.PersonTeam;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by No3x on 08.09.2017.
 */
public interface PersonTeamRepository extends CrudRepository<PersonTeam, Long> {
    @Query(value = "SELECT * FROM person_team t where t.person_id = ?1", nativeQuery=true)
    List<PersonTeam> findAllByPersonId(Long id);
    @Query(value = "SELECT * FROM person_team t where t.person_id = ?1 AND t.team_id = ?2", nativeQuery=true)
    Optional<PersonTeam> findOneByPersonAndTeamId(Long person, Long team);
}
