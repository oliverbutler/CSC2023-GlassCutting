package com.example.glasscutting.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import com.example.glasscutting.Algorithms;
import com.example.glasscutting.Shape;
import com.example.glasscutting.Sheet;
import com.example.glasscutting.Shelf;

import org.junit.*;

public class CorrectnessTest {

    Algorithms alg = new Algorithms();

    List<Shape> shapes;

    @Before
    public void init() {
        shapes = new ArrayList<>();
    }

    @Test
    public void nextFitBasicTest() {
        shapes.add(new Shape(50, 50));
        shapes.add(new Shape(40, 50));
        shapes.add(new Shape(50, 100));
        List<Sheet> sorted = alg.nextFit(shapes);
        assertTrue(sorted.size() == 1);
        assertTrue(sorted.get(0).getShelves().size() == 1);
        assertTrue(sorted.get(0).getShelves().get(0).getShapes().size() == 3);
        System.out.println(sorted.get(0).getShelves().get(0));
        assertTrue(
                sorted.get(0).getShelves().get(0).toString().equals("{ 190 x 50} [ 50 x 50 ][ 40 x 50 ][ 100 x 50 ]"));
    }

    @Test
    public void nextFitNeedNewShelf() {
        shapes.add(new Shape(100, 100));
        shapes.add(new Shape(99, 99));
        shapes.add(new Shape(98, 98));
        shapes.add(new Shape(97, 97));
        List<Sheet> sorted = alg.nextFit(shapes);
        assertTrue(sorted.get(0).getShelves().size() == 2);
        assertTrue(sorted.get(0).getShelves().get(0).toString()
                .equals("{ 297 x 100} [ 100 x 100 ][ 99 x 99 ][ 98 x 98 ]"));
        assertTrue(sorted.get(0).getShelves().get(1).toString().equals("{ 97 x 97} [ 97 x 97 ]"));
    }

    @Test
    public void nextFitCheckLimit() {
        for (int i = 0; i < 100; i++) {
            shapes.add(new Shape(5, 5));
        }
        List<Sheet> sorted = alg.nextFit(shapes);

        for (Sheet s : sorted)
            System.out.println(s);

        int shapesPerSheet = 0;
        for (Shelf shelf : sorted.get(0).getShelves())
            shapesPerSheet += shelf.getShapes().size();
        assertTrue(shapesPerSheet <= 20);
    }

    @Test
    public void nextFitCorrectExample1() {
        shapes.add(new Shape(150, 100));
        shapes.add(new Shape(50, 50));
        shapes.add(new Shape(75, 75));
        shapes.add(new Shape(75, 200));
        shapes.add(new Shape(100, 100));
        shapes.add(new Shape(100, 50));
        shapes.add(new Shape(200, 250));

        List<Sheet> sorted = alg.nextFit(shapes);
        for (Sheet s : sorted)
            System.out.println(s);
    }
}
