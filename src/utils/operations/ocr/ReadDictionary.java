package utils.operations.ocr;

import gfx.DisplayTableView;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.application.Platform;
import utils.benchmarking.Logging;
import utils.definitions.DefinitionBlock;

/**
 *
 * @author Luis
 */
public class ReadDictionary {

    public static void readDict(String lookUp) {
        System.out.println("Default Charset: " + Charset.defaultCharset());
        System.out.println("Recognized String: " + lookUp);

        DisplayTableView.getTableView().getItems().clear();
        int counter = 0;

        if (!lookUp.equals("")) {
            try (BufferedReader br = Files.newBufferedReader(Paths.get("docs/dictionary/EDICT/edict2u"), StandardCharsets.UTF_8)) {
                for (String line = null; (line = br.readLine()) != null;) {
                    Logging.setStartTime(); //Start Time

                    if (line.contains(lookUp)) {
                        final DefinitionBlock temp = new DefinitionBlock(line.split("/"));

                        if (counter == 0) {
                            Platform.runLater(() -> {
                                temp.display();
                            });
                            counter++;
                        }

                        Platform.runLater(() -> {
                            DisplayTableView.getTableView().getItems().add(temp);
                        });
                    }

                    Logging.setEndTime(); //End Time
                }
            } catch (IOException ex) {
            }

            System.out.println("Time to Scan: " + Logging.benchmarkTime() + "ms");
        }
    }
}
