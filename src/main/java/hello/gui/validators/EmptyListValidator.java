package hello.gui.validators;

import de.saxsys.mvvmfx.utils.validation.ObservableRuleBasedValidator;
import de.saxsys.mvvmfx.utils.validation.ValidationMessage;
import de.saxsys.mvvmfx.utils.validation.ValidationStatus;
import de.saxsys.mvvmfx.utils.validation.Validator;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

/**
 * Created by No3x on 18.09.2017.
 * Let you validate an ObservableList on empty state. For that it creates an SimpleListProperty (that has an {@link SimpleListProperty#isEmpty()} property) and binds to the original list.
 * Holds an ObservableRuleBasedValidator.
 */
public class EmptyListValidator<T> implements Validator {

    /**
     * List with capability to observe emtpy property. Contents are always in sync with the origin list.
     */
    private final SimpleListProperty<T> proxyList;

    /**
     * Validation utilizes the {@link ObservableRuleBasedValidator}
     */
    private final ObservableRuleBasedValidator observableRuleBasedValidator;

    /**
     * Creates an instance of the Validator with an empty check for the specified list.
     *
     * @param list    the list to validate for empty state
     * @param message the message displayed to the user
     */
    public EmptyListValidator(ObservableList<T> list, ValidationMessage message) {
        this.proxyList = new SimpleListProperty<>(list);
        this.observableRuleBasedValidator = new ObservableRuleBasedValidator(proxyList.emptyProperty()
                                                                                      .not(), message);
    }

    @Override
    public ValidationStatus getValidationStatus() {
        return observableRuleBasedValidator.getValidationStatus();
    }
}
