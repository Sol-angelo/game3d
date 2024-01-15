package com.solace.game3d.graphics;

import com.solace.game3d.Game;
import com.solace.game3d.input.Controller;

public class Render3D extends Render {

    public double[] zBuffer;
    private final double renderDistance = 10000;
    public Render3D(int width, int height) {
        super(width, height);
        zBuffer = new double[width * height];
    }

    public void floor(Game game) {
        double floorPosition = 8;
        double verticalPosition = 800;
        double forward = game.controls.z;
        double right = game.controls.x;
        double up = game.controls.y;
        double walking = Math.sin(game.time / 6.0) * 0.4;
        if (Controller.crouching) walking = Math.sin(game.time / 6.0) * 0.2;
        if (Controller.running) walking = Math.sin(game.time / 6.0) * 0.7;
        double rotation = game.controls.rotation;
        double cosine = Math.cos(rotation);
        double sine = Math.sin(rotation);
        for (int y = 0; y < height; y++) {
            double ceiling = (y - height / 2.0) / height;
            double z = (floorPosition + up) / ceiling;
            if (ceiling < 0) z = (verticalPosition - up) / -ceiling;
            if (Controller.walk) {
                z = (floorPosition + up + walking) / ceiling;
                if (ceiling < 0) z = (verticalPosition - up - walking) / -ceiling;
            }
            for (int x = 0; x < width; x++) {
                double depth = (x - width / 2.0) / height;
                depth *= z;
                double xx = depth * cosine + z * sine;
                double yy = z * cosine - depth * sine;
                int xPix = (int) (xx+right);
                int yPix = (int) (yy+forward);
                zBuffer[x+y*width] = z;
                pixels[x + y * width] = Texture.floor.pixels[(xPix & 7) + (yPix & 7) *16];
            }
        }

    }

    public void renderDistanceLimiter() {
        for (int i = 0; i < width * height; i++) {
            int colour = pixels[i];
            int brightness = (int) (renderDistance / (zBuffer[i]));

            if (brightness < 0) {
                brightness = 0;
            }
            if (brightness > 255) {
                brightness = 255;
            }

            int r = (colour >> 16) & 0xff;
            int g = (colour >> 8) & 0xff;
            int b = (colour) & 0xff;

            r = r*brightness >>> 8;
            g = g*brightness >>> 8;
            b = b*brightness >>> 8;

            pixels[i] = r << 16 | g << 8 | b;
        }
    }
}
