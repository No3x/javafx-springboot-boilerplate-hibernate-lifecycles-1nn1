package hello.gui.persons.edit;

/**
 * Created by No3x on 08.08.2017.
 */

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.utils.validation.visualization.ControlsFxVisualizer;
import de.saxsys.mvvmfx.utils.validation.visualization.ValidationVisualizer;
import de.saxsys.mvvmfx.utils.viewlist.CachedViewModelCellFactory;
import de.saxsys.mvvmfx.utils.viewlist.ViewListCellFactory;
import hello.gui.persons.view.TeamListItemView;
import hello.gui.persons.view.TeamListItemViewModel;
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
    private ListView<TeamListItemViewModel> teamListview;

    @FXML
    private ComboBox<TeamListItemViewModel> teamCombobox;

    @InjectViewModel
    private PersonsEditViewModel viewModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.textProperty().bindBidirectional(viewModel.nameProperty());

        ViewListCellFactory<TeamListItemViewModel> teamComboboxCellFactory = CachedViewModelCellFactory.createForFxmlView(TeamListItemView.class);
        teamCombobox.setCellFactory(teamComboboxCellFactory);
        Bindings.bindContent(teamCombobox.getItems(), viewModel.getTeams());

        teamCombobox.getSelectionModel().selectFirst();
        ViewListCellFactory<TeamListItemViewModel> teamListviewCellFactory = CachedViewModelCellFactory.createForFxmlView(TeamListItemView.class);
        teamListview.setCellFactory(teamListviewCellFactory);
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
