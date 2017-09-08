package hello.data.model;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.util.Callback;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by No3x on 11.08.2017.
 */
@Entity
public class Team implements Serializable, Comparable<Team>  {
    private final LongProperty id = new SimpleLongProperty(this, "id");
    private final StringProperty name = new SimpleStringProperty(this, "name");
    private Set<PersonTeam> personTeams = new LinkedHashSet<>();

    public Team(String name) {
        this.name.set(name);
    }

    public Team() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id.get();
    }

    public void setId(Long id) {
        this.id.set(id);
    }
    public LongProperty idProperty() {
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public Set<PersonTeam> getPersonTeams() {
        return personTeams;
    }

    public void setPersonTeams(Set<PersonTeam> personTeams) {
        this.personTeams = personTeams;
    }

    public static Callback<Team, Observable[]> extractor() {
        return (Team p) -> new Observable[]{p.nameProperty()};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        if (getId() != null ? !getId().equals(team.getId()) : team.getId() != null) return false;
        if (getName() != null ? !getName().equals(team.getName()) : team.getName() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name.getValue();
    }

    @Override
    public int compareTo(Team o) {
        return Long.compare(getId(), o.getId());
    }
}
