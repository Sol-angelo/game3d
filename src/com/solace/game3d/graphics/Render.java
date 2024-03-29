package com.solace.game3d.graphics;

import com.solace.game3d.Display;

public class Render {
    public final int width;
    public final int height;
    public final int[] pixels;


    public Render(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width*height];
    }

    public void draw(Render render, int xOffs, int yOffs) {
        for (int y = 0; y < render.height; y++) {
            int yPix = y + yOffs;
            if (yPix < 0 || yPix >= Display.height) {
                continue;
            }
            for (int x = 0; x < render.width; x++) {
                int xPix = x + xOffs;
                if (xPix < 0 || xPix >= Display.width) {
                    continue;
                }
                int alpha = render.pixels[x+y* render.width];
                if (alpha > 0) {
                    pixels[xPix + yPix * width] = alpha;
                }
            }
        }
    }
}
