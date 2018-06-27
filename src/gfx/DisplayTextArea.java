package gfx;

import javafx.scene.control.TextArea;

/**
 *
 * @author Luis
 */
public class DisplayTextArea {

    private static final TextArea TEXT_AREA = new TextArea();

    protected static void init() {
        TEXT_AREA.setEditable(false);
        TEXT_AREA.setFocusTraversable(false);
        TEXT_AREA.setMinWidth(0);
    }

    public static TextArea getTextArea() {
        return TEXT_AREA;
    }
}
