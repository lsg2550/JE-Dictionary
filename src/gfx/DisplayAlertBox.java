package gfx;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author Luis
 */
public class DisplayAlertBox {

    private static final VBox ALERT_VBOX = new VBox(10);
    private static final Text ALERT_MESSAGE = new Text();
    private static final Scene ALERT_SCENE = new Scene(ALERT_VBOX, 200, 100);

    protected static void init() {
        //UI
        Button ok = new Button("OK");

        //VBox
        ALERT_VBOX.getChildren().addAll(ALERT_MESSAGE, ok);
        ALERT_VBOX.setAlignment(Pos.CENTER);

        //Handlers
        ok.setOnAction(e -> {
            DisplayWindow.close();
        });
    }

    public static void show(String message) {
        DisplayAlertBox.ALERT_MESSAGE.setText(message);
        DisplayWindow.setScene(ALERT_SCENE);
        DisplayWindow.setResizable(false);
        DisplayWindow.show();
    }
}
