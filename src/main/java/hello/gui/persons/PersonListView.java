package hello.gui.persons;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import hello.data.model.Person;
import hello.data.model.Team;
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
    private ListView<Person> personsListView;

    @FXML
    private ListView<Team> teamsOfSelected;

    @InjectViewModel
    private PersonListViewModel personListViewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        personsListView.setItems((personListViewModel.personsProperty()));
        personListViewModel.selectedPersonProperty().bind(personsListView.getSelectionModel().selectedItemProperty());
    }

    public void addRandomAction(ActionEvent actionEvent) {
        personListViewModel.addRandom();
    }

}
