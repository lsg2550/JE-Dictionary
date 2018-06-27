package gfx;

import assets.ico.Icon;
import java.net.MalformedURLException;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.io.FileSelector;
import utils.operations.imageprocessing.Crop;
import utils.operations.ocr.ReadDictionary;
import utils.operations.ocr.TessOCR;

/**
 *
 * @author Luis
 */
public class GUI {

    //UI
    private Stage primaryStage;
    private final BorderPane ROOT = new BorderPane();
    private final Scene ROOT_SCENE = new Scene(ROOT, 800, 600);

    GUI(Stage primaryStage) {
        this.primaryStage = primaryStage;
        display();
    }

    private void display() {
        //Root
        HBox tableImgHBox = new HBox();

        //Menu
        MenuBar menuBar = new MenuBar();
        Menu file = new Menu("File"), operations = new Menu("Operations"), help = new Menu("Help");

        MenuItem open = new MenuItem("Open"), exit = new MenuItem("Exit"), //end File Menu
                ocr = new MenuItem("OCR"), search = new MenuItem("Search"), draw = new MenuItem("Draw"), undo = new MenuItem("Undo Crop"); //end Operations Menu

        menuBar.getMenus().addAll(file, operations, help, DisplayRecognizedText.getRT());
        file.getItems().addAll(open, new SeparatorMenuItem(), exit);
        operations.getItems().addAll(ocr, search, new SeparatorMenuItem(), draw, new SeparatorMenuItem(), undo);
        help.getItems().addAll(DisplayHelp.getAboutMenuItem(), DisplayReadme.getReadmeMenuItem());
        ocr.setDisable(true);

        //VBox
        VBox imageViewVBox = new VBox();
        imageViewVBox.setAlignment(Pos.CENTER);
        imageViewVBox.setMinSize(250, 250);
        imageViewVBox.getChildren().add(DisplayImageView.imageView);

        imageViewVBox.setOnMouseClicked(e -> {
            if (DisplayImageView.hasImage()) {
                ScrollPane sp = new ScrollPane();
                StackPane stp = new StackPane();
                ImageView iv = new ImageView();
                stp.getChildren().add(Crop.crop(iv));
                sp.setContent(stp);
                sp.setFitToWidth(true);
                sp.setFitToHeight(true);
                iv.setImage(DisplayImageView.getImage());
                Scene scene = new Scene(sp, 800, 600);
                DisplayWindow.setScene(scene);
                DisplayWindow.show();
            }
        });

        //HBox
        tableImgHBox.getChildren().addAll(DisplayTableView.getTableView(), imageViewVBox /*Crop.crop(DisplayImageView.imageView)*/);
        HBox.setHgrow(DisplayTableView.getTableView(), Priority.ALWAYS);
        tableImgHBox.setMaxWidth(Double.MAX_VALUE);
        tableImgHBox.setAlignment(Pos.CENTER);

        //Root
        ROOT.setTop(menuBar);
        ROOT.setCenter(tableImgHBox);
        ROOT.setBottom(DisplayTextArea.getTextArea());

        //Scene & Stage
        primaryStage.setTitle("Japanese-English Dictionary using OCR");
        primaryStage.getIcons().add(Icon.ICON);
        //primaryStage.setResizable(false);
        primaryStage.setScene(ROOT_SCENE);
        primaryStage.show();

        //Handlers 
        open.setOnAction(e -> {
            try {
                String filePath = FileSelector.readFile();
                DisplayImageView.imageView.setImage(new Image(filePath));
            } catch (IllegalArgumentException | NullPointerException ex) {
                DisplayAlertBox.show("No Image Selected!");
            }
        });

        ocr.setOnAction(e -> {
            DisplayRecognizedText.setText("Reading...");
            new Thread(() -> {
                String recognizedText = TessOCR.recognize(FileSelector.file.getAbsoluteFile()).replace("//s", "");

                Platform.runLater(() -> {
                    DisplayTextArea.getTextArea().setText("");
                    DisplayRecognizedText.setText(recognizedText.trim());
                });

                ReadDictionary.readDict(recognizedText.trim());
            }).start();
        });

        search.setOnAction(e -> {
            DisplaySearchBox.show();
        });

        draw.setOnAction(e -> {
            DisplayPaintFX.show();
        });

        undo.setOnAction(e -> {
            if (FileSelector.tempFile != null) {
                try {
                    FileSelector.file = FileSelector.tempFile;
                    String filePath = FileSelector.tempFile.toURI().toURL().toExternalForm();
                    DisplayImageView.imageView.setImage(new Image(filePath));
                } catch (MalformedURLException ex) {
                }
            }
        });

        exit.setOnAction(e -> {
            primaryStage.close();
        });

        DisplayImageView.imageView.imageProperty().addListener((o, oV, nV) -> {
            if (DisplayImageView.imageView.getImage() != null) {
                ocr.setDisable(false);
            } else {
                ocr.setDisable(true);
            }
        });
    }
}
