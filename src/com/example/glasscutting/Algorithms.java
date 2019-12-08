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
     * Takes a shape, and an x and y boundary and returns whether or not it will fit
     * within the space given
     * 
     * @param shape the shape to fit
     * @param xFree x space available
     * @param yFree y space available
     * @return 0 if will fit currently, 1 if will fit when rotated, 2 if it wont fit
     *         regardless of orientation
     */
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
        int yFreeSheet = 250;
        int noShapes = 0;

        boolean newSheet = true; // if this is true, a new sheet will be created
        boolean newShelf = true; // if this is true, a new shelf will be created for an existing sheet

        Sheet sheet = new Sheet();
        Shelf shelf = new Shelf();

        for (Shape shape : shapes) { // iterate through every shape
            int result = 3;
            while (result > 1) { // keep trying until it has been successfully inserted
                if (newSheet || noShapes == 20) { // if a new sheet is required, OR the shape limit has been reached
                    sheet = new Sheet();
                    shelf = new Shelf();
                    shelf.place(shape); // you know it will 100% fit, as its a new sheet
                    sheet.addShelf(shelf);
                    yFreeSheet = sheet.getHeight() - shape.getHeight(); // remove the y space of the shelf from the
                                                                        // number free for that sheet
                    usedSheets.add(sheet);
                    newSheet = false;
                    newShelf = false;
                    noShapes = 1;
                    result = 0;
                } else {
                    if (newShelf) { // if you need a new shelf, add it here
                        result = willFit(shape, sheet.getWidth(), yFreeSheet); // try willfit() for the shape
                        if (result <= 1) { // if it WILL fit
                            if (result == 1) // if it needs rotating
                                shape.rotate(); // rotate it
                            shelf = new Shelf(); // new shelf
                            shelf.place(shape); // place it
                            sheet.addShelf(shelf); //
                            yFreeSheet -= shape.getHeight(); // remove the y height (this is an optimisation)
                            newShelf = false; // new shelf no longer required
                            noShapes++;
                            break;
                        }
                        newSheet = true; // otherwise a new sheet is required
                    }
                    result = willFit(shape, sheet.getWidth() - shelf.getWidth(), shelf.getHeight()); // if you dont need
                                                                                                     // a new sheet or
                                                                                                     // shelf
                    if (result <= 1) { // same as before
                        if (result == 1)
                            shape.rotate();
                        shelf.place(shape);
                        newShelf = false;
                        noShapes++;
                        break;
                    } else {
                        newShelf = true; // if this wont fit on the shelf, get a new shelf
                    }
                }
            }
        }

        return usedSheets; // return used sheets

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

        List<Sheet> usedSheets = new ArrayList<Sheet>(); // list to hold sheets
        List<Integer> yFreeSheet = new ArrayList<Integer>(); // list to holds the y space free for each sheet, this is
                                                             // an optimsation instead of always looping through the
                                                             // sheets contents to find y space free.
        List<Integer> noShapes = new ArrayList<Integer>(); // this is another optimsation to hold the number of shapes
                                                           // on each sheet

        Sheet sheet;
        Shelf shelf;

        for (Shape shape : shapes) { // for each shape
            Integer result = 3;
            while (result > 1) { // keep going until it is placed
                for (int sheetNo = 0; sheetNo <= usedSheets.size(); sheetNo++) { // loop through the sheets, also loop
                                                                                 // one out of bounds
                    if (sheetNo == usedSheets.size()) { // if a new sheet is required
                        sheet = new Sheet();
                        shelf = new Shelf();
                        shelf.place(shape);
                        sheet.addShelf(shelf);
                        yFreeSheet.add(sheet.getHeight() - shape.getHeight()); // same as next fit but we are using a
                                                                               // list for efficiency
                        noShapes.add(1); // same as above
                        usedSheets.add(sheet);
                        result = 0; // ensure the loop is broken and the next shape is called
                    } else {
                        sheet = usedSheets.get(sheetNo); // get the sheet
                        for (int shelfNo = 0; shelfNo <= sheet.getShelves().size(); shelfNo++) { // for every shelf in
                                                                                                 // the sheet
                            if (shelfNo == sheet.getShelves().size()) { // if last shelf on a sheet
                                result = willFit(shape, sheet.getWidth(), yFreeSheet.get(sheetNo));
                                if (noShapes.get(sheetNo) == 20) { // if this is the max, we need a new sheet
                                    result = 2;
                                    break;
                                }
                                if (result <= 1) { // if it WILL fit
                                    if (result == 1) // if it WILL fit if its rotated
                                        shape.rotate(); // rotate it
                                    shelf = new Shelf();
                                    shelf.place(shape);
                                    sheet.addShelf(shelf);
                                    yFreeSheet.set(sheetNo, yFreeSheet.get(sheetNo) - shape.getHeight());
                                    noShapes.set(sheetNo, noShapes.get(sheetNo) + 1);
                                    break;
                                }
                            } else {
                                shelf = sheet.getShelves().get(shelfNo); // if the shelf aready exists
                                result = willFit(shape, sheet.getWidth() - shelf.getWidth(), shelf.getHeight()); // try
                                                                                                                 // fit
                                if (noShapes.get(sheetNo) == 20) { // if max, new sheet required
                                    result = 2;
                                    break;
                                }
                                if (result <= 1) { // if it WILL fit
                                    if (result == 1) // if it WILL fit if rotated
                                        shape.rotate(); // rotate it
                                    shelf.place(shape); // place the shape
                                    noShapes.set(sheetNo, noShapes.get(sheetNo) + 1); // add one to the list for shapes
                                    break;
                                }
                            }
                        }
                    }
                    if (result <= 1) // break out of this loop
                        break;
                }
            }
        }
        return usedSheets;
    }
}
