package hello.data.model;

/**
 * Created by No3x on 11.08.2017.
 */

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * Created by No3x on 05.02.2017.
 * https://vladmihalcea.com/2017/07/26/the-best-way-to-map-a-many-to-many-association-with-extra-columns-when-using-jpa-and-hibernate/
 */
@Entity
@Table(name = "person_team")
public class PersonTeam implements java.io.Serializable {
    @EmbeddedId
    private PersonTeamPk pk;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("personId")
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("teamId")
    private Team team;

    private Date createdDate;
    private String createdBy;

    public PersonTeam() {
    }

    public PersonTeam(Person person, Team team) {
        this.person = person;
        this.team = team;
        this.pk = new PersonTeamPk(person.getId(), team.getId());
    }

    public PersonTeamPk getPk() {
        return pk;
    }

    public void setPk(PersonTeamPk pk) {
        this.pk = pk;
    }

    @Transient
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Transient
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE", nullable = false, length = 10)
    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "CREATED_BY", nullable = false, length = 10)
    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        PersonTeam that = (PersonTeam) o;

        return Objects.equals(person, that.person) &&
            Objects.equals(team, that.team);
    }

    public int hashCode() {
        return Objects.hash(person, team);
    }

}
