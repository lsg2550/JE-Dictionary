package gfx;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

/**
 *
 * @author Luis
 */
public class DisplayImageView {

    protected static ImageView imageView = new ImageView();

    protected static void init() {
        imageView.setFitWidth(375.0);
        imageView.setFitHeight(375.0);
        imageView.setPreserveRatio(true);
    }

    public static boolean hasImage() {
        return imageView.getImage() != null; //True, has image; False, doesn't have image
    }

    public static Image getImage() {
        return imageView.getImage();
    }

    public static void setImage(Image image) {
        imageView.setImage(image);
    }

    public static void snapshot(SnapshotParameters params, WritableImage image) {
        imageView.snapshot(params, image);
    }
}
