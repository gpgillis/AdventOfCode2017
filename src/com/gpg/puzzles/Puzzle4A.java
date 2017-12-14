package com.gpg.puzzles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 Advent Of Code Puzzle 4B:
 http://adventofcode.com/2017/day/4

 --- Day 4: High-Entropy Passphrases ---

 A new system policy has been put in place that requires all accounts to
 use a passphrase instead of simply a password.
 A passphrase consists of a series of words (lowercase letters) separated by spaces.

 To ensure security, a valid passphrase must contain no duplicate words.

 For example:

 aa bb cc dd ee is valid.
 aa bb cc dd aa is not valid - the word aa appears more than once.
 aa bb cc dd aaa is valid - aa and aaa count as different words.
 The system's full passphrase list is available as your puzzle input. How many passphrases are valid?

 The correct answer is 466 with puzzle4.dat
 */
public class Puzzle4A extends PuzzleBase {

    protected boolean DataIsValid(ArrayList<String> inputData) {
        return ValidateMultiLineDataFile(inputData);
    }

    protected int SolveMe(ArrayList<String> inputData) {

        int validCount = 0;
        Map<String, Integer> uniques = new HashMap<String, Integer>();

        System.out.println(String.format("Testing %d passphrases.", inputData.size()));
        for (String phrase: inputData) {
            Boolean pass = true;
            uniques.clear();

            System.out.print("Testing ");
            for (String word: phrase.split("\\s+")) {

                System.out.print(".");
                if (uniques.containsKey(word)) {
                    pass = false;
                    System.out.println(" failed.");
                    break;
                }
                uniques.put(word, 0);
            }

            if (pass) {
                System.out.println(" passed.");
                validCount++;
            }
        }

        return validCount;
    }
}
