package com.example.glasscutting;

/**
 * @Purpose: The Algorithms class contains the two algorithms you have to implement  
 * Do NOT modify the names and signatures of the two algorithm methods
 * 
 * @author  RYK
 * @since   30/10/2019
 * extended by @author Oliver Butler 
 *
 **/

import java.util.ArrayList;
import java.util.List;

public class Algorithms {
    /**
     * Takes a shelf and a shape and attempts to place the shape within it
     * optimally, tends towards tall shelves as those shelves can best fit more
     * items
     * 
     * @param shelf      The shelf to put the shape onto
     * @param shape      The shape to put into the shelf
     * @param yFreeSheet Space free to the top of the sheet
     * @param xFreeShelf Space free left on the shelf
     * @return True if placed correctly, false if not placed
     */
    private Integer optimalPlace(Shelf shelf, Shape shape, Integer xFreeShelf, Integer yFreeSheet) {
        Integer yFreeShelf = yFreeSheet;
        if (shelf.getHeight() != 0)
            yFreeShelf = shelf.getHeight();
        if (shape.getHeight() <= yFreeShelf && shape.getWidth() <= xFreeShelf) {
            shelf.place(shape);
            return 0;
        }
        if (shape.getWidth() <= yFreeShelf && shape.getHeight() <= xFreeShelf) {
            shape.rotate();
            shelf.place(shape);
            return 0;
        }
        if (shelf.getHeight() == 0)
            return 2;
        return 1; // Need a new shelf
    }

    private Integer willFit(Shape shape, Integer xFree, Integer yFree) {
        if (shape.getHeight() <= yFree && shape.getWidth() <= xFree)
            return 0;
        if (shape.getWidth() <= yFree && shape.getHeight() <= xFree)
            return 1;
        return 2;
    }

    /**
     * This method is used to implement the next fit algorithm
     * 
     * @param shapes:a list of shapes representing customer requests(shapes to be
     *                 cut/placed)
     * @return a list of the sheets used to fulfil all customer requests (i.e. place
     *         all the shapes). e.g. if you pass a "shapes" list that has two shapes
     *         {(w=200,h=200),(w=50,h=100)}, then the returned list of sheets should
     *         show us all the information needed (e.g. that one sheet is used, this
     *         sheet has one shelf and this shelf has two shapes in it). In the test
     *         program, you can use the returned list to retrieve the total number
     *         of sheets used.
     **/

    public List<Sheet> nextFit(List<Shape> shapes) {
        List<Sheet> usedSheets = new ArrayList<Sheet>();

        Sheet sheet = new Sheet();
        Shelf shelf = new Shelf();
        int yFreeSheet = sheet.getHeight();
        int noShapes = 0;

        for (Shape shape : shapes) {
            int result = 21;
            if (noShapes == 20) {
                sheet.addShelf(shelf);
                usedSheets.add(sheet);
                sheet = new Sheet();
                shelf = new Shelf();
                yFreeSheet = sheet.getHeight();
                noShapes = 0;
            }
            while (result > 1) {
                boolean firstOnShelf = false;
                if (shelf.getHeight() == 0)
                    firstOnShelf = true;
                int yMax = yFreeSheet;
                if (!firstOnShelf)
                    yMax = shelf.getHeight();
                result = willFit(shape, sheet.getWidth() - shelf.getWidth(), yMax);
                switch (result) {
                case 0:
                    if (firstOnShelf)
                        yFreeSheet -= shape.getHeight();
                    shelf.place(shape);
                    noShapes++;
                    break;
                case 1:
                    shape.rotate();
                    if (firstOnShelf)
                        yFreeSheet -= shape.getHeight();
                    shelf.place(shape);
                    noShapes++;
                    break;
                case 2:
                    if (firstOnShelf) { // If wont fit, and new shelf, need a new sheet
                        usedSheets.add(sheet);
                        sheet = new Sheet();
                        yFreeSheet = sheet.getHeight();
                    } else if (yMax == yFreeSheet) { // no more shelfs will fit, so new sheet
                        sheet.addShelf(shelf);
                        usedSheets.add(sheet);
                        sheet = new Sheet();
                        shelf = new Shelf();
                        yFreeSheet = sheet.getHeight();
                    } else { // else get a new
                        sheet.addShelf(shelf);
                        shelf = new Shelf();
                    }
                    break;
                }
            }
        }

        sheet.addShelf(shelf);
        usedSheets.add(sheet);

        return usedSheets;

    }

    /**
     * This method is used to implement the first fit algorithm
     * 
     * @param shapes:a list of shapes representing customer requests (shapes to be
     *                 cut/placed)
     * @return a list of the sheets used to fulfil all customer requests (i.e. place
     *         all the shapes). e.g. if you pass a "shapes" list that has two shapes
     *         {(w=200,h=200),(w=50,h=100)}, then the returned list of sheets should
     *         show us all the information needed (e.g. that one sheet is used, this
     *         sheet has one shelf and this shelf has two shapes in it). In the test
     *         program, you can use the returned list to retrieve the total number
     *         of sheets used
     **/
    public List<Sheet> firstFit(List<Shape> shapes) {

        /*
         * Start with an empty list of sheets (remember each sheet has a width of 300
         * and a height of 250 as specified in the Sheet class)
         */
        List<Sheet> usedSheets = new ArrayList<Sheet>();

        Sheet sheet = new Sheet();
        Shelf shelf = new Shelf();
        sheet.addShelf(shelf);
        usedSheets.add(sheet);

        List<Integer> yFreeSheet = new ArrayList<Integer>();
        yFreeSheet.add(sheet.getHeight());

        for (Shape shape : shapes) {
            Integer result = 1;
            while (result != 0) {
                for (int j = 0; j < usedSheets.size(); j++) {
                    sheet = usedSheets.get(j);
                    for (int i = 0; i < sheet.getShelves().size(); i++) {
                        shelf = sheet.getShelves().get(i);
                        if (shelf.getShapes().size() == 20) {
                            if (j == usedSheets.size() - 1) {
                                sheet = new Sheet();
                                sheet.addShelf(new Shelf());
                                usedSheets.add(sheet);
                                yFreeSheet.add(sheet.getHeight());
                            }
                        } else {
                            result = optimalPlace(shelf, shape, sheet.getWidth() - shelf.getWidth(), yFreeSheet.get(j));

                            if (result == 0) {
                                if (shelf.getShapes().size() == 1)
                                    yFreeSheet.set(j, yFreeSheet.get(j) - shape.getHeight());
                                break;
                            }
                            if (result == 1 && i == sheet.getShelves().size() - 1) {
                                sheet.addShelf(new Shelf());
                            }
                            if ((result == 2) && j == usedSheets.size() - 1) {
                                sheet = new Sheet();
                                sheet.addShelf(new Shelf());
                                usedSheets.add(sheet);
                                yFreeSheet.add(sheet.getHeight());
                            }
                        }
                    }
                    if (result == 0)
                        break;
                }
            }
        }
        /*
         * Add in your own code so that the method will place all the shapes according
         * to FirstFit under the assumptions mentioned in the spec
         */

        return usedSheets;
    }

}
