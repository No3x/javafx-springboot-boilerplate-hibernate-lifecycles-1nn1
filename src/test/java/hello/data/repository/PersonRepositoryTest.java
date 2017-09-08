package hello.data.repository;

import hello.data.model.Person;
import hello.data.model.PersonTeam;
import hello.data.model.Team;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.util.Pair;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Date;

import static org.hamcrest.Matchers.*;
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
    private PersonTeamRepository personTeamRepository;

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
        PersonTeamPair created = createPersonAndTransitiveTeam();

        // when
        Person found = personRepository.findOne(created.getPerson().getId());

        // then
        assertThat(found, notNullValue());
        assertThat(found.getTeams().size(), is(1));
        assertThat(found.getTeams().get(0).getName(), is("team"));
    }

    private PersonTeamPair createPersonAndTransitiveTeam() {
        Person alex = new Person("alex");
        Team t = new Team("team");
        t = teamRepository.save(t);
        alex.addTeam(t, "integrationTest", new Date());
        alex = personRepository.save(alex);
        return PersonTeamPair.of(alex, t);
    }

    @Test
    public void whenAddingANewTeamTroughPersonItIsFoundByRepository_thenNoException() {
        // given
        PersonTeamPair created = createPersonAndTransitiveTeam();

        // when
        Team found = teamRepository.findOne(created.getTeam().getId());

        // then
        assertThat(found, notNullValue());
        assertThat(found.getName(), is("team"));
    }

    @Test
    public void whenCallingFindAll_thenReturnsProperSizeOfOne() {
        // given
        createPersonAndTransitiveTeam();

        // when
        Iterable<Team> found = teamRepository.findAll();
        // then
        assertThat(found, notNullValue());
    }

    @Test
    public void whenPersistingAPersonTeam_thenNoException() {
        // given
        PersonTeamPair created = createPersonAndTransitiveTeam();
        created.getPerson().removeTeam(created.getTeam());
        personRepository.save(created.getPerson());

        // when
        Person foundPerson = personRepository.findOne(created.getPerson().getId());
        Team foundTeam = teamRepository.findOne(created.getTeam().getId());

        // then
        assertThat(foundPerson, notNullValue());
        assertThat(foundTeam, notNullValue());
        assertThat(foundPerson.getTeams().size(), is(0));
        assertThat(foundTeam.getPersonTeams().size(), is(0));
    }

    @Test
    public void whenRemovingAPersonFromATeam_thenTheTeamIsNotDeleted() {
        Person alex = new Person("alex");
        Team t = new Team("team");
        PersonTeam personTeam = alex.addTeam(t, "integrationTest", new Date());
        // when
        personTeamRepository.save(personTeam);
        // then
        final ArrayList<PersonTeam> personTeams = Lists.newArrayList(personTeamRepository.findAll());
        Assert.assertThat( personTeams.size(), is(1) );
        Assert.assertThat( personTeams.get(0).getPerson().getName(), is("alex") );
        Assert.assertThat( personTeams.get(0).getTeam().getName(), is("team") );
    }

    private static class PersonTeamPair {
        private Pair<Person, Team> personTeamPair;

        private PersonTeamPair(Person first, Team second) {
            personTeamPair = Pair.of(first, second);
        }

        Person getPerson() {
            return personTeamPair.getFirst();
        }

        Team getTeam() {
            return personTeamPair.getSecond();
        }

        static PersonTeamPair of(@Nonnull Person first, @Nonnull Team second) {
           return new PersonTeamPair(first, second);
        }

    }

}
