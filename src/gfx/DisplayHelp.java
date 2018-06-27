package gfx;

import assets.ico.Icon;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Luis
 */
class DisplayHelp {

    private final static MenuItem ABOUT = new MenuItem("About");

    protected static void init() {
        Scene scene = new Scene(aboutPane(), 150, 125); //Scene

        ABOUT.setOnAction(e -> {
            DisplayWindow.setResizable(false);
            DisplayWindow.setScene(scene);
            DisplayWindow.show();
        });
    }

    private static VBox aboutPane() {
        VBox vb = new VBox();
        vb.setAlignment(Pos.CENTER);

        //Node - Column - Row
        ImageView imageView = new ImageView(Icon.ICON);
        imageView.setFitWidth(32.0);
        imageView.setFitHeight(32.0);

        Text info = new Text("Made by Luis Garay"
                + "\nGitHub: @lsg2550"
                + "\n2016-2017"
                + "\nCurrent OS: " + System.getProperty("os.name"));
        info.setTextAlignment(TextAlignment.CENTER);

        vb.getChildren().addAll(imageView, info);
        return vb;
    }

    protected static MenuItem getAboutMenuItem() {
        return ABOUT;
    }
}
