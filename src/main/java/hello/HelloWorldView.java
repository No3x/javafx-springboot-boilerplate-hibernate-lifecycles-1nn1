package hello;

/**
 * Created by No3x on 08.08.2017.
 */

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.utils.validation.visualization.ControlsFxVisualizer;
import de.saxsys.mvvmfx.utils.validation.visualization.ValidationVisualizer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class HelloWorldView implements FxmlView<HelloWorldViewModel>, Initializable {

    @FXML
    private Label helloLabel;

    @InjectViewModel
    private HelloWorldViewModel viewModel;

    private ValidationVisualizer validationVisualizer = new ControlsFxVisualizer();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        helloLabel.textProperty().bind(viewModel.helloMessage());
        helloLabel.setOnMouseClicked(event -> viewModel.onAction());
    }

}
