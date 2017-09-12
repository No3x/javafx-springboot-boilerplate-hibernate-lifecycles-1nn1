package hello.gui.persons.edit;

/**
 * Created by No3x on 08.08.2017.
 */

import com.google.common.collect.Lists;
import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ScopeProvider;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import hello.data.model.Team;
import hello.data.repository.PersonRepository;
import hello.data.repository.TeamRepository;
import hello.gui.persons.view.PersonListItemViewModel;
import hello.gui.scopes.PersonDetailScope;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ScopeProvider(scopes = {PersonDetailScope.class})
public class PersonsEditViewModel implements ViewModel {

    /**
     * Used for View<->ViewModel dependencies.
     * The view is able to communicate with the model and vice versa.
     */
    public class PersonEditContext {
        public ObjectProperty<Team> teamOfCombobox = new SimpleObjectProperty<>();
    }
    PersonEditContext personEditContext = new PersonEditContext();

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TeamRepository teamRepository;

    private final StringProperty nameProperty = new SimpleStringProperty("Hello World");
    private ObservableList<Team> teams = FXCollections.observableArrayList();
    private ObservableList<Team> teamsOfSelected = FXCollections.observableArrayList();

    @InjectScope
    private PersonDetailScope scope;

    private final Command saveCommand;
    private final Command addTeamCommand;
    private final Command removeTeamCommand;

    public PersonsEditViewModel() {
        saveCommand = new DelegateCommand(() -> new Action() {
            @Override
            protected void action() throws Exception {
                save();
            }
        });
        addTeamCommand = new DelegateCommand(() -> new Action() {
            @Override
            protected void action() throws Exception {
                addTeam();
            }
        }, personEditContext.teamOfCombobox.isNotNull() );
        removeTeamCommand = new DelegateCommand(() -> new Action() {
            @Override
            protected void action() throws Exception {
                removeTeam();
            }
        });
    }

    public void initialize() {
        nameProperty.bind(selectedPersonProperty().get().titleProperty());
        teams.setAll(Lists.newArrayList(teamRepository.findAll()));
        teamsOfSelected.setAll(Lists.newArrayList(selectedPersonProperty().get().getPerson().getTeams()));
    }

    private void removeTeam() {

    }

    private void addTeam() {
        final Team selectedTeam = personEditContext.teamOfCombobox.get();
        if(teamsOfSelected.stream().noneMatch(team -> team.getName().equals(selectedTeam.getName()))) {
            teamsOfSelected.add(selectedTeam);
        }
    }
    
    protected void save() {
        personRepository.save(scope.selectedPersonProperty().get().getPerson());
    }

    public StringProperty namePropertyProperty() {
        return nameProperty;
    }

    public String getNameProperty() {
        return nameProperty.get();
    }

    public void setNameProperty(String message) {
        nameProperty.set(message);
    }

    public void onAction() {
        nameProperty.set("Clicked");
    }

    public ObjectProperty<PersonListItemViewModel> selectedPersonProperty() {
        if( null == scope.selectedPersonProperty().get() ) {
            throw new IllegalStateException("There is no person selected! This is crucial for the process.");
        }

        return scope.selectedPersonProperty();
    }

    public Command getSaveCommand() {
        return saveCommand;
    }

    public Command getAddTeamCommand() {
        return addTeamCommand;
    }

    public Command getRemoveTeamCommand() {
        return removeTeamCommand;
    }

    public ObservableList<Team> getTeams() {
        return teams;
    }

    public ObservableList<Team> getTeamsOfSelected() {
        return teamsOfSelected;
    }
}
