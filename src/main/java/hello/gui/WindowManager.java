package hello.gui;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Stack;

/**
 * Created by No3x on 11.08.2017.
 * Based on http://www.c-sharpcorner.com/code/2654/javafx-managing-multiple-stages.aspx
 */
@Component
public class WindowManager {
    private static final Logger logger = LoggerFactory.getLogger(WindowManager.class);

    private Stack<Stage> windows;
    private EventHandler<KeyEvent> escKeyHandler;

    private WindowManager() {
        windows = new Stack<>();
    }

    public void setMainWindow(@Nonnull Stage stage) {
        windows.push(stage);
    }

    public Stage getCurrentWindow() {
        return windows.lastElement();
    }

    public Stage newWindow() {
        logger.info("total windows {}", windows.size());
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(windows.lastElement());
        windows.push(stage);
        stage.setOnCloseRequest(event -> {
            logger.info("removing from windows stack");
            windows.remove(stage);
        });
        return stage;
    }

    public Stage getMainWindow() {
        return windows.firstElement();
    }

    private void setHandlers(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> getEscKeyHandler());
    }

    private EventHandler<KeyEvent> getEscKeyHandler() {
        if(escKeyHandler == null) {
            escKeyHandler = new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if(event.getCode() == KeyCode.ESCAPE) {
                        closeWindow();
                    }
                }
            };
        }
        return escKeyHandler;
    }

    public Stage createWindow(@Nonnull Parent view) {
        Stage stage = newWindow();
        Scene scene = view.getScene();
        if(scene == null) {
            scene = new Scene(view);
            setHandlers(scene);
        }
        stage.setScene(scene);
        return stage;
    }

    public void closeWindow() {
        windows.lastElement().close();
    }

}
