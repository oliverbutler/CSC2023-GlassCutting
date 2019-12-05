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

        List<Sheet> usedSheets = new ArrayList<Sheet>();
        List<Integer> yFreeSheet = new ArrayList<Integer>();

        Sheet sheet;
        Shelf shelf;

        for (Shape shape : shapes) {
            Integer result = 3;
            while (result > 1) {
                for (int sheetNo = 0; sheetNo <= usedSheets.size(); sheetNo++) {
                    if (sheetNo == usedSheets.size()) { // if a new sheet is required, add it
                        sheet = new Sheet();
                        shelf = new Shelf();
                        shelf.place(shape);
                        sheet.addShelf(shelf);
                        yFreeSheet.add(sheet.getHeight() - shape.getHeight());
                        usedSheets.add(sheet);
                        result = 0;
                    } else {
                        sheet = usedSheets.get(sheetNo);
                        for (int shelfNo = 0; shelfNo <= sheet.getShelves().size(); shelfNo++) {
                            if (shelfNo == sheet.getShelves().size()) { // if last shelf on a sheet
                                result = willFit(shape, sheet.getWidth(), yFreeSheet.get(sheetNo));
                                if (result <= 1) {
                                    if (result == 1)
                                        shape.rotate();
                                    shelf = new Shelf();
                                    shelf.place(shape);
                                    sheet.addShelf(shelf);
                                    yFreeSheet.set(sheetNo, yFreeSheet.get(sheetNo) - shape.getHeight());
                                    break;
                                }
                            } else {
                                shelf = sheet.getShelves().get(shelfNo);
                                result = willFit(shape, sheet.getWidth() - shelf.getWidth(), shelf.getHeight());
                                if (result <= 1) {
                                    if (result == 1)
                                        shape.rotate();
                                    shelf.place(shape);
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
