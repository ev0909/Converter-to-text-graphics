package ru.netology.graphics;

import ru.netology.graphics.image.ColorSchema;
import ru.netology.graphics.image.GraphicsConverter;
import ru.netology.graphics.image.TextGraphicsConverter;
import ru.netology.graphics.server.GServer;

import java.io.File;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) throws Exception {
        TextGraphicsConverter converter = new GraphicsConverter();

        GServer server = new GServer(converter);
        server.start();

    }
}
