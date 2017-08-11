package hello.utils;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by No3x on 11.08.2017.
 */
public class JavaFXUtils {

    public static boolean isDoubleClick(MouseEvent event) {
        return event.getButton().equals(MouseButton.PRIMARY) && 2 == event.getClickCount();
    }
}
