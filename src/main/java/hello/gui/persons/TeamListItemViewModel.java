package hello.gui.persons;

import de.saxsys.mvvmfx.ViewModel;
import hello.data.model.Team;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableStringValue;

/**
 * Created by No3x on 09.09.2017.
 */
public class TeamListItemViewModel implements ViewModel {

    private ReadOnlyStringWrapper title = new ReadOnlyStringWrapper();
    private ReadOnlyStringWrapper identifier = new ReadOnlyStringWrapper();
    private Team team;

    public TeamListItemViewModel(Team team) {
        this.team = team;
        title.set(team.getName());
        identifier.set("(" + team.getId() + ")");
    }

    public Team getTeam() {
        return team;
    }

    public ObservableStringValue titleProperty() {
        return title.getReadOnlyProperty();
    }

    public ObservableStringValue identifierProperty() {
        return identifier.getReadOnlyProperty();
    }

}
