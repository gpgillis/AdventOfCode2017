package com.gpg.puzzles;

import static com.gpg.util.Logging.fail;

import java.util.ArrayList;
import java.util.List;

/**
     Advent Of Code Puzzle :
     http://adventofcode.com/2017

     Base class for Puzzle 5
 */
public abstract class Puzzle5Base extends PuzzleBase {

    protected boolean DataIsValid(ArrayList<String> inputData) {
        return ValidateMultiLineDataFile(inputData);
    }

    /** Loads a 1 dimensional list with instruction data in inputData
     * Input data is a list of integers with one instruction per line
     * in the file.
     * @param inputData
     * @return single dimension list of integers.
     */
    protected List<Integer> loadInstructions(List<String> inputData) {
        List<Integer> instructions = new ArrayList<Integer>();
        try {
            for (String s : inputData) {
                int instruction = Integer.parseInt(s.trim());
                instructions.add(instruction);
            }
        }
        catch (NumberFormatException e) {
            fail("Each instruction must be an integer.", e);
        }

        return instructions;
    }
}
