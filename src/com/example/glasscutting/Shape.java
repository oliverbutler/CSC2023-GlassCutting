package com.example.glasscutting;

/**
 * @Purpose: The shape class represents a single shape. DO NOT MODIFY THE
 *           SIGNITURE OF EXISTING METHODS, You may add additional methods if
 *           you wish
 * 
 * @author RYK
 * @since 30/10/2019 extended by @author Oliver Butler
 * 
 **/

public class Shape {

    private int sWidth; // width of the shape
    private int sHeight; // height of the shape

    public enum orientation {
        VERTICAL, HORIZONTAL
    }

    /**
     * A Shape constructor to set the width and height of a shape.
     * 
     * @param width  of a shape
     * @param height of a shape
     **/
    public Shape(int width, int height) {

        // Shape width and height should not be greater than the sheet width and height
        if (width > Sheet.SHEET_WIDTH || height > Sheet.SHEET_HEIGHT) {
            throw new IllegalArgumentException("Shape width or height is not valid");
        }

        this.sWidth = width;
        this.sHeight = height;
    }

    /**
     * Rotate a shape
     */
    public void rotate() {
        int tempWidth = this.sWidth;
        this.sWidth = this.sHeight;
        this.sHeight = tempWidth;
    }

    /**
     * Make either vertical or horizontal
     * 
     * @param orientation chosen orientation
     */
    public void rotate(orientation o) {
        if (o == orientation.VERTICAL) {
            if (this.sHeight < this.sWidth)
                rotate();
        } else if (this.sWidth < this.sHeight)
            rotate();
    }

    /**
     * @return height of a shape
     **/
    public int getHeight() {
        return sHeight;
    }

    /**
     * @return width of a shape
     */
    public int getWidth() {
        return sWidth;
    }

    /**
     * Override toString method for printing a shape
     */
    @Override
    public String toString() {
        return String.format("[ %d x %d ]", sWidth, sHeight);
    }
}