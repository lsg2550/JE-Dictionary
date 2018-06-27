package utils.operations.imageprocessing;

import gfx.DisplayImageView;
import gfx.DisplayWindow;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javax.imageio.ImageIO;
import utils.io.FileSelector;

/**
 *
 * @author Luis
 */
public class Crop {

    private static RubberBandSelection rubberBandSelection;

    public static Group crop(ImageView imageView) {
        //Image layer: a group of images
        Group imageLayer = new Group();
        imageLayer.getChildren().add(imageView);
        //imageView.setFitHeight(250.0);
        //imageView.setFitWidth(250.0);
        imageView.setPreserveRatio(true);

        // Rubberband selection
        rubberBandSelection = new RubberBandSelection(imageLayer);

        // Create context menu and menu items
        ContextMenu contextMenu = new ContextMenu();
        MenuItem cropMenuItem = new MenuItem("Crop");

        cropMenuItem.setOnAction(e -> {
            // Get bounds for image crop
            Bounds selectionBounds = rubberBandSelection.getBounds();

            // Crop the image
            crop(imageView, selectionBounds);
        });

        contextMenu.getItems().add(cropMenuItem);

        // Set context menu on image layer
        imageLayer.setOnMousePressed(e -> {
            if (e.isSecondaryButtonDown()) {
                contextMenu.show(imageLayer, e.getScreenX(), e.getScreenY());
            }
        });

        return imageLayer;
    }

    private static void crop(ImageView imageView, Bounds bounds) {
        int width = (int) bounds.getWidth(), height = (int) bounds.getHeight();

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        parameters.setViewport(new Rectangle2D(bounds.getMinX(), bounds.getMinY(), width, height));

        WritableImage wi = new WritableImage(width, height);
        imageView.snapshot(parameters, wi);
        imageView.setImage(wi);
        DisplayImageView.setImage(imageView.getImage());

        try {
            File newFile = new File("docs/temp/croppedImage.png");
            ImageIO.write(SwingFXUtils.fromFXImage(wi, null), "png", newFile);
            FileSelector.tempFile = FileSelector.file;
            FileSelector.file = newFile;
            rubberBandSelection.clearRubberBandSelection();
        } catch (IOException ex) {
            System.out.println("There is no previous image or the previous image was moved.");
        }

        DisplayWindow.close();
    }

    /**
     * Drag rectangle with mouse cursor in order to get selection bounds
     */
    protected static class RubberBandSelection {

        final DragContext dragContext = new DragContext();
        Rectangle rect = new Rectangle();
        Group group;

        protected Bounds getBounds() {
            return rect.getBoundsInParent();
        }

        protected RubberBandSelection(Group group) {
            this.group = group;

            rect = new Rectangle(0, 0, 0, 0);
            rect.setStroke(Color.BLUE);
            rect.setStrokeWidth(1);
            rect.setStrokeLineCap(StrokeLineCap.ROUND);
            rect.setFill(Color.LIGHTBLUE.deriveColor(0, 1.2, 1, 0.6));

            group.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
            group.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
            group.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);
        }

        protected void clearRubberBandSelection() {
            rect.setX(0);
            rect.setY(0);
            rect.setWidth(0);
            rect.setHeight(0);
        }

        protected EventHandler<MouseEvent> onMousePressedEventHandler = e -> {
            if (e.isSecondaryButtonDown()) {
                return;
            }

            // remove old rect
            rect.setX(0);
            rect.setY(0);
            rect.setWidth(0);
            rect.setHeight(0);

            group.getChildren().remove(rect);

            // prepare new drag operation
            dragContext.mouseAnchorX = e.getX();
            dragContext.mouseAnchorY = e.getY();

            rect.setX(dragContext.mouseAnchorX);
            rect.setY(dragContext.mouseAnchorY);
            rect.setWidth(0);
            rect.setHeight(0);

            group.getChildren().add(rect);
        };

        protected EventHandler<MouseEvent> onMouseDraggedEventHandler = e -> {
            if (e.isSecondaryButtonDown()) {
                return;
            }

            double offsetX = e.getX() - dragContext.mouseAnchorX;
            double offsetY = e.getY() - dragContext.mouseAnchorY;

            if (offsetX > 0) {
                rect.setWidth(offsetX);
            } else {
                rect.setX(e.getX());
                rect.setWidth(dragContext.mouseAnchorX - rect.getX());
            }

            if (offsetY > 0) {
                rect.setHeight(offsetY);
            } else {
                rect.setY(e.getY());
                rect.setHeight(dragContext.mouseAnchorY - rect.getY());
            }
        };

        protected EventHandler<MouseEvent> onMouseReleasedEventHandler = e -> {
            if (e.isSecondaryButtonDown()) {
            }
        };

        private static final class DragContext {

            public double mouseAnchorX;
            public double mouseAnchorY;
        }
    }
}
