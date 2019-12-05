package com.example.glasscutting;

/**
 * @Purpose: The Sheet class represents a sheet with a list of shelves.
 * DO NOT MODIFY THE EXISTING METHODS SIGNITURE, You may add additional methods if you wish
 * 
 * @author  RYK 
 * @since   30/10/2019
 * extended by @author  Oliver Butler
 */
import java.util.ArrayList;
import java.util.List;

public class Sheet {

    public static final int SHEET_HEIGHT = 250; // sheet height

    public static final int SHEET_WIDTH = 300; // sheet width

    public static final int SHAPE_LIMIT = 20; // maximum number of shapes that can be placed in each sheet (rule F)

    private List<Shelf> shelves = new ArrayList<Shelf>(); // list of shelves

    /**
     * empty constructor
     */
    public Sheet() {
    }

    /**
     * This method is used to add a shelf to a sheet
     * 
     * @param a shelf
     */
    public void addShelf(Shelf shelf) {
        shelves.add(shelf);
    }

    /**
     * @return height of all shelves in a sheet
     */
    public int allShelvesHeight() {

        int total = 0;

        for (Shelf shelf : this.shelves) {

            // has a shelf with at least 1 shape
            if (!shelf.getShapes().isEmpty()) {

                // add all shelf height to total
                total += shelf.getHeight();
            }
        }
        return total;
    }

    /**
     * @return list of all shelves in a sheet
     */
    public List<Shelf> getShelves() {
        return this.shelves;
    }

    /**
     * @return height
     */
    public int getHeight() {
        return SHEET_HEIGHT;
    }

    /**
     * @return width
     */
    public int getWidth() {
        return SHEET_WIDTH;
    }

    /**
     * Returns the used area of a sheet
     * 
     * @return the area used of a sheet
     */
    public int getUsedArea() {
        Integer area = 0;
        for (Shelf shelf : getShelves()) {
            for (Shape shape : shelf.getShapes()) {
                area += shape.getHeight() * shape.getWidth();
            }
        }
        return area;
    }

    /**
     * Returns the total area of sheet
     * 
     * @return the area of a sheet
     */
    public int getArea() {
        return SHEET_HEIGHT * SHEET_WIDTH;
    }

    /**
     * Overrides toString to print out a sheet and its content in a detailed mannor
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        int i = 1;
        int count = 0;
        for (Shelf shelf : shelves)
            count += shelf.getShapes().size();
        str.append(String.format("[%d/20] [%.1f%%]\n", count, ((double) getUsedArea() / (double) getArea()) * 100));
        str.append("===============\n");
        for (Shelf s : shelves) {
            str.append(String.format("Shelf %d: %s\n", i, s));
            i++;
        }
        return str.toString();
    }

}
