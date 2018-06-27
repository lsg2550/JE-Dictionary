package utils.io;

import java.io.File;
import java.net.MalformedURLException;
import javafx.stage.FileChooser;

/**
 *
 * @author Luis
 */
public class FileSelector {

    //Reading File
    private static final FileChooser FILE_CHOOSER = new FileChooser();
    public static File file, tempFile;

    public static void init() {
        FILE_CHOOSER.setTitle("Select Image");
        FILE_CHOOSER.setInitialDirectory(new File(System.getProperty("user.home")));
        FILE_CHOOSER.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));
    }

    public static String readFile() {
        try {
            file = FILE_CHOOSER.showOpenDialog(null); //Select an Image
            if (file != null) {
                FILE_CHOOSER.setInitialDirectory(file.getParentFile());
                tempFile = null;
                return file.toURI().toURL().toExternalForm();
            }
        } catch (MalformedURLException ex) {
        }

        return null;
    }
}
