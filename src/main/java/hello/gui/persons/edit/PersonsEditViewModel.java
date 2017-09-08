package hello.gui.persons.edit;

/**
 * Created by No3x on 08.08.2017.
 */

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ScopeProvider;
import de.saxsys.mvvmfx.ViewModel;
import hello.data.repository.CustomerRepository;
import hello.gui.persons.view.PersonListItemViewModel;
import hello.gui.scopes.PersonDetailScope;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ScopeProvider(scopes = {PersonDetailScope.class})
public class PersonsEditViewModel implements ViewModel {

    private final StringProperty helloMessage = new SimpleStringProperty("Hello World");

    @InjectScope
    private PersonDetailScope scope;

    @Autowired
    private CustomerRepository customerRepository;

    public PersonsEditViewModel() {
    }

    public StringProperty helloMessageProperty() {
        return helloMessage;
    }

    public String getHelloMessage() {
        return helloMessage.get();
    }

    public void setHelloMessage(String message) {
        helloMessage.set(message);
    }

    public void onAction() {
        helloMessage.set("Clicked");
    }

    public ObjectProperty<PersonListItemViewModel> selectedPersonProperty() {
        if( null == scope.selectedPersonProperty().get() ) {
            throw new IllegalStateException("There is no person selected! This is crucial for the process.");
        }

        return scope.selectedPersonProperty();
    }

}
