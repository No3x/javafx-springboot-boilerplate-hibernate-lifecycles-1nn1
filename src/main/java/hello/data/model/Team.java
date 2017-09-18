package hello.data.model;

import javafx.beans.Observable;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by No3x on 11.08.2017.
 */
@Entity
public class Team implements Serializable, Comparable<Team>  {
    private Long id;
    private String name;
    private List<PersonTeam> personTeams = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }

    public Team() {}

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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "team", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    public List<PersonTeam> getPersonTeams() {
        return personTeams;
    }

    public void setPersonTeams(List<PersonTeam> personTeams) {
        this.personTeams = personTeams;
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
        return Objects.toString(this);
    }

    @Override
    public int compareTo(@Nonnull Team o) {
        return Long.compare(getId(), o.getId());
    }
}
