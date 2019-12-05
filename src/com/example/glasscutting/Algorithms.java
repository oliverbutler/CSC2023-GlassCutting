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

        boolean newSheet = true;
        boolean newShelf = true;

        Sheet sheet = new Sheet();
        Shelf shelf = new Shelf();

        for (Shape shape : shapes) {
            int result = 3;
            while (result > 1) {
                if (newSheet || noShapes == 20) {
                    sheet = new Sheet();
                    shelf = new Shelf();
                    shelf.place(shape);
                    sheet.addShelf(shelf);
                    yFreeSheet = sheet.getHeight() - shape.getHeight();
                    usedSheets.add(sheet);
                    newSheet = false;
                    newShelf = false;
                    noShapes = 1;
                    result = 0;
                } else {
                    if (newShelf) {
                        result = willFit(shape, sheet.getWidth(), yFreeSheet);
                        if (result <= 1) {
                            if (result == 1)
                                shape.rotate();
                            shelf = new Shelf();
                            shelf.place(shape);
                            sheet.addShelf(shelf);
                            yFreeSheet -= shape.getHeight();
                            newShelf = false;
                            noShapes++;
                            break;
                        }
                        newSheet = true;
                    }
                    result = willFit(shape, sheet.getWidth() - shelf.getWidth(), shelf.getHeight());
                    if (result <= 1) {
                        if (result == 1)
                            shape.rotate();
                        shelf.place(shape);
                        newShelf = false;
                        noShapes++;
                        break;
                    } else {
                        newShelf = true;
                    }
                }
            }
        }

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

        List<Sheet> usedSheets = new ArrayList<Sheet>();
        List<Integer> yFreeSheet = new ArrayList<Integer>();
        List<Integer> noShapes = new ArrayList<Integer>();

        Sheet sheet;
        Shelf shelf;

        for (Shape shape : shapes) {
            Integer result = 3;
            while (result > 1) {
                for (int sheetNo = 0; sheetNo <= usedSheets.size(); sheetNo++) {
                    if (sheetNo == usedSheets.size()) { // if a new sheet is required
                        sheet = new Sheet();
                        shelf = new Shelf();
                        shelf.place(shape);
                        sheet.addShelf(shelf);
                        yFreeSheet.add(sheet.getHeight() - shape.getHeight());
                        noShapes.add(1);
                        usedSheets.add(sheet);
                        result = 0;
                    } else {
                        sheet = usedSheets.get(sheetNo);
                        for (int shelfNo = 0; shelfNo <= sheet.getShelves().size(); shelfNo++) {
                            if (shelfNo == sheet.getShelves().size()) { // if last shelf on a sheet
                                result = willFit(shape, sheet.getWidth(), yFreeSheet.get(sheetNo));
                                if (noShapes.get(sheetNo) == 20) {
                                    result = 2;
                                    break;
                                }
                                if (result <= 1) {
                                    if (result == 1)
                                        shape.rotate();
                                    shelf = new Shelf();
                                    shelf.place(shape);
                                    sheet.addShelf(shelf);
                                    yFreeSheet.set(sheetNo, yFreeSheet.get(sheetNo) - shape.getHeight());
                                    noShapes.set(sheetNo, noShapes.get(sheetNo) + 1);
                                    break;
                                }
                            } else {
                                shelf = sheet.getShelves().get(shelfNo);
                                result = willFit(shape, sheet.getWidth() - shelf.getWidth(), shelf.getHeight());
                                if (noShapes.get(sheetNo) == 20) {
                                    result = 2;
                                    break;
                                }
                                if (result <= 1) {
                                    if (result == 1)
                                        shape.rotate();
                                    shelf.place(shape);
                                    noShapes.set(sheetNo, noShapes.get(sheetNo) + 1);
                                    break;
                                }
                            }
                        }
                    }
                    if (result <= 1)
                        break;
                }

            }
        }

        return usedSheets;
    }

}
