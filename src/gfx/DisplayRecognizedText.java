package gfx;

import javafx.scene.control.Menu;

/**
 *
 * @author Luis
 */
class DisplayRecognizedText {

    private static final Menu RECOGNIZED_TEXT = new Menu();

    protected static void init() {
        RECOGNIZED_TEXT.setStyle("-fx-padding: 5 0 0 0;");
    }

    protected static Menu getRT() {
        return RECOGNIZED_TEXT;
    }

    protected static void setText(String text) {
        RECOGNIZED_TEXT.setText(text);
    }
}
