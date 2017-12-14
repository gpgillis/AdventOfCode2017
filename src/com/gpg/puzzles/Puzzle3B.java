package com.gpg.puzzles;

import static com.gpg.util.Logging.fail;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 Advent Of Code Puzzle 3B:
 http://adventofcode.com/2017/day/3

 -- Part Two --

 As a stress test on the system, the programs here clear the grid and then store the value 1 in square 1.
 Then, in the same allocation order as shown above (3A), they store the sum of the values in all adjacent
 squares, including diagonals.

 So, the first few squares' values are chosen as follows:

 Square 1 starts with the value 1.
 Square 2 has only one adjacent filled square (with value 1), so it also stores 1.
 Square 3 has both of the above squares as neighbors and stores the sum of their values, 2.
 Square 4 has all three of the aforementioned squares as neighbors and stores the sum of their values, 4.
 Square 5 only has the first and fourth squares as neighbors, so it gets the value 5.
 Once a square is written, its value does not change. Therefore, the first few squares would receive the following values:

 147  142  133  122   59
 304    5    4    2   57
 330   10    1    1   54
 351   11   23   25   26
 362  747  806--->   ...
 What is the first value written that is larger than your puzzle input?

 The correct answer is 312453 with puzzle4.dat

 */
public class Puzzle3B extends Puzzle3Base {

    protected int SolveMe(ArrayList<String> inputData) {
        int cell = 1;

        try {
            int target = Integer.parseInt(inputData.get(0));
            int x = 0;
            int y = 0;
            int dir = 1;
            int count = 1;

            Map<Point2D.Double, Integer> map = new HashMap<Point2D.Double, Integer>();
            addMapValue(map, x, y, cell);   // grid center initial point.

            // move x count times or until cell > target; increment cell each move, calc cell value and store
            // move y count time or until cell > target; increment cell each move, calc cell value and store
            // increment count, change direction
            while (cell <= target) {
                System.out.print(String.format("\n\tMoving x, d -> %s : ", dir > 0 ? "+" : "-"));
                for (int dx = 0; dx < count && cell <= target; dx++) {
                    x += dir;
                    cell = calculateCell(map, x, y);
                }

                System.out.print(String.format("\n\tMoving y, d -> %s : ", dir > 0 ? "+" : "-"));
                for (int dy = 0; dy < count && cell <= target; dy++) {
                    y += dir;
                    cell = calculateCell(map, x, y);
                }

                dir *= -1;
                count++;
            }
        }
        catch (Exception e) {
            fail("An exception was thrown while puzzle solving!", e);
        }

        return cell;
    }

    private int calculateCell(Map<Point2D.Double, Integer> map, int x, int y) {
        int val = 0;
        for (int dx = -1; dx < 2; dx++) {
            for (int dy = -1; dy < 2; dy++) {
                val += getMapValue(map, x - dx, y - dy);
            }
        }
        addMapValue(map, x, y, val);
        return val;
    }

    private void addMapValue(Map<Point2D.Double, Integer> map, int x, int y, int val) {
        System.out.print(String.format("(%d, %d) = %d ", x, y, val));
        map.put(new Point2D.Double(x, y), val);
    }

    private int getMapValue(Map<Point2D.Double, Integer> map, int x, int y) {
        Point2D.Double key = new Point2D.Double(x, y);
        return map.containsKey(key) ? map.get(key) : 0;
    }
}
