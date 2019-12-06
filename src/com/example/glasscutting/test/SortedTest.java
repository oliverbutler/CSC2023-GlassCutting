package com.example.glasscutting.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.glasscutting.Algorithms;
import com.example.glasscutting.Generator;
import com.example.glasscutting.Shape;
import com.example.glasscutting.Sheet;

/**
 * @Purpose: The SortedTest class is used to compare the implemented algorithms
 *           in term of the number of sheets used WHEN the list of shapes is
 *           SORTED
 *
 *           You can add additional methods if you need to in this class
 * 
 * @author RYK
 * @since 30/10/2019 extended by @author
 */

public class SortedTest {
    public static void main(String[] args) {
        System.out.println("*********************************************");
        System.out.println("**************** Sorted Test ****************");
        System.out.println("*********************************************");
        System.out.println();

        final int noOfReps = 10;
        final int noOfShapes = 10000;

        Generator shapeGenerator = new Generator();
        Algorithms algorithms = new Algorithms();

        System.out.println(padString("Algorithm(Shapes)", 20) + padString("Sort By", 15) + padString("Time", 15)
                + padString("Sheets", 15) + padString("Percent Used", 15));
        System.out.println("=".repeat(80));

        long[] nextTimes = new long[3];
        Arrays.fill(nextTimes, 0);
        long[] firstTimes = new long[3];
        Arrays.fill(firstTimes, 0);

        for (int reverse = 0; reverse < 2; reverse++) {
            for (int algNumber = 0; algNumber < 2; algNumber++) {
                for (int sortNumber = 0; sortNumber < 3; sortNumber++) {
                    double timeTaken = 0;
                    int noSheets = 0;
                    double percentUsed = 0;

                    for (int i = 0; i < noOfReps; i++) {
                        List<Shape> unsorted = shapeGenerator.generateShapeList(noOfShapes);
                        List<Sheet> sorted;
                        switch (sortNumber) {
                        case 0:
                            Collections.sort(unsorted, new sortByWidth());
                            break;
                        case 1:
                            Collections.sort(unsorted, new sortByHeight());
                            break;
                        case 2:
                            Collections.sort(unsorted, new sortByArea());
                            break;
                        }
                        if (reverse == 1)
                            Collections.reverse(unsorted);
                        long startTime = System.nanoTime();
                        if (algNumber == 0)
                            sorted = algorithms.nextFit(unsorted);
                        else
                            sorted = algorithms.firstFit(unsorted);
                        long endTime = System.nanoTime();

                        int used = 0;
                        int total = 0;
                        for (Sheet sheet : sorted) {
                            used += sheet.getUsedArea();
                            total += sheet.getArea();
                        }

                        timeTaken += (endTime - startTime);
                        noSheets += sorted.size();
                        percentUsed += ((double) used / (double) total) * 100;
                    }

                    timeTaken /= noOfReps;
                    noSheets /= noOfReps;
                    percentUsed /= noOfReps;

                    String algName;
                    if (algNumber == 0)
                        algName = "nextFit";
                    else
                        algName = "firstFit";

                    String sortName;
                    if (sortNumber == 0)
                        sortName = "width";
                    else if (sortNumber == 1)
                        sortName = "height";
                    else
                        sortName = "area";
                    if (reverse == 1)
                        sortName += " (r)";

                    StringBuilder str = new StringBuilder();
                    str.append(padString(String.format("%s(%d)", algName, noOfShapes), 20));
                    str.append(padString(String.format("%s", sortName), 15));
                    str.append(padString(String.format("%.2f ms", timeTaken / 1e6), 15));
                    str.append(padString(String.format("%d", noSheets), 15));
                    str.append(padString(String.format("%.2f", percentUsed), 15));
                    System.out.println(str.toString());
                }
                System.out.println("=".repeat(80));
            }
        }
    }

    public static String padString(String str, int leng) {
        for (int i = str.length(); i <= leng; i++)
            str += " ";
        return str;
    }
}

class sortByWidth implements Comparator<Shape> {
    public int compare(Shape a, Shape b) {
        Integer var1 = a.getWidth();
        Integer var2 = b.getWidth();
        return var1.compareTo(var2);
    }
}

class sortByHeight implements Comparator<Shape> {
    public int compare(Shape a, Shape b) {
        Integer var1 = a.getHeight();
        Integer var2 = b.getHeight();
        return var1.compareTo(var2);
    }
}

class sortByArea implements Comparator<Shape> {
    public int compare(Shape a, Shape b) {
        Integer var1 = a.getWidth() * a.getHeight();
        Integer var2 = b.getWidth() * b.getHeight();
        return var1.compareTo(var2);
    }
}