package hello.gui.persons.edit;

/**
 * Created by No3x on 08.08.2017.
 */

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.utils.validation.visualization.ControlsFxVisualizer;
import de.saxsys.mvvmfx.utils.validation.visualization.ValidationVisualizer;
import hello.data.model.Team;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
public class PersonsEditView implements FxmlView<PersonsEditViewModel>, Initializable {

    @FXML
    private Button save;

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
        name.textProperty().bindBidirectional(viewModel.nameProperty());
        Bindings.bindContent(teamCombobox.getItems(), viewModel.getTeams());
        teamCombobox.getSelectionModel().selectFirst();
        teamListview.getItems().setAll(viewModel.getTeamsOfSelected());
        Bindings.bindContent(teamListview.getItems(), viewModel.getTeamsOfSelected());

        viewModel.personEditContext.teamOfCombobox.bind(teamCombobox.getSelectionModel().selectedItemProperty());
        viewModel.personEditContext.selectedTeam.bind(teamListview.getSelectionModel().selectedItemProperty());

        ValidationVisualizer visualizer = new ControlsFxVisualizer();
        visualizer.initVisualization(viewModel.nameValidation(), name, true);
        visualizer.initVisualization(viewModel.teamsValidation(), teamListview, true);

        save.disableProperty().bind(viewModel.formValidation().validProperty().not());
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
