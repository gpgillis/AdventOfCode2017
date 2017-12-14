package com.gpg.puzzles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 Advent Of Code Puzzle :
 http://adventofcode.com/2017/day/8

 --- Day 8: I Heard You Like Registers ---
 You receive a signal directly from the CPU. Because of your recent assistance
 with jump instructions, it would like you to compute the result of a series of
 unusual register instructions.

 Each instruction consists of several parts:
 the register to modify,
 whether to increase or decrease that register's value,
 the amount by which to increase or decrease it,
 and a condition.

 If the condition fails, skip the instruction without modifying the register.
 The registers all start at 0. The instructions look like this:

 b inc 5 if a > 1
 a inc 1 if b < 5
 c dec -10 if a >= 1
 c inc -20 if c == 10
 These instructions would be processed as follows:

 Because a starts at 0, it is not greater than 1, and so b is not modified.
 a is increased by 1 (to 1) because b is less than 5 (it is 0).
 c is decreased by -10 (to 10) because a is now greater than or equal to 1 (it is 1).
 c is increased by -20 (to -10) because c is equal to 10.
 After this process, the largest value in any register is 1.

 You might also encounter <= (less than or equal to) or != (not equal to).

 However, the CPU doesn't have the bandwidth to tell you what all the registers
 are named, and leaves that to you to determine.

 What is the largest value in any register after completing the instructions in your puzzle input?

 The correct answer is 6611 with puzzle8.dat

 --- Part Two ---
 To be safe, the CPU also needs to know the highest value held in any register
 during this process so that it can decide how much memory to allocate to these operations.
 For example, in the above instructions, the highest value ever held was 10
 (in register c after the third instruction was evaluated).

 */
public class Puzzle8AB extends PuzzleBase{

    private static final int EQUAL = 0;
    private static final int NOTEQUAL = 1;
    private static final int GREATERTHEN =2;
    private static final int LESSTHEN = 3;
    private static final int GREATEROREQUAL = 4;
    private static final int LESSOREQUAL = 5;

    protected boolean DataIsValid(ArrayList<String> inputData) {
        return ValidateMultiLineDataFile(inputData);
    }

    Map<String, Integer> registers = new HashMap<String, Integer>();

    protected int SolveMe(ArrayList<String> inputData) {

        initializeRegisters(registers, inputData);
        int maxRegisterValue = 0;

        for (String instruction : inputData) {
            int val = processInstruction(instruction, registers);
            if (val > maxRegisterValue) {
                maxRegisterValue = val;
            }
        }
        System.out.println(String.format("The max register value was %d", maxRegisterValue));
        return Collections.max(registers.values());
    }

    private void initializeRegisters(Map<String, Integer> registers, List<String> inputData) {
        System.out.print("Initializing registers ... ");
        for (String instruction : inputData) {
            String registerName = instruction.substring(0, instruction.indexOf(" "));
            registers.put(registerName, 0);
        }

        System.out.println(String.format("done!  Loaded %d registers.", registers.keySet().size()));
    }

    private Integer convertTestOperation(String el) {
        if (el.equals("==")) return EQUAL;
        if (el.equals("!=")) return NOTEQUAL;
        if (el.equals(">")) return GREATERTHEN;
        if (el.equals(">=")) return GREATEROREQUAL;
        if (el.equals("<")) return LESSTHEN;
        if (el.equals("<=")) return LESSOREQUAL;

        throw new IllegalArgumentException(String.format("An unknown test operation was requested - %s", el));
    }

    private Boolean evaluateTest(Integer registerVal, Integer testVal, Integer operation) {
        System.out.print(String.format("EVAL: rv = %d, tv = %d op : ", registerVal, testVal));
        switch (operation) {
            case EQUAL:
                System.out.print(" == ");
                return registerVal.intValue() == testVal.intValue();
            case NOTEQUAL:
                System.out.print(" != ");
                return registerVal.intValue() != testVal.intValue();
            case GREATERTHEN:
                System.out.print(" > ");
                return registerVal.intValue() > testVal.intValue();
            case GREATEROREQUAL:
                System.out.print(" >= ");
                return registerVal.intValue() >= testVal.intValue();
            case LESSTHEN:
                System.out.print(" < ");
                return registerVal.intValue() < testVal.intValue();
            case LESSOREQUAL:
                System.out.print(" <= ");
                return registerVal.intValue() <= testVal.intValue();
        }

        throw new IllegalArgumentException("An unknown test operation was requested.");
    }

    /**
     *
     * @param instruction
     * @param registers
     * @return final value of target register.
     */
    private Integer processInstruction(String instruction, Map<String, Integer> registers) {
        String[] elements = instruction.split("\\s+");
        String targetRegister = elements[0];
        String operation = elements[1];
        Integer operationVal = Integer.parseInt(elements[2]);
        String testRegister = elements[4];
        Integer testOperation = convertTestOperation(elements[5]);
        Integer testVal = Integer.parseInt(elements[6]);

        int targetVal = registers.get(targetRegister);
        int registerVal = registers.get(testRegister);

        System.out.println(String.format("\nProcessing %s against %s", targetRegister, testRegister));
        if (evaluateTest(registerVal, testVal, testOperation)) {    // evaluation fails -> NO-OP
            System.out.println(" TRUE");

            if (operation.equals("inc")) {
                System.out.println(String.format("INC : rv = %d, ov = %d", targetVal, operationVal));
                targetVal += operationVal;
            }
            else if (operation.equals("dec")) {
                System.out.println(String.format("DEC : rv = %d, ov = %d", targetVal, operationVal));
                targetVal -= operationVal;
            }
            else {
                throw new IllegalArgumentException(String.format("An invalid operation was requested : %s", operation));
            }

            registers.put(targetRegister, targetVal);
            System.out.println(String.format("RESULT: register %s = %d", targetRegister, registers.get(targetRegister)));
        }
        else {
            System.out.println(" FALSE");
        }
        return targetVal;
    }
}
