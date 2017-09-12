package hello.gui.persons.view;

import com.google.common.collect.Streams;
import de.saxsys.mvvmfx.*;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import hello.data.model.Person;
import hello.data.model.PersonTeam;
import hello.data.model.Team;
import hello.data.repository.PersonRepository;
import hello.gui.WindowManager;
import hello.gui.persons.edit.PersonsEditView;
import hello.gui.persons.edit.PersonsEditViewModel;
import hello.gui.scopes.PersonDetailScope;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.kohsuke.randname.RandomNameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by No3x on 11.08.2017.
 */
@Component
@ScopeProvider(scopes = {PersonDetailScope.class})
public class PersonListViewModel implements ViewModel {

    private static final Logger log = LoggerFactory.getLogger(PersonListViewModel.class);

    private final PersonRepository personRepository;
    private final RandomNameGenerator randomNameGenerator;
    private final WindowManager windowManager;

    private ObservableList<PersonListItemViewModel> persons = FXCollections.observableArrayList();
    private ObservableList<TeamListItemViewModel> teams = FXCollections.observableArrayList();

    @InjectScope
    private PersonDetailScope scope;
    private DelegateCommand gotoDelegateCommand;
    private DelegateCommand gotoDetailDelegateCommand;

    @Autowired
    public PersonListViewModel(PersonRepository personRepository, WindowManager windowManager, RandomNameGenerator randomNameGenerator) {
        this.windowManager = windowManager;
        this.personRepository = personRepository;
        this.randomNameGenerator = randomNameGenerator;
    }

    public void initialize() {
        log.debug("Init");
        gotoDelegateCommand = new DelegateCommand(() -> new Action() {
            @Override
            protected void action() throws Exception {
                random();
            }
        }, false);

        gotoDetailDelegateCommand = new DelegateCommand(() -> new Action() {
            @Override
            protected void action() throws Exception {
                gotoDetail();
            }
        }, scope.selectedPersonProperty()
                .isNotNull(), false);

        scope.selectedPersonProperty().addListener((observable, oldValue, newValue) -> {
            if( selectedPersonProperty().get() != null ) {
                teams.clear();
                selectedPersonProperty().get()
                                        .getPerson()
                                        .getPersonTeams()
                                        .stream()
                                        .map(PersonTeam::getTeam)
                                        .map(TeamListItemViewModel::new)
                                        .forEach(teams::add);
            }
        });

        initData();
    }

    private void initData() {
        log.debug("Init data");
        persons.clear();

        Streams.stream(personRepository.findAll())
               .map(PersonListItemViewModel::new)
               .forEach(persons::add);
    }

    public ObjectProperty<PersonListItemViewModel> selectedPersonProperty() {
        return scope.selectedPersonProperty();
    }

    public ObservableList<PersonListItemViewModel> personsProperty() {
        return persons;
    }

    public DelegateCommand getGotoDetailCommand() {
        return gotoDetailDelegateCommand;
    }

    public DelegateCommand getRandomCommand() {
        return gotoDelegateCommand;
    }

    private void random() {
        log.debug("Create random");
        final Person person = new Person(randomNameGenerator.next());
        final Team team = new Team(randomNameGenerator.next());
        person.addTeam(team, "random", new Date());
        personRepository.save(person);
        initData();
    }

    private void gotoDetail() {
        log.debug("Go to detail");
        final ViewTuple<PersonsEditView, PersonsEditViewModel> viewTuple = FluentViewLoader.fxmlView(PersonsEditView.class).load();
        windowManager.createWindow(viewTuple.getView()).showAndWait();
    }

    public ObservableList<TeamListItemViewModel> teamsProperty() {
        return teams;
    }
}
