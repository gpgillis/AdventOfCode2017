package com.gpg.puzzles;

import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.List;

/**
 Created by ggillis on 12/16/2017.

 Advent Of Code Puzzle :
 http://adventofcode.com/2017/day/11

 --- Day 11: Hex Ed ---
 Crossing the bridge, you've barely reached the other side of the stream when a program
 comes up to you, clearly in distress. "It's my child process," she says, "he's gotten lost in an infinite grid!"

 Fortunately for her, you have plenty of experience with infinite grids.

 Unfortunately for you, it's a hex grid.

 The hexagons ("hexes") in this grid are aligned such that adjacent hexes can be found to the
 north, northeast, southeast, south, southwest, and northwest:

   \ n  /
 nw +--+ ne
   /    \
 -+      +-
   \    /
 sw +--+ se
   / s  \

 You have the path the child process took. Starting where he started, you need to determine the fewest number
 of steps required to reach him. (A "step" means to move from the hex you are in to any adjacent hex.)

 For example:

 ne,ne,ne is 3 steps away.
 ne,ne,sw,sw is 0 steps away (back where you started).
 ne,ne,s,s is 2 steps away (se,se).
 se,sw,se,sw,sw is 3 steps away (s,s,sw).


 Proposed solution:
 Tracking 2 dimensions (R, L).
    Thus:
        NE = -R
        SE = +L
        NW = -l
        SW = -R

 A N/S movement is recorded as
    N = (-L) && (-R)
    S = (+L) && (+R)

 The path from the destination to the origin can then be calculated by combining
 movements to generate N/S movements (linear movement), with the remainder indicating
 angular movement.

 if the signs of the L and R movements are opposite, then the path count is:
    pc = abs(L) + abs(R)

 otherwise the path count is:
     u = combine(l,r)
     l/r = remainder
     pc = abs(u) + abs(l) + abs(r)

 see resources/puzzle11_testsets.txt for further examples.

 The correct answer is 670 with puzzle11.dat

 --- Part Two ---

 How many steps away is the furthest he ever got from his starting position?

 The correct answer is 1426 with puzzle11.dat

 */
public class Puzzle11AB extends PuzzleBase {

    private enum Directions {n, ne, nw, se, sw, s}

    private static final Boolean showDebugMsg = false;

    protected boolean DataIsValid(ArrayList<String> inputData) {
        return ValidateSingleLineDataFile(inputData);
    }

    protected int SolveMe(ArrayList<String> inputData) {
        int l = 0;
        int r = 0;
        int lastSteps = 0;
        int maxSteps = 0;

        for (String d : inputData.get(0).trim().split(",")) {
            switch (Directions.valueOf(d)) {
                case n:
                    l--;
                    r--;
                    break;
                case ne:
                    r--;
                    break;
                case nw:
                    l--;
                    break;
                case s:
                    l++;
                    r++;
                    break;
                case se:
                    l++;
                    break;
                case sw:
                    r++;
                    break;
                default:
                    throw new IllegalArgumentException(String.format("An illegal direction was requested: %s", d));
            }

            if (showDebugMsg)
                System.out.println(String.format("D: %s, l: %d r: %d ", d, l, r));

            lastSteps = calculateTotalSteps(l, r);
            maxSteps = lastSteps > maxSteps ? lastSteps : maxSteps;
        }

        System.out.println(String.format("The max steps away was %d, current steps away is %d", maxSteps, lastSteps));
        return lastSteps;
    }

    private Integer calculateTotalSteps(int l, int r) {

        int t = 0;
        int u = 0;
        if (l * r < 0) {
            t = abs(l) + abs(r);
        }
        else {
            u = combineAngleMovement(l, r);
            l -= u;
            r -= u;
            t = abs(u) + abs(l) + abs(r);
        }

        if (showDebugMsg)
            System.out.println(String.format("t: %d, u: %d, l: %d r: %d ", t, u, l, r));

        return t;
    }

    private int combineAngleMovement(int l, int r) {
        int t = 0;

        int il = l < 0 ? 1 : -1;
        int ir = r < 0 ? 1 : -1;

        for (; l != 0 && r != 0; l += il, r += ir, t++);

        return t * (-1 * il);
    }

}
