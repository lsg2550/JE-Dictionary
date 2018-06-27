package gfx;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import utils.definitions.DefinitionBlock;

/**
 *
 * @author Luis
 */
public class DisplayTableView {

    private static final TableView<DefinitionBlock> TABLEVIEW_4_WORDDEFINITION = new TableView<DefinitionBlock>();
    private static final TableColumn<DefinitionBlock, String> TABLECOLUMN_4_WORDDEFINITION = new TableColumn<DefinitionBlock, String>("Other Recognized Characters");

    protected static void init() {
        TABLECOLUMN_4_WORDDEFINITION.setCellValueFactory(e -> {
            return new SimpleStringProperty(e.getValue().getWord());
        });

        TABLECOLUMN_4_WORDDEFINITION.prefWidthProperty().bind(TABLEVIEW_4_WORDDEFINITION.widthProperty());
        TABLECOLUMN_4_WORDDEFINITION.setResizable(false);

        TABLEVIEW_4_WORDDEFINITION.getColumns().add(TABLECOLUMN_4_WORDDEFINITION);
        TABLEVIEW_4_WORDDEFINITION.maxWidth(Double.MAX_VALUE);
        TABLEVIEW_4_WORDDEFINITION.getSelectionModel().selectedItemProperty().addListener(e -> {
            if (TABLEVIEW_4_WORDDEFINITION.getSelectionModel().getSelectedItem() != null) {
                TABLEVIEW_4_WORDDEFINITION.getSelectionModel().getSelectedItem().display();
            }
        });
    }

    public static TableView<DefinitionBlock> getTableView() {
        return TABLEVIEW_4_WORDDEFINITION;
    }
}
