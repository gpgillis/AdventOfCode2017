package com.gpg.puzzles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 Created by ggillis on 12/18/2017.

 Advent Of Code Puzzle :
 http://adventofcode.com/2017/day/13

 --- Day 13: Packet Scanners ---
 You need to cross a vast firewall. The firewall consists of several layers, each with a security scanner
 that moves back and forth across the layer. To succeed, you must not be detected by a scanner.

 By studying the firewall briefly, you are able to record (in your puzzle input) the depth of each layer
 and the range of the scanning area for the scanner within it, written as depth: range.
 Each layer has a thickness of exactly 1.
 A layer at depth 0 begins immediately inside the firewall; a layer at depth 1 would start immediately after that.

 For example, suppose you've recorded the following:

 0: 3
 1: 2
 4: 4
 6: 4

 This means that there is a layer immediately inside the firewall (with range 3),
 a second layer immediately after that (with range 2), a third layer which begins at
 depth 4 (with range 4), and a fourth layer which begins at depth 6 (also with range 4).

 Visually, it might look like this:

 0   1   2   3   4   5   6
 [ ] [ ] ... ... [ ] ... [ ]
 [ ] [ ]         [ ]     [ ]
 [ ]             [ ]     [ ]
                 [ ]     [ ]

 Within each layer, a security scanner moves back and forth within its range.
 Each security scanner starts at the top and moves down until it reaches the bottom,
 then moves up until it reaches the top, and repeats. A security scanner takes one picosecond to move one step.
 Drawing scanners as S, the first few picoseconds look like this:


 Picosecond 0:
 0   1   2   3   4   5   6
 [S] [S] ... ... [S] ... [S]
 [ ] [ ]         [ ]     [ ]
 [ ]             [ ]     [ ]
                 [ ]     [ ]

 Picosecond 1:
 0   1   2   3   4   5   6
 [ ] [ ] ... ... [ ] ... [ ]
 [S] [S]         [S]     [S]
 [ ]             [ ]     [ ]
                 [ ]     [ ]

 Picosecond 2:
 0   1   2   3   4   5   6
 [ ] [S] ... ... [ ] ... [ ]
 [ ] [ ]         [ ]     [ ]
 [S]             [S]     [S]
                 [ ]     [ ]

 Picosecond 3:
 0   1   2   3   4   5   6
 [ ] [ ] ... ... [ ] ... [ ]
 [S] [S]         [ ]     [ ]
 [ ]             [ ]     [ ]
                 [S]     [S]

 Your plan is to hitch a ride on a packet about to move through the firewall.
 The packet will travel along the top of each layer, and it moves at one layer per picosecond.
 Each picosecond, the packet moves one layer forward (its first move takes it into layer 0),
 and then the scanners move one step. If there is a scanner at the top of the layer as your
 packet enters it, you are caught. (If a scanner moves into the top of its layer while you are
 there, you are not caught: it doesn't have time to notice you before you leave.)

 If you were to do this in the configuration above, marking your current position with parentheses,
 your passage through the firewall would look like this:

 Initial state:
 0   1   2   3   4   5   6
 [S] [S] ... ... [S] ... [S]
 [ ] [ ]         [ ]     [ ]
 [ ]             [ ]     [ ]
                 [ ]     [ ]

 Picosecond 0:
 0   1   2   3   4   5   6
 (S) [S] ... ... [S] ... [S]
 [ ] [ ]         [ ]     [ ]
 [ ]             [ ]     [ ]
                 [ ]     [ ]

 0   1   2   3   4   5   6
 ( ) [ ] ... ... [ ] ... [ ]
 [S] [S]         [S]     [S]
 [ ]             [ ]     [ ]
                 [ ]     [ ]


 Picosecond 1:
 0   1   2   3   4   5   6
 [ ] ( ) ... ... [ ] ... [ ]
 [S] [S]         [S]     [S]
 [ ]             [ ]     [ ]
                 [ ]     [ ]

 0   1   2   3   4   5   6
 [ ] (S) ... ... [ ] ... [ ]
 [ ] [ ]         [ ]     [ ]
 [S]             [S]     [S]
                 [ ]     [ ]


 Picosecond 2:
 0   1   2   3   4   5   6
 [ ] [S] (.) ... [ ] ... [ ]
 [ ] [ ]         [ ]     [ ]
 [S]             [S]     [S]
                 [ ]     [ ]

 0   1   2   3   4   5   6
 [ ] [ ] (.) ... [ ] ... [ ]
 [S] [S]         [ ]     [ ]
 [ ]             [ ]     [ ]
                 [S]     [S]


 Picosecond 3:
 0   1   2   3   4   5   6
 [ ] [ ] ... (.) [ ] ... [ ]
 [S] [S]         [ ]     [ ]
 [ ]             [ ]     [ ]
                 [S]     [S]

 0   1   2   3   4   5   6
 [S] [S] ... (.) [ ] ... [ ]
 [ ] [ ]         [ ]     [ ]
 [ ]             [S]     [S]
                 [ ]     [ ]


 Picosecond 4:
 0   1   2   3   4   5   6
 [S] [S] ... ... ( ) ... [ ]
 [ ] [ ]         [ ]     [ ]
 [ ]             [S]     [S]
                 [ ]     [ ]

 0   1   2   3   4   5   6
 [ ] [ ] ... ... ( ) ... [ ]
 [S] [S]         [S]     [S]
 [ ]             [ ]     [ ]
                 [ ]     [ ]


 Picosecond 5:
 0   1   2   3   4   5   6
 [ ] [ ] ... ... [ ] (.) [ ]
 [S] [S]         [S]     [S]
 [ ]             [ ]     [ ]
                 [ ]     [ ]

 0   1   2   3   4   5   6
 [ ] [S] ... ... [S] (.) [S]
 [ ] [ ]         [ ]     [ ]
 [S]             [ ]     [ ]
                 [ ]     [ ]


 Picosecond 6:
 0   1   2   3   4   5   6
 [ ] [S] ... ... [S] ... (S)
 [ ] [ ]         [ ]     [ ]
 [S]             [ ]     [ ]
                 [ ]     [ ]

 0   1   2   3   4   5   6
 [ ] [ ] ... ... [ ] ... ( )
 [S] [S]         [S]     [S]
 [ ]             [ ]     [ ]
                 [ ]     [ ]

 In this situation, you are caught in layers 0 and 6, because your packet entered the layer
 when its scanner was at the top when you entered it. You are not caught in layer 1, since the scanner
 moved into the top of the layer once you were already there.

 The severity of getting caught on a layer is equal to its depth multiplied by its range.
 (Ignore layers in which you do not get caught.) The severity of the whole trip is the sum of these values.
 In the example above, the trip severity is 0*3 + 6*4 = 24.

 Given the details of the firewall you've recorded, if you leave immediately, what is the severity of your whole trip?


 The correct answer is 1960 with puzzle13.dat

 Part B:

 The correct answer is 3903378 with puzzle13.dat


 */
public class Puzzle13AB extends PuzzleBase {

    private boolean _showDebugMsg = false;

    protected boolean DataIsValid(ArrayList<String> inputData) {

        return ValidateMultiLineDataFile(inputData);
    }

    protected int SolveMe(ArrayList<String> inputData) {

        int penalty = runPartA(inputData);
        System.out.println(String.format("Part A total trip penalty = %d", penalty));

        long delay = runPartB(inputData);
        System.out.println(String.format("Part B delay to avoid capture = %d", delay));

        return 0;
    }

    private int runPartA(List<String> inputData) {

        int penalty = 0;
        List<Layer> firewall = loadData(inputData);
        Map<Integer, Integer> zeroIntervals = calculateZeroIntervals(firewall);

        for (int ps = 0; ps < firewall.size(); ps++) {
            if (zeroIntervals.containsKey(ps)) {
                if (ps % zeroIntervals.get(ps).intValue() == 0) {
                    System.out.println("CAUGHT");
                    penalty += ps * firewall.get(ps).get_depth();
                }
            }
        }

//        for (int ps = 0; ps < firewall.size(); ps++) {
//            System.out.println(String.format("PicoSec %d enter ... ", ps));
//            Boolean caught = firewall.get(ps).enterLayer();
//            if (caught) {
//                System.out.println("CAUGHT");
//                penalty += ps * firewall.get(ps).get_depth();
//            }
//            for (int pls = 0; pls < firewall.size(); pls++) {
//                firewall.get(pls).pulseLayer();
//            }
//        }

        return penalty;
    }

    private long runPartB(List<String> inputData) {

        int delay = 0;
        boolean caught = true;

        List<Layer> firewall = loadData(inputData);
        Map<Integer, Integer> zeroIntervals = calculateZeroIntervals(firewall);

        while (caught) {
//            if (delay % 100000 == 0) System.out.print(String.format("D:%d ", delay));

            for (int ps = delay, pos = 0; pos < firewall.size(); ps++) {
                caught = false;
                if (zeroIntervals.containsKey(pos)) {
                    if (ps % zeroIntervals.get(pos).intValue() == 0) {
                        caught = true;
                        delay++;
                        break;
                    }
                }
                pos++;
            }
        }

        return delay;

//        int delay = 0;
//        int pos = 0;
//        boolean caught = true;
//        List<Layer> firewall = loadData(inputData);
//
//        while (caught) {
//            if (delay % 10000 == 0) System.out.print(String.format("D:%d ", delay));
//            for (int ps = 0; pos < firewall.size(); ps++) {
//                if (_showDebugMsg) System.out.println(String.format("PicoSec %d enter : delay = %d... ", ps, delay));
//
//                if (ps >= delay) {
//                    caught = firewall.get(pos).enterLayer();
//                    if (caught) {
//                        if (_showDebugMsg) System.out.println("CAUGHT");
//                        delay++;
//                        pos = 0;
//                        for (int idx = 0; idx < firewall.size(); idx++) {
//                            firewall.get(idx).reset();
//                        }
//                        break;
//                    }
//                    pos++;
//                }
//
//                if (_showDebugMsg) System.out.println("\nPulsing ...");
//
//                for (int pls = 0; pls < firewall.size(); pls++) {
//                    firewall.get(pls).pulseLayer();
//                }
//            }
//        }
//
//        return delay;
    }

    private List<Layer> loadData(List<String> inputData) {
        List<Layer> rtn = new ArrayList<Layer>();

        for (String s : inputData) {
            String el[] = s.split(":\\s*");
            int idx = Integer.parseInt(el[0]);
            int dpt = Integer.parseInt(el[1]);

            int zdlToAdd = idx - rtn.size();  // Number of zero depth layers to add.
            while (zdlToAdd > 0) {
                rtn.add(new Layer(rtn.size(), 0));
                zdlToAdd--;
            }

            rtn.add(new Layer(idx, dpt));
        }

        return rtn;
    }

    private Map<Integer, Integer> calculateZeroIntervals(List<Layer> firewall) {
        Map<Integer, Integer> zeroInts = new HashMap<Integer, Integer>();
        for (int i = 0; i < firewall.size(); i++) {
            int zi = firewall.get(i).calculateScannerZeroInterval();
            if (zi > 0) {
                zeroInts.put(firewall.get(i).get_id(), zi);
            }
        }
        return zeroInts;
    }

    protected class Layer {
        private int _id;
        private int _depth;
        private int _scannerLoc  = -1;
        private int _scannerStep = 1;

        public Layer(int id, int depth) {

            _id = id;

            _depth = depth;
            if (depth > 0) {
                _scannerLoc = 0;
            }
        }

        public int get_id() { return _id; }

        public int get_depth() { return _depth; }

        public void reset() {
            if (_depth == 0) return;

            _scannerLoc = 0;
            _scannerStep = 1;
        }

        public Boolean enterLayer() {
            return (_scannerLoc == 0); // If you enter the layer with the scanner at the top, you are caught.
        }

        public void pulseLayer() {
            if (_depth > 0) {
                _scannerLoc += _scannerStep;

                if (_scannerLoc == 0 || _scannerLoc + 1 == _depth) {
                    _scannerStep *= -1;
                }
            }

            if (_showDebugMsg) System.out.println(String.format("Layer %d depth %d scanloc %d, scanstep %d", _id, _depth, _scannerLoc, _scannerStep));
        }

        public int calculateScannerZeroInterval() {
            return (_depth == 0) ? -1 : (_depth - 1) * 2;
        }
    }
}
