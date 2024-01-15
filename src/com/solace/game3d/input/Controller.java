package com.solace.game3d.input;

import java.awt.image.RasterFormatException;

public class Controller {
    public double x, y, z, rotation, xa, za, rotationa;
    public static boolean turnLeft = false;
    public static boolean turnRight = false;
    public static boolean f3Enabled = false;
    public static boolean walk = false;
    public static boolean crouching = false;
    public static boolean running = false;

    public void tick(boolean forward, boolean back, boolean left, boolean right, boolean jump, boolean crouch, boolean run, boolean f3) {
        double rotationSpeed = 0.01;
        double walkSpeed = 0.5;
        double jumpHeight = 0.5;
        double crouchHeight = 0.3;
        double xMove = 0;
        double zMove = 0;

        if (forward) {
            zMove++;
            walk = true;
        }
        if (back) {
            zMove--;
            walk = true;
        }
        if (right) {
            xMove++;
            walk = true;
        }
        if (left) {
            xMove--;
            walk = true;
        }
        if (turnLeft) rotationa -= rotationSpeed;
        if (turnRight) rotationa += rotationSpeed;

        if (jump) {
            y += jumpHeight;
            run = false;
        }
        if (crouch) {
            y -= crouchHeight;
            walkSpeed *= 0.7;
            run = false;
            crouching = true;
        }

        if (run) {
            walkSpeed = 1;
            running = true;
        }

        if (!forward && !back && !right && !left) {
            walk = false;
        }

        if (!crouch) {
            crouching = false;
        }

        if (!run) {
            running = false;
        }

        if (f3) f3Enabled = !f3Enabled;
        xa += (xMove * Math.cos(rotation) + zMove * Math.sin(rotation)) * walkSpeed;
        za += (zMove * Math.cos(rotation) - xMove * Math.sin(rotation)) * walkSpeed;

        x += xa;
        y *= 0.9;
        z += za;
        xa *= 0.1;
        za *= 0.1;
        rotation += rotationa;
        rotationa *= 0.8;
    }
}
