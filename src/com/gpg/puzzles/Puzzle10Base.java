package com.gpg.puzzles;

import java.util.ArrayList;

/**
 * Created by ggillis on 12/16/2017.
 *
 * Base class for Puzzle 10 Solver.
 */
public abstract class Puzzle10Base extends PuzzleBase {

    static final int skipSizeIncrement = 1;
    static final Boolean showDebugMsg = false;
    static final int circListSize = 256;

    protected boolean DataIsValid(ArrayList<String> inputData) {
        return ValidateSingleLineDataFile(inputData);
    }

    protected void reverseSelection(Integer[] circList, int currentPosition, int len) {

        if (showDebugMsg)
            System.out.println(String.format("reverseSelection: currentPosition = %d, len = %d", currentPosition, len));

        int cp = currentPosition;
        int ep = currentPosition + len - 1;

        while (cp < ep) {
            int cpp = cp;
            cpp = cpp >= circList.length ? cpp - circList.length : cpp;
            int epp = ep;
            while (epp >= circList.length) {
                epp = epp - circList.length;
            }

            if (showDebugMsg)
                System.out.println(String.format("cp = %d, cpp = %d, ep = %d, epp = %d", cp, cpp, ep, epp));

            int sv = circList[cpp];
            circList[cpp] = circList[epp];
            circList[epp] = sv;
            cp++;
            ep--;
        }
    }

    protected int setCurrentPosition(int circListLen, int currentPosition, int len, int skipSize) {
        int p = (len + currentPosition + skipSize);
        while (p > circListLen) {
            p = p - circListLen;
        }

        return p;
    }

    protected Integer[] generateCircList(Integer listSize) {
        Integer[] cl = new Integer[listSize];
        for (int i = 0; i < listSize.intValue(); i++) {
            cl[i] = i;
        }
        return cl;
    }
}
