package com.gpg.puzzles;

import java.util.ArrayList;
import java.util.List;

/**
 Advent Of Code Puzzle :
 http://adventofcode.com/2017/day/5

 --- Part B ---

 Now, the jumps are even stranger: after each jump,
 if the offset was three or more, instead decrease it by 1.
 Otherwise, increase it by 1 as before.

 Using this rule with the Puzzle5A example, the process now takes 10 steps,
 and the offset values after finding the exit are left as 2 3 2 3 -1.

 How many steps does it now take to reach the exit?

 The correct answer is 25347697 with puzzle5.dat

 */
public class Puzzle5B extends Puzzle5Base {

    protected int SolveMe(ArrayList<String> inputData) {
        int steps = 0;
        List<Integer> instructions = loadInstructions(inputData);
        int ip = 0;

        Boolean processing = true;

        System.out.print("Processing instructions: ");
        while (processing) {
            int jmp = instructions.get(ip);
            //System.out.print(String.format("%d ", jmp));
            if (steps % 1000 == 0) System.out.print(".");
            instructions.set(ip, (jmp >= 3 ? jmp - 1 : jmp + 1));
            ip += jmp;
            processing = ip < instructions.size();
            steps++;
        }

        System.out.println("\nExit.");
        return steps;
    }
}
