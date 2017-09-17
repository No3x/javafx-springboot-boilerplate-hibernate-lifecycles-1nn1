package hello.data.model;

import javafx.beans.Observable;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by No3x on 11.08.2017.
 */
@Entity
public class Team implements Serializable, Comparable<Team>  {
    private final LongProperty id = new SimpleLongProperty(this, "id");
    private final StringProperty name = new SimpleStringProperty(this, "name");
    private List<PersonTeam> personTeams = new ArrayList<>();

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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "team", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    public List<PersonTeam> getPersonTeams() {
        return personTeams;
    }

    public void setPersonTeams(List<PersonTeam> personTeams) {
        this.personTeams = personTeams;
    }

    public static Callback<Team, Observable[]> extractor() {
        return (Team p) -> new Observable[]{p.nameProperty()};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team that = (Team) o;

        return Objects.equals(getId(), that.getId()) &&
            Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        int result = id != null ? getId().hashCode() : 0;
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
