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

    /*
      The method is not intended to be used in the GUI.
      Use the {@link GUIRepresentable} interface instead.
     */
    @Override
    public String toString() {
        return name;
    }

}

