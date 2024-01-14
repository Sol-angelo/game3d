package com.solace.game3d.input;

public class Controller {
    public double x, z, rotation, xa, za, rotationa;

    public void tick(boolean forward, boolean back, boolean left, boolean right, boolean turnLeft, boolean turnRight) {
        double rotationSpeed = 0.01;
        double walkSpeed = 10;
        double xMove = 0;
        double zMove = 0;

        if (forward) zMove++;
        if (back) zMove--;
        if (right) xMove++;
        if (left) xMove--;
        if (turnLeft) rotationa -= rotationSpeed;
        if (turnRight) rotationa += rotationSpeed;

        xa += (xMove * Math.cos(rotation) + zMove * Math.sin(rotation)) * walkSpeed;
        za += (zMove * Math.cos(rotation) + xMove * Math.sin(rotation)) * walkSpeed;

        x += xa;
        z += za;
        xa *= 0.1;
        za *= 0.1;
        rotation += rotationa;
        rotationa *= 0.8;
    }
}
