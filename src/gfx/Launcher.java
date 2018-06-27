package gfx;

import assets.css.CSS;
import javafx.application.Application;
import javafx.stage.Stage;
import utils.io.FileSelector;
import utils.operations.ocr.TessOCR;

/**
 *
 * @author Luis
 */
public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) {
        GUI gui = new GUI(primaryStage);

        /*Initialize*/
        DisplayAlertBox.init();
        DisplayHelp.init();
        DisplayPaintFX.init();
        DisplayReadme.init();
        DisplayRecognizedText.init();
        DisplayRootScene.init(primaryStage.getScene());
        DisplaySearchBox.init();
        DisplayTableView.init();
        DisplayTextArea.init();
        DisplayWindow.init();
        CSS.init(primaryStage.getScene());
        TessOCR.init(); //Tesseract that will perform the OCR
        FileSelector.init();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
