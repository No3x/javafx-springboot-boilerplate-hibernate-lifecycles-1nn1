package hello.gui.persons.edit;

/**
 * Created by No3x on 08.08.2017.
 */

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import hello.data.model.Team;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class PersonsEditView implements FxmlView<PersonsEditViewModel>, Initializable {
    @FXML
    private TextField name;

    @FXML
    private ListView<Team> teamListview;

    @FXML
    private ComboBox<Team> teamCombobox;

    @InjectViewModel
    private PersonsEditViewModel viewModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //helloLabel.textProperty().bind(viewModel.namePropertyProperty());
        //helloLabel.setOnMouseClicked(event -> viewModel.onAction());
        name.textProperty().bindBidirectional(viewModel.selectedPersonProperty().get().getPerson().nameProperty());

        Bindings.bindContent(teamCombobox.getItems(), viewModel.getTeams());
        teamCombobox.getSelectionModel().selectFirst();
        teamListview.getItems().setAll(viewModel.getTeamsOfSelected());
        Bindings.bindContent(teamListview.getItems(), viewModel.getTeamsOfSelected());

        viewModel.personEditContext.teamOfCombobox.bind(teamCombobox.getSelectionModel().selectedItemProperty());
    }

    public void saveAction(ActionEvent actionEvent) {
        viewModel.getSaveCommand().execute();
    }

    public void addTeamAction(ActionEvent actionEvent) {
        viewModel.getAddTeamCommand().execute();
    }

    public void removeTeamAction(ActionEvent actionEvent) {
        viewModel.getRemoveTeamCommand().execute();
    }
}
