package com.gpg.puzzles;

import static com.gpg.util.Logging.fail;

import java.util.ArrayList;
import java.util.List;

/**
 * A base class for each puzzle solver.
 */
public abstract class PuzzleBase {

    private String _puzzleName = "";

    public PuzzleBase() {
        _puzzleName = "no name";
    }

    public PuzzleBase(String name) {
        _puzzleName = name;
    }

    public void SolvePuzzle(ArrayList<String> inputData) {
        System.out.println(String.format("\nSolving Puzzle %s ...", this.getClass().getSimpleName()));

        if (!DataIsValid(inputData))
            return;

        int total = SolveMe(inputData);

        System.out.println(String.format("The total is %d", total));
        System.out.println("Complete");
    }

    protected abstract boolean DataIsValid(ArrayList<String> inputData);
    protected abstract int SolveMe(ArrayList<String> inputData);

    protected boolean ValidateSingleLineDataFile(List<String> inputData) {
        if (inputData.size() != 1) {
            System.out.println("Incorrect input data - The input data should be a single line of text.");
            return false;
        }
        return true;
    }

    protected boolean ValidateMultiLineDataFile(List<String> inputData) {
        if (inputData.size() == 0) {
            System.out.println("Incorrect input data - The input data should be one or more lines of text.");
            return false;
        }
        return true;
    }
}
