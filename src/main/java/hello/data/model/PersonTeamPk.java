package hello.data.model;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * Created by No3x on 05.02.2017.
 */
@Embeddable
public class PersonTeamPk implements java.io.Serializable {

    private Person person;
    private Team team;

    @ManyToOne
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @ManyToOne
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonTeamPk that = (PersonTeamPk) o;

        if (person != null ? !person.equals(that.person) : that.person != null) return false;
        if (team != null ? !team.equals(that.team) : that.team != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (person != null ? person.hashCode() : 0);
        result = 31 * result + (team != null ? team.hashCode() : 0);
        return result;
    }

}
