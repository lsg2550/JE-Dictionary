package gfx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Luis
 */
public class DisplayImageView {

    protected static ImageView imageView = new ImageView();

    protected static void init() {
        imageView.minHeight(500);
        imageView.minWidth(500);
        imageView.maxHeight(Double.MAX_VALUE);
        imageView.maxWidth(Double.MAX_VALUE);
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
}
