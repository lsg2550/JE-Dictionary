package utils.operations.imageprocessing;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;

/**
 *
 * @author Luis
 */
public class ImageProcessing {

    public static BufferedImage toGray(BufferedImage original) {
        BufferedImage grayKeyImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d = grayKeyImage.createGraphics();
        g2d.drawImage(original, 0, 0, null);
        return grayKeyImage;
    }

    public static byte[][] getGrayByteImageArray2DFromBufferedImage(BufferedImage image) {
        byte[] byteData_1d;
        int Rows = image.getHeight();
        int Cols = image.getWidth();

        byteData_1d = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        byte[][] byteData_2d = new byte[Rows][Cols];
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Cols; j++) {
                byteData_2d[i][j] = byteData_1d[i * Cols + j];
            }
        }

        return byteData_2d;
    }

    public static BufferedImage setGrayByteImageArray2DToBufferedImage(byte[][] byteData_2d) {
        int width = byteData_2d[0].length;
        int height = byteData_2d.length;
        byte[] byteData_1d = new byte[width * height];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                byteData_1d[i * width + j] = byteData_2d[i][j];
            }
        }
        int dataType = DataBuffer.TYPE_BYTE;

        DataBufferByte buffer = new DataBufferByte(byteData_1d, byteData_1d.length);

        int cs = ColorSpace.CS_GRAY;
        ColorSpace cSpace = ColorSpace.getInstance(cs);
        ComponentColorModel ccm;
        if (dataType == DataBuffer.TYPE_INT || dataType == DataBuffer.TYPE_BYTE) {
            ccm = new ComponentColorModel(cSpace,
                    ((cs == ColorSpace.CS_GRAY)
                            ? new int[]{8} : new int[]{8, 8, 8}),
                    false, false, Transparency.OPAQUE, dataType);
        } else {
            ccm = new ComponentColorModel(cSpace, false, false, Transparency.OPAQUE, dataType);
        }

        SampleModel sm = ccm.createCompatibleSampleModel(width, height);
        WritableRaster raster = Raster.createWritableRaster(sm, buffer, new Point(0, 0));
        return new BufferedImage(ccm, raster, false, null);
    }

    public static byte[][] threshold(byte[][] original, int th) {
        int h = original.length;
        int w = original[0].length;

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if ((int) (original[i][j] & 0xFF) >= th) {
                    original[i][j] = (byte) 255;
                } else {
                    original[i][j] = (byte) 0;
                }
            }
        }
        return original;
    }

    public static byte[][] dilate(byte[][] input, byte[][] output) { //Grows White
        int topLeft, topCenter, topRight,
                left, center, right,
                bottomLeft, bottomCenter, bottomRight;

        for (int i = 1; i < output.length - 1; i++) {
            for (int j = 1; j < output[0].length - 1; j++) {

                topLeft = input[i - 1][j - 1] & 0xFF;
                left = input[i - 1][j] & 0xFF;
                bottomLeft = input[i - 1][j + 1] & 0xFF;
                topCenter = input[i][j - 1] & 0xFF;
                center = input[i][j] & 0xFF;
                bottomCenter = input[i][j + 1] & 0xFF;
                topRight = input[i + 1][j - 1] & 0xFF;
                right = input[i + 1][j] & 0xFF;
                bottomRight = input[i + 1][j + 1] & 0xFF;

                if (topLeft == 0 && topCenter == 0 && topRight == 0
                        && left == 0 && center == 0 && right == 0
                        && bottomLeft == 0 && bottomCenter == 0 && bottomRight == 0) {
                    output[i][j] = (byte) 0;
                } else {
                    output[i][j] = (byte) 255;
                }
            }
        }
        return output;
    }

    public static byte[][] erosion(byte[][] input, byte[][] output) { //Grows Black
        int topLeft, topCenter, topRight,
                left, center, right,
                bottomLeft, bottomCenter, bottomRight;

        for (int i = 1; i < output.length - 1; i++) {
            for (int j = 1; j < output[0].length - 1; j++) {

                topLeft = input[i - 1][j - 1] & 0xFF;
                left = input[i - 1][j] & 0xFF;
                bottomLeft = input[i - 1][j + 1] & 0xFF;
                topCenter = input[i][j - 1] & 0xFF;
                center = input[i][j] & 0xFF;
                bottomCenter = input[i][j + 1] & 0xFF;
                topRight = input[i + 1][j - 1] & 0xFF;
                right = input[i + 1][j] & 0xFF;
                bottomRight = input[i + 1][j + 1] & 0xFF;

                if (topLeft == 255 && topCenter == 255 && topRight == 255
                        && left == 255 && center == 255 && right == 255
                        && bottomLeft == 255 && bottomCenter == 255 && bottomRight == 255) {
                    output[i][j] = (byte) 255;
                } else {
                    output[i][j] = (byte) 0;
                }
            }
        }
        return output;
    }

    public static byte[][] middleOutline(byte[][] input) {
        byte[][] outputDilate = new byte[input.length][input[0].length];
        byte[][] outputErode = new byte[input.length][input[0].length];
        byte[][] output = new byte[input.length][input[0].length];

        outputDilate = dilate(input, outputDilate);
        outputErode = erosion(input, outputErode);

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                output[i][j] = (byte) (outputDilate[i][j] - outputErode[i][j]);
            }
        }
        return output;
    }

    public static byte[][] insideOutline(byte[][] input, byte[][] output) {
        byte[][] tempErosion = erosion(input, output);

        for (int i = 0; i < output.length; i++) {
            for (int j = 0; j < output[0].length; j++) {
                output[i][j] = (byte) (input[i][j] - tempErosion[i][j]);
            }
        }
        return output;
    }

    public static byte[][] outsideOutline(byte[][] input, byte[][] output) {
        byte[][] tempDilate = dilate(input, output);

        for (int i = 0; i < output.length; i++) {
            for (int j = 0; j < output[0].length; j++) {
                output[i][j] = (byte) (tempDilate[i][j] - input[i][j]);
            }
        }
        return output;
    }

    public static byte[][] opening(byte[][] input) {
        byte[][] outputErode = new byte[input.length][input[0].length];
        byte[][] outputDilate = new byte[input.length][input[0].length];

        outputErode = erosion(input, outputErode);
        outputDilate = dilate(outputErode, outputDilate);

        return outputDilate;
    }

    public static byte[][] closing(byte[][] input) {
        byte[][] outputDilate = new byte[input.length][input[0].length];
        byte[][] outputErode = new byte[input.length][input[0].length];

        outputDilate = dilate(input, outputDilate);
        outputErode = erosion(outputDilate, outputErode);

        return outputErode;
    }
}
