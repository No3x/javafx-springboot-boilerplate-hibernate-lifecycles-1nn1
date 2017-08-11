package hello.gui.persons;

import de.saxsys.mvvmfx.*;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import hello.data.model.Person;
import hello.data.repository.PersonRepository;
import hello.gui.WindowManager;
import hello.gui.personsedit.PersonsEditView;
import hello.gui.personsedit.PersonsEditViewModel;
import hello.gui.scopes.PersonDetailScope;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.kohsuke.randname.RandomNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by No3x on 11.08.2017.
 */
@Component
@ScopeProvider(scopes = {PersonDetailScope.class})
public class PersonListViewModel implements ViewModel {

    private final PersonRepository personRepository;
    private final RandomNameGenerator randomNameGenerator;
    private final WindowManager windowManager;

    private ObservableList<Person> persons = FXCollections.observableArrayList();

    @InjectScope
    private PersonDetailScope scope;
    private DelegateCommand gotoDelegateCommand;
    private DelegateCommand gotoDetailDelegateCommand;

    @Autowired
    public PersonListViewModel(PersonRepository personRepository, WindowManager windowManager, RandomNameGenerator randomNameGenerator) {
        this.windowManager = windowManager;
        this.personRepository = personRepository;
        this.randomNameGenerator = randomNameGenerator;

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
        }, scope.selectedPersonProperty().isNotNull(), false);

        initData();
    }

    private void initData() {
        persons.clear();
        personRepository.findAll().forEach(persons::add);
    }

    public ObjectProperty<Person> selectedPersonProperty() {
        return scope.selectedPersonProperty();
    }

    public ObservableList<Person> personsProperty() {
        return persons;
    }

    public DelegateCommand getGotoDetailCommand() {
        return gotoDetailDelegateCommand;
    }

    public DelegateCommand getRandomCommand() {
        return gotoDelegateCommand;
    }

    private void random() {
        final Person person = new Person(randomNameGenerator.next());
        personRepository.save(person);
        initData();
    }

    private void gotoDetail() {
        final ViewTuple<PersonsEditView, PersonsEditViewModel> viewTuple = FluentViewLoader.fxmlView(PersonsEditView.class).load();
        windowManager.createWindow(viewTuple.getView()).show();
    }
}
