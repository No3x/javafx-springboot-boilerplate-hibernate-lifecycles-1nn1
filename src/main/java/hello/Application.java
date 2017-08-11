package hello;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.ViewTuple;
import hello.view.MainLayout;
import javafx.application.Preloader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;

/**
 * Created by No3x on 08.08.2017.
 */
@Lazy
@SpringBootApplication
public class Application extends AbstractJavaFxApplicationSupport {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    /**
     * Note that this is configured in application.properties
     */
    @Value("${app.ui.title:Example App}")
    private String windowTitle;

    @Autowired
    private MainLayout mainController;

    @Override
    public void start(Stage stage) throws Exception {

        MvvmFX.setCustomDependencyInjector(this::resolve);

        notifyPreloader(new Preloader.StateChangeNotification(Preloader.StateChangeNotification.Type.BEFORE_START));

        final ViewTuple<HelloWorldView, HelloWorldViewModel> viewTuple = FluentViewLoader.fxmlView(HelloWorldView.class).load();

        final Parent root = viewTuple.getView();
        stage.setScene(new Scene(root));
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launchApp(Application.class, args);
    }
}