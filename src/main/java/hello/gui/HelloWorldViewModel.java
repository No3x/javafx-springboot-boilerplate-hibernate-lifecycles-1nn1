package hello.gui;

/**
 * Created by No3x on 08.08.2017.
 */

import de.saxsys.mvvmfx.ViewModel;
import hello.data.repository.CustomerRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldViewModel implements ViewModel {

    private final StringProperty helloMessage = new SimpleStringProperty("Hello World");
    @Autowired
    private CustomerRepository customerRepository;

    public HelloWorldViewModel() {
    }

    public StringProperty helloMessage() {
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

}
