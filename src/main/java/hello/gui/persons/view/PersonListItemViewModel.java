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

    public PersonListItemViewModel(Person person) {
        id.set(person.getId());
        title.set(person.getName());
    }

    public ObservableStringValue titleProperty() {
        return title.getReadOnlyProperty();
    }

    public ObservableLongValue idProperty() {
        return id.getReadOnlyProperty();
    }
}
