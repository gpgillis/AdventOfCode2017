package com.gpg.puzzles;
import java.util.ArrayList;

/**
    Advent Of Code Puzzle 1A:
    http://adventofcode.com/2017/day/1

    The captcha requires you to review a sequence of digits (your puzzle input) and
    find the sum of all digits that match the next digit in the list.
    The list is circular, so the digit after the last digit is the first digit in the list.

    For example:

    1122 produces a sum of 3 (1 + 2) because the first digit (1) matches the second digit and the third digit (2) matches the fourth digit.
    1111 produces 4 because each digit (all 1) matches the next.
    1234 produces 0 because no digit matches the next.
    91212129 produces 9 because the only digit that matches the next one is the last digit, 9.

    The correct answer is 1144 using puzzle1.dat.
*/

public class Puzzle1A extends PuzzleBase
{
    protected boolean DataIsValid(ArrayList<String> inputData) {
        return ValidateSingleLineDataFile(inputData);
    }

    protected int SolveMe(ArrayList<String> inputData) {
        int total = 0;

        String source = inputData.get(0);
        if (source == null || source.isEmpty()) {
            System.out.println("The supplied source string is null or empty and cannot be processed!");
        }
        else {
            for (int i = 0; i < source.length(); i++) {
                int nextChar = i < source.length() - 1 ? i + 1 : 0;
                if (source.charAt(i) == source.charAt(nextChar)) {
                    total += Character.getNumericValue(source.charAt(i));
                }
                String format = "For idx = %d, nextIdx = %d\n\tThe character is %s\n\tThe next character is %s\n\tThe running total is %d";
                System.out.println(String.format(format, i, nextChar, source.charAt(i), source.charAt(nextChar), total));
            }
        }

        return total;
    }
}

