package utils.operations.ocr;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import utils.operations.imageprocessing.ImageProcessing;

/**
 *
 * @author Luis
 */
public class TessOCR {

    private static final ITesseract TESSERACT_INSTANCE = new Tesseract();

    public static void init() {
        TESSERACT_INSTANCE.setDatapath("docs/tessdata");
        TESSERACT_INSTANCE.setLanguage("jpn");
    }

    public static String recognize(File file) {
        try {
            byte[][] imageByteArray = ImageProcessing.getGrayByteImageArray2DFromBufferedImage(ImageProcessing.toGray(ImageIO.read(file)));
            BufferedImage image = ImageProcessing.setGrayByteImageArray2DToBufferedImage(ImageProcessing.threshold(imageByteArray, 128));

            return TESSERACT_INSTANCE.doOCR(image);
        } catch (IOException | TesseractException ex) {
            return "";
        }
    }
}
