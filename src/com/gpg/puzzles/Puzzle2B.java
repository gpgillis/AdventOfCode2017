package com.gpg.puzzles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
     Advent Of Code Puzzle 2B:
     http://adventofcode.com/2017/day/2

     It sounds like the goal is to find the only two numbers in each row
     where one evenly divides the other - that is, where the result of the
     division operation is a whole number.

     They would like you to find those numbers on each line, divide them,
     and add up each line's result.

     For example, given the following spreadsheet:

     5 9 2 8
     9 4 7 3
     3 8 6 5
     In the first row, the only two numbers that evenly divide are 8 and 2; the result of this division is 4.
     In the second row, the two numbers are 9 and 3; the result is 3.
     In the third row, the result is 2.
     In this example, the sum of the results would be 4 + 3 + 2 = 9.

    The correct answer is 246 with puzzle2.dat
 */
public class Puzzle2B extends PuzzleBase {

    protected boolean DataIsValid(ArrayList<String> inputData) {
        return ValidateMultiLineDataFile(inputData);
    }

    protected int SolveMe(ArrayList<String> inputData) {
        int total = 0;

        for (String line: inputData) {
            System.out.println(String.format("Data: %s", line));

            List<Integer> cells = new ArrayList<Integer>();
            for (String cell: line.split("\\s+")) {
                cells.add(Integer.valueOf(cell));
            }
            Collections.sort(cells, Collections.<Integer>reverseOrder());
            for (int i = 0; i < cells.size(); i++)
            {
                for (int j = i + 1; j < cells.size(); j++) {
                    System.out.print(String.format("\tTesting %d : %d", cells.get(i), cells.get(j)));
                    if (cells.get(i) % cells.get(j) == 0) {
                        System.out.println(" - evenly divisible!");
                        total += cells.get(i) / cells.get(j);
                    }
                    else {
                        System.out.print("\n");
                    }
                }
            }
        }

        return total;
    }
}
