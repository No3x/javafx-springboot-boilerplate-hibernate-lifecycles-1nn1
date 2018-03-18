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
import de.saxsys.mvvmfx.utils.validation.CompositeValidator;
import de.saxsys.mvvmfx.utils.validation.ObservableRuleBasedValidator;
import de.saxsys.mvvmfx.utils.validation.ValidationMessage;
import de.saxsys.mvvmfx.utils.validation.ValidationStatus;
import hello.data.model.Person;
import hello.data.model.PersonTeam;
import hello.data.model.PersonTeamPk;
import hello.data.model.Team;
import hello.data.repository.PersonRepository;
import hello.data.repository.PersonTeamRepository;
import hello.data.repository.TeamRepository;
import hello.gui.persons.listitems.TeamListItemViewModel;
import hello.gui.scopes.PersonDetailScope;
import hello.gui.validators.EmptyListValidator;
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
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
@ScopeProvider(scopes = {PersonDetailScope.class})
public class PersonsEditViewModel implements ViewModel {

    /**
     * Used for View<->ViewModel dependencies.
     * The view is able to communicate with the viewmodel and vice versa.
     */
    public class PersonEditContext {
        public ObjectProperty<TeamListItemViewModel> teamOfCombobox = new SimpleObjectProperty<>();
        public ObjectProperty<TeamListItemViewModel> selectedTeam = new SimpleObjectProperty<>();
    }

    PersonEditContext personEditContext = new PersonEditContext();

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PersonTeamRepository personTeamRepository;

    private final StringProperty nameProperty = new SimpleStringProperty("Hello World");
    private ObservableList<TeamListItemViewModel> teams = FXCollections.observableArrayList();
    private ObservableList<TeamListItemViewModel> teamsOfSelected = FXCollections.observableArrayList();

    @InjectScope
    private PersonDetailScope scope;

    private final DelegateCommand saveCommand;
    private final DelegateCommand addTeamCommand;
    private final DelegateCommand removeTeamCommand;

    private ObservableRuleBasedValidator nameValidator;
    private EmptyListValidator teamsValidator;
    private CompositeValidator formValidator;

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
        nameProperty.set(selectedPerson().getName());

        teams.setAll(Lists.newArrayList(teamRepository.findAll()).stream().map(TeamListItemViewModel::new).collect(Collectors.toList()));

        final List<PersonTeam> allByPersonId = getAllTeamsOfSelectedPerson();
        teamsOfSelected.setAll(Lists.newArrayList(allByPersonId.stream().map(PersonTeam::getTeam).map(TeamListItemViewModel::new).collect(Collectors.toList())));

        nameValidator = new ObservableRuleBasedValidator(nameProperty.isNotNull()
                                                                     .and(nameProperty.isNotEmpty()), ValidationMessage.error("Name may not be empty"));

        teamsValidator = new EmptyListValidator<>(teamsOfSelected, ValidationMessage.error("List may not be empty"));

        formValidator = new CompositeValidator(nameValidator, teamsValidator);
    }


    private void addTeam() {
        final String teamName = personEditContext.teamOfCombobox.get().titleProperty().get();
        if(teamsOfSelected.stream().noneMatch(team -> team.titleProperty().get().equals(teamName))) {
            final Team team = new Team();
            team.setName(teamName);
            team.setId(personEditContext.teamOfCombobox.get().idProperty().get());
            teamsOfSelected.add( new TeamListItemViewModel( team ) );
        }
    }

    private void removeTeam() {
        final Long selectedTeamId = personEditContext.selectedTeam.get().idProperty().get();
        teamsOfSelected.removeIf(teamListItemViewModel -> teamListItemViewModel.idProperty().get() == selectedTeamId );
    }

    private void save() {
        List<Long> idsOfSelected = teamsOfSelected.stream().map( pt -> pt.idProperty().get()).collect(Collectors.toList());
        List<Long> idsOfSelectedInDatabase = personTeamRepository.findAllByPersonId(selectedPerson().getId()).stream().map(PersonTeam::getPk).map(PersonTeamPk::getTeamId).collect(Collectors.toList());

        for( TeamListItemViewModel t : teamsOfSelected ) {
            if( !idsOfSelectedInDatabase.contains(t.idProperty().get())) {
                final PersonTeam personTeam = new PersonTeam(personRepository.findOne(selectedPerson().getId()), teamRepository.findOne(t.idProperty()
                                                                                                                                         .get()));
                personTeam.setCreatedBy("GUI");
                personTeam.setCreatedDate( new Date() );
                personTeamRepository.save(personTeam);
            }
        }

        for( PersonTeam t : getAllTeamsOfSelectedPerson() ) {
            if( !idsOfSelected.contains(t.getPk().getPersonId())) {
                final PersonTeam oneByPersonAndTeamId = personTeamRepository.findOneByPersonAndTeamId(selectedPerson().getId(), t.getTeam().getId()).orElseThrow(IllegalStateException::new);
                personTeamRepository.delete( oneByPersonAndTeamId );
            }
        }

        personRepository.save(selectedPerson());
    }

    public StringProperty nameProperty() {
        return nameProperty;
    }

    public Person selectedPerson() {
        final Person selectedPerson = personRepository.findOne(scope.selectedPersonIdProperty().get());

        if( null == selectedPerson ) {
            throw new IllegalStateException("There is no person selected! This is crucial for the process.");
        }

        return selectedPerson;
    }

    private List<PersonTeam> getAllTeamsOfSelectedPerson() {
        return personTeamRepository.findAllByPersonId(scope.selectedPersonIdProperty().get());
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

    public ObservableList<TeamListItemViewModel> getTeams() {
        return teams;
    }

    public ObservableList<TeamListItemViewModel> getTeamsOfSelected() {
        return teamsOfSelected;
    }

    public ValidationStatus nameValidation() {
        return nameValidator.getValidationStatus();
    }

    public ValidationStatus teamsValidation() {
        return teamsValidator.getValidationStatus();
    }

    public ValidationStatus formValidation() {
        return formValidator.getValidationStatus();
    }


}
