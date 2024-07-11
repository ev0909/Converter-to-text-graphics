package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;

public class GraphicsConverter implements TextGraphicsConverter{
    private double maxRatio = Double.MAX_VALUE;
    private int maxWidth = Integer.MAX_VALUE;
    private int maxHeight = Integer.MAX_VALUE;

    private TextColorSchema schema = new ColorSchema();

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {

        BufferedImage img = ImageIO.read(new URL(url));

        checkRatio(img.getWidth(), img.getHeight());
        int newWidth = newSizeWidth(img.getWidth(), img.getHeight());
        int newHeight = newSizeHeight(img.getWidth(), img.getHeight());

        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);

        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        ImageIO.write(bwImg, "png", new File("out.png"));

        WritableRaster bwRaster = bwImg.getRaster();

        StringBuilder sb = new StringBuilder();

        for (int h = 0; h < newHeight; h++) {
            for (int w = 0; w < newWidth; w++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char c = schema.convert(color);
                sb.append(c).append(c);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private void checkRatio(int width, int height) throws BadImageSizeException {
        double imgRatioForWidePic = (double) width / height;
        double imgRatioForHighPic = (double) height / width;
        if (width > height && imgRatioForWidePic > maxRatio) {
            throw new BadImageSizeException(imgRatioForWidePic, maxRatio);
        }
        if (width < height && imgRatioForHighPic > maxRatio) {
            throw new BadImageSizeException(imgRatioForHighPic, maxRatio);
        }
    }

    private int newSizeWidth(int width, int height) {
        double HeightDifference = (double) height / maxHeight;
        int newWidth;
        if (width >= height) {
            newWidth = Math.min(maxWidth, width);
        } else {
            BigDecimal b1 = new BigDecimal(Double.toString(HeightDifference));
            BigDecimal b2 = new BigDecimal(width);
            BigDecimal result = b2.divide(b1, 2, RoundingMode.HALF_UP);
            newWidth = result.intValue();
        }
        return newWidth;
    }

    private int newSizeHeight(int width, int height) {
        double widthDifference = (double) width / maxWidth;
        int newHeight;
        if (width > height) {
            BigDecimal b1 = new BigDecimal(Double.toString(widthDifference));
            BigDecimal b2 = new BigDecimal(height);
            BigDecimal result = b2.divide(b1, 2, RoundingMode.HALF_UP);
            newHeight = result.intValue();
        } else {
            newHeight = Math.min(maxHeight, height);
        }
        return newHeight;
    }

    @Override
    public void setMaxWidth(int width) {
        this.maxWidth = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.maxHeight = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
    }
}


