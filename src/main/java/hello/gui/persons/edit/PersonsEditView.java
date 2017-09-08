package hello.gui.persons.edit;

/**
 * Created by No3x on 08.08.2017.
 */

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class PersonsEditView implements FxmlView<PersonsEditViewModel>, Initializable {

    @FXML
    private Label helloLabel;

    @FXML
    private Label personSelectedName;

    @InjectViewModel
    private PersonsEditViewModel viewModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        helloLabel.textProperty().bind(viewModel.helloMessageProperty());
        helloLabel.setOnMouseClicked(event -> viewModel.onAction());
        personSelectedName.textProperty().bind(viewModel.selectedPersonProperty().get().getPerson().nameProperty());
    }

}
