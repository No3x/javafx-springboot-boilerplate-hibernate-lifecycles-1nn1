package hello.data.model;

/**
 * Created by No3x on 11.08.2017.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;


// http://www.codejava.net/frameworks/hibernate/hibernate-many-to-many-association-with-extra-columns-in-join-table-example
@Entity
public class Person {
    private static final Logger LOG = LoggerFactory.getLogger(Person.class);
    private Long id;
    private String name;
    private List<PersonTeam> personTeams = new ArrayList<>();

    public Person() {}

    public Person(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "person", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    public List<PersonTeam> getPersonTeams() {
        return personTeams;
    }

    /**
     * The setter is called by hibernate.
     * @param personTeams maybe null, maybe the collection is not even ready for read access.
     *                    Don't do anything with the collection here!
     */
    public void setPersonTeams(List<PersonTeam> personTeams) {
        this.personTeams = personTeams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person that = (Person) o;

        return Objects.equals(getId(), that.getId()) &&
            Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        int result = id != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }

    /**
     * Returns an Observable List that must remain unchanged.
     * Unfortunately there is no RO Observable List Interface to refer to.
     */
    @Transient
    public List<Team> getTeams() {
        return getPersonTeams().stream().map(PersonTeam::getTeam).collect(Collectors.toList());
    }

    public PersonTeam addTeam(Team team, String createdBy, Date createdDate) {
        final PersonTeam personTeam = new PersonTeam(this, team);
        personTeam.setCreatedBy(createdBy);
        personTeam.setCreatedDate(createdDate);
        if( !personTeams.add(personTeam) ) {
            LOG.error("Failed to add personTeam " + personTeam + " to collection personTeams " + personTeams);
        }
        if( !team.getPersonTeams().add( personTeam ) ) {
            LOG.error("Failed to add personTeam " + personTeam + " to collection team.getPersonTeams " + team.getPersonTeams());
        }

        return personTeam;
    }

    public void removeTeam(Team team) {

        for (Iterator<PersonTeam> iterator = personTeams.iterator();
             iterator.hasNext(); ) {
            PersonTeam personTeam = iterator.next();

            if (personTeam.getPerson().equals(this) &&
                personTeam.getTeam().equals(team)) {
                iterator.remove();
                team.getPersonTeams().remove(personTeam);
                personTeam.setPerson(null);
                personTeam.setTeam(null);
            }
        }
    }

    /*
      The method is not intended to be used in the GUI.
      Use the {@link GUIRepresentable} interface instead.
     */
    @Override
    public String toString() {
        return name;
    }

}

