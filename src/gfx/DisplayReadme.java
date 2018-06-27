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
public class DisplayReadme {

    private static final MenuItem README = new MenuItem("Readme");

    protected static void init() {
        Scene scene = new Scene(readmePane(), 500, 400);

        README.setOnAction(e -> {
            DisplayWindow.setResizable(false);
            DisplayWindow.setScene(scene);
            DisplayWindow.show();
        });
    }

    private static VBox readmePane() {
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

    protected static MenuItem getReadmeMenuItem() {
        return README;
    }
}
