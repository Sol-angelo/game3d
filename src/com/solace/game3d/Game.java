package com.solace.game3d;

import com.solace.game3d.input.Controller;

import java.awt.event.KeyEvent;

public class Game {
    public int time;
    public Controller controls;

    public Game() {
        controls = new Controller();
    }

    public void tick(boolean[] key) {
        time++;
        boolean forward = key[KeyEvent.VK_W];
        boolean backward = key[KeyEvent.VK_S];
        boolean left = key[KeyEvent.VK_A];
        boolean right = key[KeyEvent.VK_D];
        boolean jump = key[KeyEvent.VK_SPACE];
        boolean crouch = key[KeyEvent.VK_SHIFT];
        boolean run = key[KeyEvent.VK_CONTROL];
        boolean f3 = key[KeyEvent.VK_F3];
        controls.tick(forward, backward, left, right, jump, crouch, run, f3);
    }

}
