package com.gpg.puzzles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
    Advent Of Code Puzzle 2A:
    http://adventofcode.com/2017/day/2

    The spreadsheet consists of rows of apparently-random numbers. To make sure the recovery process is on the right track,
    they need you to calculate the spreadsheet's checksum.

    For each row, determine the difference between the largest value and the smallest value;
    the checksum is the sum of all of these differences.

    For example, given the following spreadsheet:

    5 1 9 5
    7 5 3
    2 4 6 8

    The first row's largest and smallest values are 9 and 1, and their difference is 8.
    The second row's largest and smallest values are 7 and 3, and their difference is 4.
    The third row's difference is 6.

    In this example, the spreadsheet's checksum would be 8 + 4 + 6 = 18.

    The correct answer is 42378 with puzzle2.dat.
*/

public class Puzzle2A extends PuzzleBase {

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
            int max = Collections.max(cells);
            int min = Collections.min(cells);

            System.out.println(String.format("\tMax: %d\n\tMin: %d", max, min));

            total += max - min;
        }

        return total;
    }
}
