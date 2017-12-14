package com.gpg.puzzles;
import java.io.*;
import java.util.ArrayList;

/**
    Advent Of Code Puzzle 1B:
    http://adventofcode.com/2017/day/1

    Now, instead of considering the next digit, it wants you to consider the digit halfway
    around the circular list. That is, if your list contains 10 items,
    only include a digit in your sum if the digit 10/2 = 5 steps forward matches it.
    Fortunately, your list has an even number of elements.

    For example:

    1212 produces 6: the list contains 4 items, and all four digits match the digit 2 items ahead.
    1221 produces 0, because every comparison is between a 1 and a 2.
    123425 produces 4, because both 2s match each other, but no other digit has a match.
    123123 produces 12.
    12131415 produces 4.

    The correct answer is 1194 using puzzle1.dat
*/

public class Puzzle1B extends PuzzleBase
{
    public boolean DataIsValid(ArrayList<String> inputData)
    {
        return ValidateSingleLineDataFile(inputData);
    }

    protected int SolveMe(ArrayList<String> inputData) {
        int total = 0;

        String source = inputData.get(0);

        if (source == null || source.isEmpty()) {
            System.out.println("The supplied source string is null or empty and cannot be processed!");
        }
        else {
            int midPoint = source.length() / 2;
            System.out.println(String.format("The calculated midpoint is %d", midPoint));

            for (int i = 0; i < source.length(); i++) {
                int nextIdx = i + midPoint;
                if (nextIdx > source.length() - 1) {
                    nextIdx = nextIdx - source.length();
                }

                if (source.charAt(i) == source.charAt(nextIdx)) {
                    total += Character.getNumericValue(source.charAt(i));
                }
            }
        }

        return total;
    }
}

