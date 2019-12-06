package com.example.glasscutting.test;

import java.util.ArrayList;
import java.util.List;

import com.example.glasscutting.Algorithms;
import com.example.glasscutting.Render;
import com.example.glasscutting.Shape;
import com.example.glasscutting.Sheet;

/**
 * RenderTest
 */
public class RenderTest {
    public static void main(String[] args) {
        init();
    }

    static public void init() {

        List<Shape> dataSet1 = new ArrayList<>();
        dataSet1.add(new Shape(50, 50));
        dataSet1.add(new Shape(49, 49));
        dataSet1.add(new Shape(48, 48));
        dataSet1.add(new Shape(100, 100));
        dataSet1.add(new Shape(75, 200));
        dataSet1.add(new Shape(100, 100));
        dataSet1.add(new Shape(100, 50));
        dataSet1.add(new Shape(200, 250));
        dataSet1.add(new Shape(100, 100));
        dataSet1.add(new Shape(1, 1));

        List<Shape> dataSet2 = new ArrayList<>();
        dataSet2.add(new Shape(10, 10));
        dataSet2.add(new Shape(125, 50));
        dataSet2.add(new Shape(100, 80));
        dataSet2.add(new Shape(10, 50));
        dataSet2.add(new Shape(10, 200));
        dataSet2.add(new Shape(25, 30));
        dataSet2.add(new Shape(123, 80));
        dataSet2.add(new Shape(5, 43));
        dataSet2.add(new Shape(40, 32));
        dataSet2.add(new Shape(40, 1));

        List<Shape> dataSet3 = new ArrayList<>();
        dataSet3.add(new Shape(50, 50));
        dataSet3.add(new Shape(50, 50));
        dataSet3.add(new Shape(50, 50));
        dataSet3.add(new Shape(50, 50));
        dataSet3.add(new Shape(50, 50));
        dataSet3.add(new Shape(50, 50));
        dataSet3.add(new Shape(50, 50));
        dataSet3.add(new Shape(50, 50));
        dataSet3.add(new Shape(50, 50));
        dataSet3.add(new Shape(50, 50));
        dataSet3.add(new Shape(50, 50));
        dataSet3.add(new Shape(50, 50));
        dataSet3.add(new Shape(50, 50));
        dataSet3.add(new Shape(50, 50));
        dataSet3.add(new Shape(50, 50));
        dataSet3.add(new Shape(50, 50));
        dataSet3.add(new Shape(50, 50));
        dataSet3.add(new Shape(50, 50));
        dataSet3.add(new Shape(50, 50));
        dataSet3.add(new Shape(50, 50));
        dataSet3.add(new Shape(50, 50));
        dataSet3.add(new Shape(250, 300));

        Algorithms alg = new Algorithms();

        List<Sheet> d1 = alg.firstFit(dataSet3);
        for (Sheet s : d1)
            System.out.println(s);
        new Render(d1);
    }

}