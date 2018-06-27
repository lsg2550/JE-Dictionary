package utils.definitions;

import gfx.DisplayTextArea;

/**
 *
 * @author Luis
 */
public class DefinitionBlock {

    private final String INDEX_PATTERN = "^EntL[0-9]{1,10}[a-zA-Z]{0,2}$"; //Regular Expression to Remove Reference ID From Definition;

    private String[] definitionBlock;
    private String kanji, kana;

    public DefinitionBlock() {
    }

    public DefinitionBlock(String[] input) {
        definitionBlock = input;
        splitKanjiAndKana();
    }

    public String getWord() { //Because of the dictionary's format, the word is always the first index
        return definitionBlock[0];
    }

    public String getKanji() { //Kanji
        return kanji;
    }

    public String getKana() { //Hiragana/Katakana
        return kana;
    }

    public String[] getWordDefinition() { //Full Definition Block
        return definitionBlock;
    }

    public void setWordDefinition(String[] wordDefinition) {
        this.definitionBlock = wordDefinition;
    }

    private void splitKanjiAndKana() {
        try {
            String[] temp = definitionBlock[0].split("\\[");
            kanji = temp[0].trim();
            kana = temp[1].replace("]", "");
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    public void display() {
        DisplayTextArea.getTextArea().clear(); //Clears TextArea to Display New Definition

        //Grabs each line in the definition, and checks if the line contains the reference, if it does not, then print the line into the TextArea
        for (String string : definitionBlock) {
            if (!string.matches(INDEX_PATTERN)) {
                DisplayTextArea.getTextArea().appendText(string + "\n");
            }
        }

        DisplayTextArea.getTextArea().appendText("\nKanji: " + kanji + "\nKana: " + kana);
    }
}
