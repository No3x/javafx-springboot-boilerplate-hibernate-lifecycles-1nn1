package hello.gui.persons.view;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.utils.viewlist.CachedViewModelCellFactory;
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
                viewModel.selectedPersonProperty().set(personsListView.getSelectionModel().getSelectedItem().getPerson());
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
