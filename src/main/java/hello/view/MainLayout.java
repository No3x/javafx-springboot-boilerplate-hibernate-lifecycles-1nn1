package hello.view;

import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by No3x on 08.08.2017.
 */
@Component
public class MainLayout extends GridPane {

    private final HelloWorldComponent helloWorldComponent;
    private ListCustomersComponent listCustomersComponent;

    @Autowired
    public MainLayout(HelloWorldComponent helloWorldComponent, ListCustomersComponent listCustomersComponent) {
        this.helloWorldComponent = helloWorldComponent;
        this.listCustomersComponent = listCustomersComponent;
        initComponent();
    }

    private void initComponent() {
        add(this.helloWorldComponent, 0, 0);
        add(this.listCustomersComponent, 0, 1);
    }
}
