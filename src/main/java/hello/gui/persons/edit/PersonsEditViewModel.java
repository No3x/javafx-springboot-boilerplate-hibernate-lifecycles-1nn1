package hello.gui.persons.edit;

/**
 * Created by No3x on 08.08.2017.
 */

import com.google.common.collect.Lists;
import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ScopeProvider;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import hello.data.adapter.ListCompare;
import hello.data.model.Person;
import hello.data.model.Team;
import hello.data.repository.PersonRepository;
import hello.data.repository.TeamRepository;
import hello.gui.scopes.PersonDetailScope;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Scope("prototype")
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

    private final DelegateCommand saveCommand;
    private final DelegateCommand addTeamCommand;
    private final DelegateCommand removeTeamCommand;

    public PersonsEditViewModel() {
        saveCommand = new DelegateCommand(() -> new Action() {
            @Override
            protected void action() throws Exception {
                save();
            }
        }, false);
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
        nameProperty.set(selectedPersonProperty().get().getName());
        teams.setAll(Lists.newArrayList(teamRepository.findAll()));
        teamsOfSelected.setAll(Lists.newArrayList(selectedPersonProperty().get().getTeams()));
    }

    private void addTeam() {
        final Team selectedTeam = personEditContext.teamOfCombobox.get();
        if(teamsOfSelected.stream().noneMatch(team -> team.getName().equals(selectedTeam.getName()))) {
            teamsOfSelected.add(selectedTeam);
        }
    }

    private void removeTeam() {
        final Team selectedTeam = personEditContext.teamOfCombobox.get();
        teamsOfSelected.remove(selectedTeam);
    }

    private void save() {
        new ListCompare<>(teamsOfSelected, scope.selectedPersonProperty().get().getTeams(), new ListCompare.IChangeAction<Team>() {
            @Override
            public void added(Iterable<? extends Team> added) {
                added.forEach( team -> scope.selectedPersonProperty().get().addTeam(team, "GUI", new Date()));
            }
            @Override
            public void removed(Iterable<? extends Team> removed) {
                removed.forEach(team -> scope.selectedPersonProperty().get().removeTeam(team));
            }
        }).manageChanges();
        selectedPersonProperty().get().setName(nameProperty.get());
        selectedPersonProperty().set(personRepository.save(scope.selectedPersonProperty().get()));
    }

    public StringProperty nameProperty() {
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

    public ObjectProperty<Person> selectedPersonProperty() {
        if( null == scope.selectedPersonProperty().get() ) {
            throw new IllegalStateException("There is no person selected! This is crucial for the process.");
        }

        return scope.selectedPersonProperty();
    }

    public DelegateCommand getSaveCommand() {
        return saveCommand;
    }

    public DelegateCommand getAddTeamCommand() {
        return addTeamCommand;
    }

    public DelegateCommand getRemoveTeamCommand() {
        return removeTeamCommand;
    }

    public ObservableList<Team> getTeams() {
        return teams;
    }

    public ObservableList<Team> getTeamsOfSelected() {
        return teamsOfSelected;
    }
}
