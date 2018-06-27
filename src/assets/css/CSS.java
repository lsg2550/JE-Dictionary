package assets.css;

import com.sun.javafx.css.StyleManager;
import javafx.scene.Scene;

/**
 *
 * @author Luis
 */
public class CSS {

    private final static String CSS_PATH = "assets/css/cssS.css";

    public static void init(Scene scene) {
        scene.getStylesheets().add(CSS_PATH);
        StyleManager.getInstance().addUserAgentStylesheet(CSS_PATH);
    }

}
