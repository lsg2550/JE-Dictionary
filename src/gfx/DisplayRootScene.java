package gfx;

import javafx.scene.Scene;

/**
 *
 * @author Luis
 */
class DisplayRootScene {

    private static Scene scene;

    protected static void init(Scene scene) {
        DisplayRootScene.scene = scene;
    }

    protected static Scene getScene() {
        return scene;
    }
}
