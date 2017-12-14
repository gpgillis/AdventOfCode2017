package com.gpg.puzzles;

import java.util.ArrayList;

/**
    Advent Of Code Puzzle :
    http://adventofcode.com/2017

    Base class for Puzzle 3
 */
public abstract class Puzzle3Base extends PuzzleBase {
    protected boolean DataIsValid(ArrayList<String> inputData) {
        Boolean v = ValidateSingleLineDataFile(inputData);
        if (v) {
            try {
                Integer.parseInt(inputData.get(0));
            }
            catch (NumberFormatException e) {
                System.out.println("The input data should be a single integer.");
                v = false;
            }
        }
        return v;
    }
}
