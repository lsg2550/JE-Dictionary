package gfx;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utils.operations.ocr.ReadDictionary;
import utils.definitions.DefinitionBlock;

/**
 *
 * @author Luis
 */
class DisplaySearchBox {

    private static final VBox SEARCHBOX_VBOX = new VBox(5);
    private static final Scene SEARCHBOX_SCENE = new Scene(SEARCHBOX_VBOX, 300, 100);

    protected static void init() {
        HBox hbox = new HBox(5), hbox2 = new HBox();

        RadioButton dictionary = new RadioButton("Dictionary"), //Searches dictionary
                tableView = new RadioButton("TableView"); //Searches tableview
        ToggleGroup searchOption = new ToggleGroup();
        searchOption.getToggles().addAll(dictionary, tableView);
        dictionary.setSelected(true);

        TextField textField = new TextField();
        Label label = new Label("Search: ");
        Button button = new Button("Submit");

        button.setOnAction(e -> {
            DisplayWindow.close();

            if (dictionary.isSelected()) {
                DisplayRecognizedText.setText(textField.getText());
                ReadDictionary.readDict(textField.getText());
            } else {
                for (DefinitionBlock wordDef : DisplayTableView.getTableView().getItems()) {
                    if (wordDef.getKanji().equals(textField.getText()) || wordDef.getKana().equals(textField.getText())) {
                        wordDef.display();

                        DisplayRecognizedText.setText(textField.getText());

                        int loc = DisplayTableView.getTableView().getItems().indexOf(wordDef);
                        DisplayTableView.getTableView().requestFocus();
                        DisplayTableView.getTableView().getSelectionModel().focus(loc);
                        DisplayTableView.getTableView().getFocusModel().focus(loc, DisplayTableView.getTableView().getColumns().get(0));
                        break;
                    }
                }
            }

            textField.setText("");
        });

        hbox.getChildren().addAll(dictionary, tableView);
        hbox2.getChildren().addAll(label, textField);
        SEARCHBOX_VBOX.getChildren().addAll(hbox, hbox2, button);

        hbox.setAlignment(Pos.CENTER);
        hbox2.setAlignment(Pos.CENTER);
        SEARCHBOX_VBOX.setAlignment(Pos.CENTER);
    }

    protected static void show() {
        DisplayWindow.setScene(SEARCHBOX_SCENE);
        DisplayWindow.setResizable(false);
        DisplayWindow.show();
    }

}
