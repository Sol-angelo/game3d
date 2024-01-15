package com.solace.game3d.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Texture {
    public static Render floor = loadBitmap("/textures/floor.png");

    public static Render loadBitmap (String filename) {
        try {
            BufferedImage image = ImageIO.read(Texture.class.getResource(filename));
            int width = image.getWidth();
            int height = image.getHeight();
            Render result = new Render(width, height);
            image.getRGB(0, 0, width, height, result.pixels, 0, width);
            return result;
        } catch (Exception e) {
            System.out.println("Texture could not load at path "+filename);
            throw new RuntimeException(e);
        }
    }

}
