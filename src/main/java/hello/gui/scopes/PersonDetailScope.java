package hello.gui.scopes;

import de.saxsys.mvvmfx.Scope;
import hello.gui.persons.PersonListItemViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.springframework.stereotype.Component;

/**
 * Created by No3x on 11.08.2017.
 */
@Component
public class PersonDetailScope implements Scope {

    private ObjectProperty<PersonListItemViewModel> selectedPerson = new SimpleObjectProperty<>();

    public ObjectProperty<PersonListItemViewModel> selectedPersonProperty() {
        return selectedPerson;
    }
}
