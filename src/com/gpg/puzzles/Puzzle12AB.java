package com.gpg.puzzles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
    Created by ggillis on 12/17/2017.

    Advent Of Code Puzzle :
    http://adventofcode.com/2017/day/12

    --- Day 12: Digital Plumber ---
    Walking along the memory banks of the stream, you find a small village that is experiencing
    a little confusion: some programs can't communicate with each other.

    Programs in this village communicate using a fixed system of pipes.
    Messages are passed between programs using these pipes, but most programs aren't
    connected to each other directly.

    Instead, programs pass messages between each other until the message reaches the intended recipient.

    For some reason, though, some of these messages aren't ever reaching their intended
    recipient, and the programs suspect that some pipes are missing. They would like you to investigate.

    You walk through the village and record the ID of each program and the IDs with which it
    can communicate directly (your puzzle input). Each program has one or more programs with
    which it can communicate, and these pipes are bidirectional; if 8 says it can communicate
    with 11, then 11 will say it can communicate with 8.

    You need to figure out how many programs are in the group that contains program ID 0.

    For example, suppose you go door-to-door like a travelling salesman and record the following list:

    0 <-> 2
    1 <-> 1
    2 <-> 0, 3, 4
    3 <-> 2, 4
    4 <-> 2, 3, 6
    5 <-> 6
    6 <-> 4, 5
    In this example, the following programs are in the group that contains program ID 0:

    Program 0 by definition.
    Program 2, directly connected to program 0.
    Program 3 via program 2.
    Program 4 via program 2.
    Program 5 via programs 6, then 4, then 2.
    Program 6 via programs 4, then 2.
    Therefore, a total of 6 programs are in this group; all but program 1, which has a pipe that connects it to itself.

    How many programs are in the group that contains program ID 0?

    The correct answer is 288 with puzzle12.dat

    --- Part Two ---
    There are more programs than just the ones in the group containing program ID 0.
    The rest of them have no way of reaching that group, and still might have no way of reaching each other.

    A group is a collection of programs that can all communicate via pipes either directly or indirectly.
    The programs you identified just a moment ago are all part of the same group.
    Now, they would like you to determine the total number of groups.

    In the example above, there were 2 groups: one consisting of programs 0,2,3,4,5,6,
    and the other consisting solely of program 1.

    How many groups are there in total?

    The correct answer is 211 with puzzle12.dat

 */
public class Puzzle12AB extends PuzzleBase {
    protected boolean DataIsValid(ArrayList<String> inputData) {
        return ValidateMultiLineDataFile(inputData);
    }

    protected int SolveMe(ArrayList<String> inputData) {
        Map<Integer, List<Integer>> progMap = new HashMap<Integer, List<Integer>>();

        Map<Integer, List<Integer>> groups = new HashMap<Integer, List<Integer>>();

        loadData(progMap, inputData);

        for (Integer pid : progMap.keySet()) {
            getGroupMembers(progMap, groups, pid, -1);
        }

        List<Integer> group = groups.get(0);
        Integer g[] = group.toArray(new Integer[group.size()]);
        Arrays.sort(g);

        System.out.println(String.format("There are %d groups.", groups.keySet().size()));
        System.out.println(String.format("There are %d programs in the 0 group.", g.length));
        //System.out.println(String.format("The following pid's are in the 0 group: %s", Arrays.toString(g)));

        return 0;
    }

    private void getGroupMembers(Map<Integer, List<Integer>> progMap, Map<Integer, List<Integer>> groups, Integer key, Integer grpIdx) {

        for (Integer idx: groups.keySet()) {
            List<Integer> grp = groups.get(idx);
            if (grp.contains(key)) {
                return;
            }
        }

        if (!groups.containsKey(grpIdx)) {
            Integer idx = groups.size();
            groups.put(idx, new ArrayList<Integer>());
            grpIdx = idx;
        }

        groups.get(grpIdx).add(key);

        for (Integer pid : progMap.get(key)) {
            getGroupMembers(progMap, groups, pid, grpIdx);
        }
    }

    private void loadData(Map<Integer, List<Integer>> progMap, List<String> inputData) {
        for (String l : inputData) {
            String line[] = l.split("\\s*<->\\s*");
            if (line.length != 2)
                throw new IllegalArgumentException(String.format("The data line %s is incorrectly formed.", l));

            Integer key = Integer.parseInt(line[0].trim());
            String progs[] = line[1].split(",\\s*");
            for (String prog : progs) {
                Integer pid = Integer.parseInt(prog.trim());
                addToProgMap(progMap, key, pid);
            }
        }
    }

    private void addToProgMap(Map<Integer, List<Integer>> progMap, Integer key, Integer val) {
        addToProgMap(progMap, key, val, false);
    }

    private void addToProgMap(Map<Integer, List<Integer>> progMap, Integer key, Integer val, Boolean rentrant) {
        if (!progMap.containsKey(key)) {
            progMap.put(key, new ArrayList<Integer>());
        }

        if (!progMap.get(key).contains(val)) {
            progMap.get(key).add(val);
        }

        if (rentrant)
            return;

        addToProgMap(progMap, val, key, true);
    }

}
