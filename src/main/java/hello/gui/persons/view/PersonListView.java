package hello.gui.persons.view;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.utils.viewlist.CachedViewModelCellFactory;
import hello.gui.persons.listitems.PersonListItemView;
import hello.gui.persons.listitems.PersonListItemViewModel;
import hello.gui.persons.listitems.TeamListItemView;
import hello.gui.persons.listitems.TeamListItemViewModel;
import hello.utils.JavaFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by No3x on 11.08.2017.
 */
@Component
public class PersonListView implements FxmlView<PersonListViewModel>, Initializable {

    @FXML
    private ListView<PersonListItemViewModel> personsListView;

    @FXML
    private ListView<TeamListItemViewModel> teamsOfSelected;

    @InjectViewModel
    private PersonListViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        personsListView.setItems((viewModel.personsProperty()));
        personsListView.setCellFactory(CachedViewModelCellFactory.createForFxmlView(PersonListItemView.class));

        teamsOfSelected.setItems((viewModel.teamsProperty()));
        teamsOfSelected.setCellFactory(CachedViewModelCellFactory.createForFxmlView(TeamListItemView.class));

        personsListView.setOnMouseClicked(event -> {
            if (JavaFXUtils.isDoubleClick(event)) {
                gotoDetailAction();
            } else {
                //TODO: this should be refactored to an command to prevent coupling to the viewModel, unf. MVVMFx doesn't support params for commands yet
                viewModel.selectedPersonPropertyId().bind(personsListView.getSelectionModel().getSelectedItem().idProperty());
            }
        });
    }

    public void gotoDetailAction() {
        viewModel.getGotoDetailCommand().execute();
    }

    public void randomAction() {
        viewModel.getRandomCommand().execute();
    }

}
