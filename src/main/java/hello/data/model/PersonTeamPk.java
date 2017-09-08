package hello.data.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

/**
 * Created by No3x on 05.02.2017.
 */
@Embeddable
public class PersonTeamPk implements java.io.Serializable {

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "team_id")
    private Long teamId;

    public PersonTeamPk() {}

    public PersonTeamPk(Long personId, Long teamId) {
        this.personId = personId;
        this.teamId = teamId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonTeamPk that = (PersonTeamPk) o;

        return Objects.equals(personId, that.personId) &&
            Objects.equals(teamId, that.teamId);
    }

    public int hashCode() {
        return Objects.hash(personId, teamId);
    }

}
