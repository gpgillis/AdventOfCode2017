package com.gpg;

import java.util.ArrayList;

import com.gpg.puzzles.PuzzleBase;

/**
 * Created by ggillis on 12/8/2017.
 * Holding class for A and B puzzle solvers.
 */
public class Solver {
    private PuzzleBase _puzzleASolver;
    private PuzzleBase _puzzleBSolver;

    public Solver(PuzzleBase aSolver, PuzzleBase bSolver) {
        _puzzleASolver = aSolver;
        _puzzleBSolver = bSolver;
    }

    public void Solve(ArrayList<String> inputData) {
        if (_puzzleASolver != null) {
            _puzzleASolver.SolvePuzzle(inputData);
        }
        if (_puzzleBSolver != null) {
            _puzzleBSolver.SolvePuzzle(inputData);
        }
    }
}
