package hello.view;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.springframework.stereotype.Component;

/**
 * Created by No3x on 08.08.2017.
 */
@Component
public class HelloWorldComponent extends HBox {

    public HelloWorldComponent() {
        getChildren().add(new Label("Hello World"));
    }
}
