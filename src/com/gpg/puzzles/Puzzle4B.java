package com.gpg.puzzles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 Advent Of Code Puzzle 4B:
 http://adventofcode.com/2017/day/4

 --- Day 4: High-Entropy Passphrases ---

 --- Part Two ---

 For added security, yet another system policy has been put in place.
 Now, a valid passphrase must contain no two words that are anagrams of each
 other - that is, a passphrase is invalid if any word's letters can be
 rearranged to form any other word in the passphrase.

 For example:

 abcde fghij is a valid passphrase.
 abcde xyz ecdab is not valid - the letters from the third word can be rearranged to form the first word.
 a ab abc abd abf abj is a valid passphrase, because all letters need to be used when forming another word.
 iiii oiii ooii oooi oooo is valid.
 oiii ioii iioi iiio is not valid - any of these words can be rearranged to form any other word.
 Under this new system policy, how many passphrases are valid?

 The correct answer is 251 using puzzle4.dat

 Remarks:
    1.  To determine if a test word is an anagram of any other word in the phrase
        we will decompose a test word into a collection of characters, sort this collection
        and create a key string of this result to be used to test for pre-existence.
        If no pre-existence is found, the key string is saved to the unique collection.
  */
public class Puzzle4B extends PuzzleBase {

    protected boolean DataIsValid(ArrayList<String> inputData) {
        return ValidateMultiLineDataFile(inputData);
    }

    protected int SolveMe(ArrayList<String> inputData) {
        int validCount = 0;
        List<String> uniques = new ArrayList<String>();

        System.out.println(String.format("Testing %d passphrases.", inputData.size()));
        for (String phrase: inputData) {
            Boolean pass = true;
            uniques.clear();

            System.out.print("Testing ");
            for (String word: phrase.split("\\s+")) {

                System.out.print(".");
                char[] c = word.toCharArray();          // [1]
                Arrays.sort(c);
                String key = Arrays.toString(c);

                if (uniques.contains(key)) {
                    pass = false;
                    System.out.println(" anagram found.");
                    break;
                }
                uniques.add(key);
            }

            if (pass) {
                System.out.println(" passed.");
                validCount++;
            }
        }

        return validCount;
    }
}
