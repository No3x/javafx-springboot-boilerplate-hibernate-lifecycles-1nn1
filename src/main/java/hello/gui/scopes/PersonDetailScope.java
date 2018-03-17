package hello.gui.scopes;

import de.saxsys.mvvmfx.Scope;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import org.springframework.stereotype.Component;

/**
 * Created by No3x on 11.08.2017.
 */
@Component
@org.springframework.context.annotation.Scope(value = "singleton")
public class PersonDetailScope implements Scope {

    private LongProperty selectedPersonId = new SimpleLongProperty();

    public LongProperty selectedPersonIdProperty() {
        return selectedPersonId;
    }

}
