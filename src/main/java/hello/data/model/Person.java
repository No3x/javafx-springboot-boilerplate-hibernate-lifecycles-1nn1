package hello.data.model;

/**
 * Created by No3x on 11.08.2017.
 */

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Person {
    private static final Logger LOG = LoggerFactory.getLogger(Person.class);
    private final IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private final StringProperty name = new SimpleStringProperty(this, "name");
    private Set<PersonTeam> personTeams = new LinkedHashSet<>();

    private ListProperty<Team> teams = new SimpleListProperty<>(this, "teams", FXCollections.observableArrayList());

    public Person(String s) {
        this.name.set(s);
    }

    public Person() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id.get();
    }

    public void setId(Integer id) {
        this.id.set(id);
    }
    public IntegerProperty idProperty() {
        return id;
    }
    @Basic
    @Column(name = "name")
    public String getName() {
        return name.get();
    }
    public void setName(String name) {
        this.name.set(name);
    }
    public StringProperty nameProperty() {
        return name;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.person", cascade=CascadeType.ALL, orphanRemoval = true)
    public Set<PersonTeam> getPersonTeams() {
        return personTeams;
    }

    public void setPersonTeams(Set<PersonTeam> personTeams) {
        this.personTeams = personTeams;
        teams.setAll(personTeams.stream().map(PersonTeam::getTeam).collect(Collectors.toList()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (getId() != null ? !getId().equals(person.getId()) : person.getId() != null) return false;
        if (getName() != null ? !getName().equals(person.getName()) : person.getName() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }

    /**
     * Returns an Observable List that must remain unchanged.
     * Unfortunately there is no RO Observable List Interface to refer to.
     */
    @Transient
    public ObservableList<Team> getTeams() {
        return teams.get();
    }

    @Transient
    public ReadOnlyListProperty<Team> teamsProperty() {
        return teams;
    }

    public void addTeam(Team team, String createdBy, Date createdDate) {
        final PersonTeam personTeam = new PersonTeam(this, team);
        personTeam.setCreatedBy(createdBy);
        personTeam.setCreatedDate(createdDate);
        if( !personTeams.add(personTeam) ) {
            LOG.error("Failed to add personTeam " + personTeam + " to collection personTeams " + personTeams);
        }
        if( !team.getPersonTeams().add( personTeam ) ) {
            LOG.error("Failed to add personTeam " + personTeam + " to collection team.getPersonTeams " + team.getPersonTeams());
        }
        teams.setAll(personTeams.stream().map(PersonTeam::getTeam).collect(Collectors.toList()));
    }

    public void removeTeam(Team team) {
        PersonTeam personTeam = new PersonTeam( this, team );
        team.getPersonTeams().remove( personTeam );
        personTeams.removeIf( pt -> pt.getTeam() == team );
        personTeam.setPerson( null );
        personTeam.setTeam( null );
        teams.setAll(personTeams.stream().map(PersonTeam::getTeam).collect(Collectors.toList()));
    }

    /*
      The method is not intended to be used in the GUI.
      Use the {@link GUIRepresentable} interface instead.
     */
    @Override
    public String toString() {
        return name.getValue();
    }

}

