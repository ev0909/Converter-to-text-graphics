package ru.netology.graphics.image;



public class ColorSchema implements TextColorSchema {

    private final char[] SYMBOLS = new char[]{'#', '$', '@', '%', '*', '+', '-', '"'};

    @Override
    public char convert(int color) {
        return SYMBOLS[(int) Math.floor(color / 255.0 * SYMBOLS.length)];
    }
}
