package hello.gui.persons.view;

import de.saxsys.mvvmfx.ViewModel;
import hello.data.model.Team;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableStringValue;

/**
 * Created by No3x on 09.09.2017.
 */
public class TeamListItemViewModel implements ViewModel {

    private ReadOnlyLongWrapper id = new ReadOnlyLongWrapper();
    private ReadOnlyStringWrapper title = new ReadOnlyStringWrapper();

    public TeamListItemViewModel(Team team) {
        id.set(team.getId());
        title.set(team.getName());
    }

    public ObservableStringValue titleProperty() {
        return title.getReadOnlyProperty();
    }

    public ReadOnlyLongProperty idProperty() {
        return id.getReadOnlyProperty();
    }
}
