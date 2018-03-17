package hello.gui.persons.view;

import de.saxsys.mvvmfx.ViewModel;
import hello.data.model.Person;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableLongValue;
import javafx.beans.value.ObservableStringValue;

/**
 * Created by No3x on 08.09.2017.
 * A viewModel for list entries of persons.
 */
public class PersonListItemViewModel implements ViewModel {

    private ReadOnlyLongWrapper id = new ReadOnlyLongWrapper();
    private ReadOnlyStringWrapper title = new ReadOnlyStringWrapper();
    private ReadOnlyStringWrapper identifier = new ReadOnlyStringWrapper();

    public PersonListItemViewModel(Person person) {
        id.set(person.getId());
        title.set(person.getName());
        identifier.set("(" + person.getId() + ")");
    }

    public ObservableStringValue titleProperty() {
        return title.getReadOnlyProperty();
    }

    public ObservableStringValue identifierProperty() {
        return identifier.getReadOnlyProperty();
    }

    public ObservableLongValue getId() {
        return id.getReadOnlyProperty();
    }
}
