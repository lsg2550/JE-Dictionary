package gfx;

import assets.ico.Icon;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Luis
 */
public class DisplayWindow {

    private static final Stage DISPLAY_WINDOW = new Stage();

    protected static void init() {
        DISPLAY_WINDOW.getIcons().add(Icon.ICON);
        DISPLAY_WINDOW.setAlwaysOnTop(true);
        DISPLAY_WINDOW.setResizable(false);
        DISPLAY_WINDOW.initModality(Modality.WINDOW_MODAL);
        DISPLAY_WINDOW.initOwner(DisplayRootScene.getScene().getWindow());

        DISPLAY_WINDOW.setOnCloseRequest(e -> {
            DISPLAY_WINDOW.setResizable(true);
        });
    }

    public static void setResizable(boolean canResize) {
        DISPLAY_WINDOW.setResizable(canResize);
    }

    public static void setScene(Scene scene) {
        DISPLAY_WINDOW.setScene(scene);
    }

    public static void close() {
        DISPLAY_WINDOW.close();
    }

    public static void show() {
        DISPLAY_WINDOW.show();
    }

}
