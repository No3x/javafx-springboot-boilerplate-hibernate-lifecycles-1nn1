package hello.gui.persons;

import de.saxsys.mvvmfx.*;
import hello.data.model.Person;
import hello.data.repository.PersonRepository;
import hello.gui.HelloWorldView;
import hello.gui.HelloWorldViewModel;
import hello.gui.WindowManager;
import hello.gui.scopes.PersonDetailScope;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by No3x on 11.08.2017.
 */
@Component
@ScopeProvider(scopes = {PersonDetailScope.class})
public class PersonListViewModel implements ViewModel {

    private final PersonRepository personRepository;

    private ObservableList<Person> persons = FXCollections.observableArrayList();
    private WindowManager windowManager;

    @InjectScope
    private PersonDetailScope scope;

    @Autowired
    public PersonListViewModel(PersonRepository personRepository, WindowManager windowManager) {
        this.windowManager = windowManager;
        this.personRepository = personRepository;
        personRepository.findAll().forEach(persons::add);
    }

    public void addRandom() {
        final ViewTuple<HelloWorldView, HelloWorldViewModel> viewTuple = FluentViewLoader.fxmlView(HelloWorldView.class).load();
        windowManager.createWindow(viewTuple.getView()).show();
    }

    public ObjectProperty<Person> selectedPersonProperty() {
        return scope.selectedPersonProperty();
    }

    public ObservableList<Person> personsProperty() {
        return persons;
    }
}
