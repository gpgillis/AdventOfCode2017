package com.gpg.puzzles;

import static com.gpg.util.Logging.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 Advent Of Code Puzzle :
 http://adventofcode.com/2017/day/6

 --- Day 6: Memory Reallocation ---

 A debugger program here is having an issue: it is trying to repair a memory reallocation
 routine, but it keeps getting stuck in an infinite loop.

 In this area, there are sixteen memory banks; each memory bank can hold any number of blocks.
 The goal of the reallocation routine is to balance the blocks between the memory banks.

 The reallocation routine operates in cycles. In each cycle, it finds the memory bank with the
 most blocks (ties won by the lowest-numbered memory bank) and redistributes those blocks among
 the banks. To do this, it removes all of the blocks from the selected bank, then moves to the
 next (by index) memory bank and inserts one of the blocks.
 It continues doing this until it runs out of blocks;
 if it reaches the last memory bank, it wraps around to the first one.

 The debugger would like to know how many redistributions can be done before a blocks-in-banks
 configuration is produced that has been seen before.

 For example, imagine a scenario with only four memory banks:

 The banks start with 0, 2, 7, and 0 blocks.
 The third bank has the most blocks, so it is chosen for redistribution.
 Starting with the next bank (the fourth bank) and then continuing to the
 first bank, the second bank, and so on, the 7 blocks are spread out over
 the memory banks. The fourth, first, and second banks get two blocks each,
 and the third bank gets one back. The final result looks like this: 2 4 1 2.

 Next, the second bank is chosen because it contains the most blocks (four).
 Because there are four memory banks, each gets one block. The result is: 3 1 2 3.
 Now, there is a tie between the first and fourth memory banks, both of which have three blocks.
 The first bank wins the tie, and its three blocks are distributed evenly over the other three banks, leaving it with none: 0 2 3 4.
 The fourth bank is chosen, and its four blocks are distributed such that each of the four banks receives one: 1 3 4 1.
 The third bank is chosen, and the same thing happens: 2 4 1 2.

 At this point, we've reached a state we've seen before: 2 4 1 2 was already seen.
 The infinite loop is detected after the fifth block redistribution cycle,
 and so the answer in this example is 5.

 Given the initial block counts in your puzzle input, how many redistribution cycles
 must be completed before a configuration is produced that has been seen before?

 The correct answer for A is 12841 using puzzle6.dat
 The correct answer for B is 8038 using puzzle6.dat

 */
public class Puzzle6AB extends PuzzleBase {

    protected boolean DataIsValid(ArrayList<String> inputData) {
        return ValidateSingleLineDataFile(inputData);
    }

    protected int SolveMe(ArrayList<String> inputData)
    {
        int distCycles = 0;
        int cyclesBetweenSeen = 0;
        List<String> seenConfigurations = new ArrayList<String>();
        List<Integer> memory = loadMemoryConfiguration(inputData);

        while(true) {
            distCycles++;
            int startingIdx = getStartingIndex(memory);
            if (startingIdx < 0) {
                fail("No starting index was found!", null);
            }

            String config = distributeBlocks(memory,startingIdx);
            System.out.println(String.format("\nMemory: %s", config));

            if (seenConfigurations.contains(config)) {
                cyclesBetweenSeen = solvePuzzle6B(seenConfigurations, config);
                break;
            }

            seenConfigurations.add(config);
        }

        System.out.println(String.format("\nDistribution complete after %d cycles.", distCycles));
        System.out.println(String.format("Cycles between first seen and last: %d", cyclesBetweenSeen));
        return distCycles;
    }

    /**
     * Loads a single dimensional list with integers representing blocks in a memory location
     * from a data file.  The input data is a single line file contains whitespace delimited
     * integers.
     * @param inputData
     * @return single dimension list of integers.
     */
    private List<Integer> loadMemoryConfiguration(List<String> inputData) {
        List<Integer> memory = new ArrayList<Integer>();

        try {
            for (String s : inputData.get(0).split("\\s+")) {
                int blocks = Integer.parseInt(s.trim());
                memory.add(blocks);
            }
        }
        catch (NumberFormatException e) {
            fail("Each memory configuration must be an integer.", e);
        }

        return memory;
    }

    /**
     * Distributes memory blocks from startingIndex location, setting the blocks in location
     * to 0 and distributing one block per location, starting with startingIndex + 1 and
     * wrapping until all blocks have been distributed.  A string representation of the final memory
     * configuration is returned.
     * @param memory
     * @param startingIndex
     * @return memory configuration after block distribution.
     */
    private String distributeBlocks(List<Integer> memory, int startingIndex) {

        int blocks = memory.get(startingIndex);
        memory.set(startingIndex, 0);

        System.out.println(String.format("\nDistributing %d blocks starting at %d", blocks, startingIndex));
        for (int idx = startingIndex + 1; blocks > 0; idx++) {
            if (idx >= memory.size())
                idx = 0;
            System.out.print(String.format("ML:%d ", idx));
            memory.set(idx, memory.get(idx) + 1);
            blocks--;
        }

        return Arrays.toString(memory.toArray());
    }

    private Integer getStartingIndex(List<Integer> memory) {
        int max = Collections.max(memory);
        return memory.indexOf(max);
    }

    private Integer solvePuzzle6B(List<String> seenConfigurations, String target) {
        int firstId = seenConfigurations.indexOf(target);
        return seenConfigurations.size() - firstId;
    }
}
