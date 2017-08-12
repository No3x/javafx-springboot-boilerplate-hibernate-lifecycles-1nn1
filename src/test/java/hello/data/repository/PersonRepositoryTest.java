package hello.data.repository;

import hello.data.model.Person;
import hello.data.model.Team;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by No3x on 11.08.2017.
 * http://www.baeldung.com/spring-boot-testing
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Test
    public void whenFindOne_thenReturnPerson() {
        // given
        Person alex = new Person("alex");
        entityManager.persist(alex);
        entityManager.flush();

        // when
        Person found = personRepository.findOne(alex.getId());

        // then
        assertThat(found.getName(), equalToIgnoringWhiteSpace(alex.getName()));
    }

    @Test
    public void whenAddingANewTeamTroughPerson_thenNoException() {
        // given
        Person alex = new Person("alex");
        Team t = new Team("team");
        alex.addTeam(t, "integrationTest", new Date());
        entityManager.persist(alex);


        // when
        Person found = personRepository.findOne(alex.getId());

        // then
        assertThat(found.getTeams().size(), is(1));
    }

}
