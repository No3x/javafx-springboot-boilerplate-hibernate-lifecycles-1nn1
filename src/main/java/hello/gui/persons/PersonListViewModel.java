package hello.gui.persons;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import hello.data.model.Person;
import hello.data.repository.PersonRepository;
import hello.gui.HelloWorldView;
import hello.gui.HelloWorldViewModel;
import hello.gui.WindowManager;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by No3x on 11.08.2017.
 */
@Component
public class PersonListViewModel implements ViewModel {

    private final PersonRepository personRepository;

    private ObjectProperty<Person> selectedPerson = new SimpleObjectProperty<>();
    private ObservableList<Person> persons = FXCollections.observableArrayList();
    private WindowManager windowManager;

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
        return selectedPerson;
    }

    public ObservableList<Person> personsProperty() {
        return persons;
    }
}
