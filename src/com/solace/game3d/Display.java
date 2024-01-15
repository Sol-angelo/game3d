package com.solace.game3d;

import com.solace.game3d.graphics.Render;
import com.solace.game3d.graphics.Screen;
import com.solace.game3d.input.Controller;
import com.solace.game3d.input.InputHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.Serial;

public class Display extends Canvas implements Runnable {
    @Serial
    private static final long serialVersionUID = 1L;
    public static final int width = 800;
    public static final int height = 600;
    public static final String title = "3D Game";
    private Screen screen;
    private BufferedImage img;
    private Thread thread;
    private Game game;
    private InputHandler input;
    private int newX;
    private int newY;
    private int oldX;
    private int oldY;
    private int[] pixels;
    private boolean running = false;
    private String fps;

    public Display() {
        Dimension size = new Dimension(width, height);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        screen = new Screen(width, height);
        game = new Game();
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
        input = new InputHandler();
        addKeyListener(input);
        addMouseListener(input);
        addMouseMotionListener(input);
        addFocusListener(input);
    }

    private void start() {
        if (running) return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private void stop() {
        if (!running) return;
        running = false;
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void run() {
        int frames = 0;
        double unprocessedSeconds = 0;
        long previousTime = System.nanoTime();
        double secondsPerTick = 1/ 60.0;
        int tickCount = 0;
        boolean ticked = false;
        this.requestFocus();
        while (running) {
            long currentTime = System.nanoTime();
            long passedTime = currentTime - previousTime;
            previousTime = currentTime;
            unprocessedSeconds += passedTime / 1000000000.0;

            while  (unprocessedSeconds > secondsPerTick) {
                tick();
                unprocessedSeconds -= secondsPerTick;
                ticked = true;
                tickCount++;
                if (tickCount % 60 == 0) {
                    //System.out.println(frames + " fps");
                    fps = String.valueOf(frames);
                    previousTime += 1000;
                    frames = 0;
                }
            }

            if (ticked) {
                render();
                frames++;
            }
            render();
            frames++;

            newX = InputHandler.mouseX;

            if (newX > oldX) {
                Controller.turnRight = true;
                Controller.turnLeft = false;
            }
            if (newX < oldX) {
                Controller.turnRight = false;
                Controller.turnLeft = true;
            }
            if (newX == oldX) {
                Controller.turnRight = false;
                Controller.turnLeft = false;
            }

            oldX = newX;
        }
    }

    private void tick() {
        game.tick(input.key);
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        screen.render(game);

        for (int i = 0; i < width*height; i++) {
            pixels[i] = screen.pixels[i];
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(img, 0, 0, width, height, null);
        g.setColor(Color.white);
        g.setFont(new Font("Verdana", 0, 20));
        if (Controller.f3Enabled) g.drawString("FPS: "+fps, 10, 25);
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "blank");
        Display game = new Display();
        JFrame frame = new JFrame();
        frame.add(game);
        frame.getContentPane().setCursor(blank);
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.pack();

        game.start();
    }
}
