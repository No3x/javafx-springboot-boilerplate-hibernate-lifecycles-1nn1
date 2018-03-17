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
    private ReadOnlyStringWrapper identifier = new ReadOnlyStringWrapper();

    public TeamListItemViewModel(Team team) {
        id.set(team.getId());
        title.set(team.getName());
        identifier.set("(" + team.getId() + ")");
    }

    public ObservableStringValue titleProperty() {
        return title.getReadOnlyProperty();
    }

    public ObservableStringValue identifierProperty() {
        return identifier.getReadOnlyProperty();
    }

    public ReadOnlyLongProperty getId() {
        return id.getReadOnlyProperty();
    }
}
