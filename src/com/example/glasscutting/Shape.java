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

public class Shape implements Comparable<Shape> {

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

    @Override
    public String toString() {
        return String.format("[ %d x %d ]", sWidth, sHeight);
    }

    @Override
    public int compareTo(Shape o) {
        if (getHeight() == o.getHeight() && getWidth() == o.getWidth())
            return 0;
        if (getHeight() == o.getWidth() && getWidth() == o.getHeight())
            return 0;
        Integer area1 = getHeight() * getWidth();
        Integer area2 = o.getHeight() * o.getWidth();
        return area1.compareTo(area2);
    }
}
