package gfx;

import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;
import utils.io.FileSelector;

/**
 *
 * @author Luis
 */
class DisplayPaintFX {

    private static final int WIDTH = 800, HEIGHT = 600;
    private static Scene scene;
    private static final Canvas CANVAS = new Canvas(WIDTH, HEIGHT);
    private static final GraphicsContext GRAPHICS_CONTEXT = CANVAS.getGraphicsContext2D();

    protected static void init() {
        GRAPHICS_CONTEXT.setFill(Color.WHITE);
        GRAPHICS_CONTEXT.fillRect(0, 0, WIDTH, HEIGHT);

        ContextMenu cm = new ContextMenu();
        MenuItem clear = new MenuItem("Clear"), save = new MenuItem("Save");
        cm.getItems().addAll(clear, save);

        StackPane root = new StackPane(CANVAS);
        root.setAlignment(Pos.CENTER);
        scene = new Scene(root, WIDTH, HEIGHT);

        //HANDLERS
        clear.setOnAction(e -> {
            clear();
            cm.hide();
        });

        save.setOnAction(e -> {
            WritableImage wi = new WritableImage(WIDTH, HEIGHT);
            CANVAS.snapshot(null, wi);
            DisplayImageView.setImage(wi);

            try {
                File newFile = new File("docs/temp/croppedImage.png");
                newFile.mkdirs(); //If directory does not exist, create it...
                ImageIO.write(SwingFXUtils.fromFXImage(wi, null), "png", newFile);
                FileSelector.tempFile = FileSelector.file;
                FileSelector.file = newFile;
            } catch (IOException ex) {
            }

            //cm.hide();
            DisplayWindow.close();
        });

        CANVAS.setOnMousePressed(e -> {
            if (e.isSecondaryButtonDown()) {
                if (cm.isShowing()) {
                    cm.hide();
                } else {
                    cm.show(CANVAS, e.getScreenX(), e.getScreenY());
                }
            }
        });

        CANVAS.setOnMouseMoved((MouseEvent event) -> {
            if (!event.isSecondaryButtonDown()) {
                PaintApp.mousePressed(event);
            }
        });

        CANVAS.setOnMouseDragged((MouseEvent event) -> {
            if (!event.isSecondaryButtonDown()) {
                PaintApp.mouseDragged(event);
            }
        });
    }

    private static void clear() { //Clear Canvas
        GRAPHICS_CONTEXT.fillRect(0, 0, WIDTH, HEIGHT);
    }

    protected static void show() {
        clear(); //Clear Canvas

        DisplayWindow.setResizable(false);
        DisplayWindow.setScene(scene);
        DisplayWindow.show();
    }

    private static class PaintApp {

        private static double oldX, oldY, newX, newY;

        public static void mousePressed(MouseEvent evt) {
            oldX = evt.getX();
            oldY = evt.getY();
            newX = evt.getX();
            newY = evt.getY();
        }

        public static void mouseDragged(MouseEvent evt) {
            newX = evt.getX();
            newY = evt.getY();

            GRAPHICS_CONTEXT.setLineWidth(10);
            GRAPHICS_CONTEXT.setStroke(Color.BLACK);
            GRAPHICS_CONTEXT.strokeLine(oldX, oldY, newX, newY);
            oldX = newX;
            oldY = newY;
        }
    }
}

//HA  HI  FU HE  HO
//は　ひ　ふ　へ　ほ
