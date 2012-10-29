package org.exp.rgb.gui.tools;

import java.awt.Color;

public class RgbVals {

    private int red;
    private int green;
    private int blue;

    public RgbVals(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public RgbVals(RgbVals orig) {
        red = orig.getRed();
        green = orig.getGreen();
        blue = orig.getBlue();
    }

    @Override
    public String toString() {
        return String.format("(%3d,%3d,%3d)", red, green, blue);
    }

    public Color toColor(){
        return new Color(red,green,blue);
    }

    public Color toColor(boolean hasRed, boolean hasGreen, boolean hasBlue) {
        int r = (hasRed) ? red : 0;
        int g = (hasGreen) ? green : 0;
        int b = (hasBlue) ? blue : 0;
        return new Color(r, g, b);
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }
}
