package hello.gui.persons.view;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by No3x on 08.09.2017.
 */
@Component
@Scope(value = "prototype")
public class PersonListItemView implements FxmlView<PersonListItemViewModel> {

    @FXML
    public Label titleLabel;
    @FXML
    public Label identifierLabel;

    @InjectViewModel
    private PersonListItemViewModel viewModel;

    public void initialize() {
        titleLabel.textProperty().bind(viewModel.titleProperty());
        identifierLabel.textProperty().bind(Bindings.concat("(", viewModel.idProperty(), ")"));
    }
}
