package com.gpg.puzzles;

import java.util.ArrayList;
import static com.gpg.util.Logging.fail;
import static java.lang.StrictMath.abs;

/**
     Advent Of Code Puzzle 3A:
     http://adventofcode.com/2017/day/3

     --- Day 3: Spiral Memory ---

     You come across an experimental new kind of memory stored on an infinite two-dimensional grid.

     Each square on the grid is allocated in a spiral pattern starting at a location marked 1 and then counting up while spiraling outward. For example, the first few squares are allocated like this:

     17  16  15  14  13
     18   5   4   3  12
     19   6   1   2  11
     20   7   8   9  10
     21  22  23---> ...
     While this is very space-efficient (no squares are skipped),
    requested data must be carried back to square 1 (the location of the only access
    port for this memory system) by programs that can only move up, down, left, or right.
    They always take the shortest path: the Manhattan Distance between the location of the data and square 1.

     For example:

     Data from square 1 is carried 0 steps, since it's at the access port.
     Data from square 12 is carried 3 steps, such as: down, left, left.
     Data from square 23 is carried only 2 steps: up twice.
     Data from square 1024 must be carried 31 steps.
     How many steps are required to carry the data from the square
    identified in your puzzle input all the way to the access port?

    Correct answer is 430 using puzzle3.dat
 */
public class Puzzle3A extends Puzzle3Base {

    protected int SolveMe(ArrayList<String> inputData) {
        int total = 0;

        try {
            int target = Integer.parseInt(inputData.get(0));
            int cell = 1;
            int x = 0;
            int y = 0;
            int dir = -1;
            int count = 1;

            // move x count times or until cell = target; increment cell each move, calc move, then test
            // move y count time or until cell = target; increment cell each move, calc move, then test
            // increment count, change direction
            while (cell != target) {

                System.out.print(String.format("\n\tMoving x, d -> %s : ", dir > 0 ? "+" : "-"));
                for (int dx = 0; dx < count && cell != target; dx++) {
                    cell++;
                    x += dir;
                    System.out.print(String.format(" %d ", cell));
                }

                System.out.print(String.format("\n\tMoving y, d -> %s : ", dir > 0 ? "+" : "-"));
                for (int dy = 0; dy < count && cell != target; dy++) {
                    cell++;
                    y += dir;
                    System.out.print(String.format(" %d ", cell));
                }

                dir *= -1;
                count++;
            }

            total = abs(x) + abs(y);
        }
        catch (Exception e) {
            fail("An exception was thrown while puzzle solving!", e);
        }

        System.out.println("\n\tTarget cell found!");
        return total;
    }


}
