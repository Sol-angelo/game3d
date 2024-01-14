package com.solace.game3d.graphics;

import com.solace.game3d.Game;

import java.util.Random;

public class Screen extends Render {

    private Render test;
    private Render3D render;

    public Screen(int width, int height) {
        super(width, height);
        Random random = new Random();
        test = new Render(256, 256);
        render = new Render3D(width, height);
        for (int i = 0; i < 256 * 256; i++){
            test.pixels[i] = random.nextInt() * (random.nextInt(5)/4);
        }
    }

    public void render(Game game) {
        for (int i = 0; i<width*height; i++) {
            pixels[i] = 0;
        }
        for (int i = 0; i < 50; i++) {
            int anim1 = (int) (Math.sin((game.time + i) % 1000.0 / 100) * 100);
            int anim2 = (int) (Math.cos((game.time + i) % 1000.0 / 100) * 100);
            //draw(test, (width - 256) / 2 + anim1, (height - 256) / 2 + anim2);
        }

        render.floor(game);
        render.renderDistanceLimiter();
        draw(render, 0, 0);
    }
}
