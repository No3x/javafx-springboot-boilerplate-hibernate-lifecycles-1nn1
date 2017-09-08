package hello.gui.persons;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.utils.viewlist.CachedViewModelCellFactory;
import hello.utils.JavaFXUtils;
import javafx.event.ActionEvent;
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
    private PersonListViewModel personListViewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        personsListView.setItems((personListViewModel.personsProperty()));
        personsListView.setCellFactory(CachedViewModelCellFactory.createForFxmlView(PersonListItemView.class));

        teamsOfSelected.setItems((personListViewModel.teamsProperty()));
        teamsOfSelected.setCellFactory(CachedViewModelCellFactory.createForFxmlView(TeamListItemView.class));

        personsListView.setOnMouseClicked(event -> {
            if (JavaFXUtils.isDoubleClick(event)) {
                gotoDetailAction();
            } else {
                personListViewModel.selectedPersonProperty().set(personsListView.getSelectionModel().getSelectedItem());
            }
        });
    }

    public void gotoDetailAction() {
        personListViewModel.getGotoDetailCommand().execute();
    }

    public void randomAction(ActionEvent actionEvent) {
        personListViewModel.getRandomCommand().execute();
    }

}
