package hello.gui.scopes;

import de.saxsys.mvvmfx.Scope;
import hello.data.model.Person;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.springframework.stereotype.Component;

/**
 * Created by No3x on 11.08.2017.
 */
@Component
@org.springframework.context.annotation.Scope(value = "singleton")
public class PersonDetailScope implements Scope {

    private ObjectProperty<Person> selectedPerson = new SimpleObjectProperty<>();

    public ObjectProperty<Person> selectedPersonProperty() {
        return selectedPerson;
    }
}
