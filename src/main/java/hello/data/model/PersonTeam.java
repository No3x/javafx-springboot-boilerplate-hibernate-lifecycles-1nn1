package hello.data.model;

/**
 * Created by No3x on 11.08.2017.
 */

import javax.persistence.*;
import java.util.Date;

/**
 * Created by No3x on 05.02.2017.
 */
@Entity
@Table(name = "person_team")
@AssociationOverrides({
    @AssociationOverride(name = "pk.person",
        joinColumns = @JoinColumn(name = "PERSON_ID")),
    @AssociationOverride(name = "pk.team",
        joinColumns = @JoinColumn(name = "TEAM_ID")) })
public class PersonTeam implements java.io.Serializable {

    private PersonTeamPk pk = new PersonTeamPk();
    private Date createdDate;
    private String createdBy;

    public PersonTeam() {
    }

    public PersonTeam(Person person, Team team) {
        this.pk.setPerson(person);
        this.pk.setTeam(team);
    }

    @EmbeddedId
    public PersonTeamPk getPk() {
        return pk;
    }

    public void setPk(PersonTeamPk pk) {
        this.pk = pk;
    }

    @Transient
    public Person getPerson() {
        return getPk().getPerson();
    }

    public void setPerson(Person person) {
        getPk().setPerson(person);
    }

    @Transient
    public Team getTeam() {
        return getPk().getTeam();
    }

    public void setTeam(Team team) {
        getPk().setTeam(team);
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

        if (getPk() != null ? !getPk().equals(that.getPk())
            : that.getPk() != null)
            return false;

        return true;
    }

    public int hashCode() {
        return (getPk() != null ? getPk().hashCode() : 0);
    }

}
