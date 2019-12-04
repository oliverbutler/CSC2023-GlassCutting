package com.example.glasscutting.test;

import java.util.List;

import com.example.glasscutting.Algorithms;
import com.example.glasscutting.Generator;
import com.example.glasscutting.Shape;
import com.example.glasscutting.Sheet;

/**
 * @Purpose: The PerformanceTest class is used to compare the implemented
 *           algorithms in term of time and the number of sheets used
 *
 *           You can add additional methods if you need to in this class
 * 
 * @author RYK
 * @since 30/10/2019 extended by @author
 */

public class PerformanceTest {

    public static void main(String[] args) {

        System.out.println("***********************************************");
        System.out.println("*********** Performance analysis **************");
        System.out.println("**********************************************");

        System.out.println();

        final int noOfReps = 4;
        final int shapeLowerBound = 10000;
        final int shapeUpperBound = 50000;
        final int shapeStep = 10000;

        Generator shapeGenerator = new Generator();
        Algorithms algorithms = new Algorithms();

        System.out.println(padString("Algorithm(Shapes)", 20) + padString("Time", 15) + padString("Sheets", 15)
                + padString("Percent Used", 15));
        System.out.println("=".repeat(65));

        for (int noShapes = shapeLowerBound; noShapes <= shapeUpperBound; noShapes += shapeStep) {
            for (int algNumber = 0; algNumber < 2; algNumber++) {
                double timeTaken = 0;
                int noSheets = 0;
                double percentUsed = 0;

                for (int i = 0; i < noOfReps; i++) {
                    List<Shape> unsorted = shapeGenerator.generateShapeList(noShapes);
                    List<Sheet> sorted;
                    long timeStart = System.nanoTime();
                    if (algNumber == 0)
                        sorted = algorithms.nextFit(unsorted);
                    else
                        sorted = algorithms.firstFit(unsorted);
                    long timeEnd = System.nanoTime();

                    timeTaken += timeEnd - timeStart;

                    noSheets += sorted.size();

                    int used = 0;
                    int total = 0;
                    for (Sheet sheet : sorted) {
                        used += sheet.getUsedArea();
                        total += sheet.getArea();
                    }
                    percentUsed += ((double) used / (double) total) * 100;

                }
                timeTaken /= 4;
                noSheets /= 4;
                percentUsed /= 4;
                String algName;
                if (algNumber == 0)
                    algName = "nextFit";
                else
                    algName = "firstFit";
                StringBuilder str = new StringBuilder();
                str.append(padString(String.format("%s(%d)", algName, noShapes), 20));
                str.append(padString(String.format("%.2f ms", timeTaken / 1e6), 15));
                str.append(padString(String.format("%d", noSheets), 15));
                str.append(padString(String.format("%.2f", percentUsed), 15));
                System.out.println(str.toString());
            }
            System.out.println("=".repeat(65));
        }
    }

    public static String padString(String str, int leng) {
        for (int i = str.length(); i <= leng; i++)
            str += " ";
        return str;
    }
}
