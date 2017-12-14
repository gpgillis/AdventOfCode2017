package com.gpg.puzzles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 Advent Of Code Puzzle :
 http://adventofcode.com/2017/day/7

 --- Day 7: Recursive Circus ---

 Wandering further through the circuits of the computer, you come upon a tower of programs
 that have gotten themselves into a bit of trouble.
 A recursive algorithm has gotten out of hand, and now they're balanced precariously in a large tower.

 One program at the bottom supports the entire tower. It's holding a large disc,
 and on the disc are balanced several more sub-towers. At the bottom of these sub-towers,
 standing on the bottom disc, are other programs, each holding their own disc, and so on.

 At the very tops of these sub-sub-sub-...-towers, many programs stand simply keeping the disc
 below them balanced but with no disc of their own.

 You offer to help, but first you need to understand the structure of these towers.
 You ask each program to yell out their name, their weight, and (if they're holding a disc)
 the names of the programs immediately above them balancing on that disc.
 You write this information down (your puzzle input). Unfortunately, in their panic,
 they don't do this in an orderly fashion; by the time you're done,
 you're not sure which program gave which information.

 For example, if your list is the following:

 pbga (66)
 xhth (57)
 ebii (61)
 havc (66)
 ktlj (57)
 fwft (72) -> ktlj, cntj, xhth
 qoyq (66)
 padx (45) -> pbga, havc, qoyq
 tknk (41) -> ugml, padx, fwft
 jptl (61)
 ugml (68) -> gyxo, ebii, jptl
 gyxo (61)
 cntj (57)

 ...then you would be able to recreate the structure of the towers that looks like this:

             gyxo
           /
      ugml - ebii
     /     \
    |        jptl
    |
     |          pbga
     /         /
 tknk --- padx - havc
     \         \
     |          qoyq
    |
    |         ktlj
     \      /
      fwft - cntj
           \
              xhth

 In this example, tknk is at the bottom of the tower (the bottom program),
 and is holding up ugml, padx, and fwft.
 Those programs are, in turn, holding up other programs;
 in this example, none of those programs are holding up any other programs,
 and are all the tops of their own towers. (The actual tower balancing in front of you is much larger.)

 Before you're ready to help them, you need to make sure your information is correct.
 What is the name of the bottom program?

 Created by ggillis on 12/9/2017.

 Remarks:

 Ordering the sample data with weighting:
                             gyxo (61)
                 ugml (68)   ebii (61)
                             jptl (61)

                             pbga (66)
     tknk (41)   padx (45)   havc (66)
                             qoyq (66)

                             ktlj (57)
                 fwft (72)   cntj (57)
                             xhth (57)

 The correct answer is hlhomy using puzzle7.dat

 --- Part Two ---

 The programs explain the situation: they can't get down.
 Rather, they could get down, if they weren't expending all of their energy
 trying to keep the tower balanced.
 Apparently, one program has the wrong weight, and until it's fixed, they're stuck here.

 For any program holding a disc, each program standing on that disc forms a sub-tower.
 Each of those sub-towers are supposed to be the same weight, or the disc itself isn't balanced.
 The weight of a tower is the sum of the weights of the programs in that tower.

 In the example above, this means that for ugml's disc to be balanced,
 gyxo, ebii, and jptl must all have the same weight, and they do: 61.

 However, for tknk to be balanced, each of the programs standing on its disc and all
 programs above it must each match.

 This means that the following sums must all be the same:

 ugml + (gyxo + ebii + jptl) = 68 + (61 + 61 + 61) = 251
 padx + (pbga + havc + qoyq) = 45 + (66 + 66 + 66) = 243
 fwft + (ktlj + cntj + xhth) = 72 + (57 + 57 + 57) = 243

 As you can see, tknk's disc is unbalanced: ugml's stack is heavier than the other two.
 Even though the nodes above ugml are balanced, ugml itself is too heavy:
 it needs to be 8 units lighter for its stack to weigh 243 and keep the towers balanced.
 If this change were made, its weight would be 60.

 Given that exactly one program is the wrong weight, what would its weight need to be to balance the entire tower?

 The correct answer is 1505 (program apjxafk) using puzzle7.dat
  */
public class Puzzle7AB extends PuzzleBase {
    protected boolean DataIsValid(ArrayList<String> inputData) {
        return ValidateMultiLineDataFile(inputData);
    }

    protected int SolveMe(ArrayList<String> inputData) {
        HashMap<String, Program> programs = new HashMap<String, Program>(inputData.size());

        System.out.print("Loading programs ... ");
        for (String line : inputData) {
            Program p = new Program(line);
            programs.put(p.get_name(), p);
        }

        setParentForChildren(programs);
        System.out.println("done!");

        System.out.print("Finding root node .. ");
        Program root = findRootNode(programs);
        System.out.println(String.format("The name of the root node is %s", root != null ? root.get_name() : " NO ROOT FOUND!"));

        if (root != null) {
            root.calculateChildrenWeightBalanced(programs);
        }

        return 0;
    }

    private Program findRootNode(HashMap<String, Program> programs) {
        Iterator<Program> it = programs.values().iterator();
        while(it.hasNext()) {
            Program p = it.next();
            if (p.get_parent() == null || p.get_parent().isEmpty()) {
                return p;
            }
        }
        return null;
    }

    private void setParentForChildren(HashMap<String, Program> programs) {
        for (String pid : programs.keySet()) {
            Program parent = programs.get(pid);

            if (!parent.hasChildren()) { continue; }

            for (String childName : parent.get_children()) {
                Program child = programs.get(childName);
                child.set_parent(parent.get_name());
            }
        }
    }

    private class Program
    {
        private String _name;
        private Integer _weight = 0;
        private String _parent;
        private Boolean      _childrenAreBalanced = true;
        private List<String> _children            = new ArrayList<String>();
        private Integer      _totalChildrenWeight = 0;
        private Integer      _balancingWeight     = 0;

        public Program(String initializer) throws IllegalArgumentException {
            if (initializer == null || initializer.isEmpty()) {
                throw new IllegalArgumentException("The initializer string cannot be empty.");
            }
            // Sample initializer string
            // fwft (72) -> ktlj, cntj, xhth
            String p = initializer;
            String w = "";

            if (initializer.contains("->")) {
                String[] s = initializer.split("->");
                p = s[0];
                for (String child : s[1].split(",")) {
                    addChild(child.trim());
                }
            }

            String[] s = p.split("\\(");
            set_name(s[0].trim());
            set_weight(Integer.parseInt(s[1].replace(")", "").trim()));
        }

        public String get_name() { return _name;}
        public void set_name(String name) { _name = name;}

        public Integer get_weight() { return _weight; }
        public void set_weight(Integer weight) { _weight = weight; }

        public String get_parent() { return _parent; }
        public void set_parent(String parent) { _parent = parent; }

        public Boolean hasChildren() { return _children.size() > 0; }
        public List<String> get_children() { return _children; }

        public Boolean get_childrenAreBalanced() { return _childrenAreBalanced; }
        public void set_childrenAreBalanced(Boolean balanced) { _childrenAreBalanced = balanced; }

        public Integer get_totalChildrenWeight() { return _totalChildrenWeight; }

        public Integer get_balancingWeight() { return _balancingWeight; }
        public void set_balancingWeight(Integer difference) { _balancingWeight = _weight + difference; }

        public Integer get_totalWeight() { return get_weight() + get_totalChildrenWeight(); }

        public void addChild(String childName) {
            if (childName == null || childName.isEmpty())
                return;

            _children.add(childName);
        }

        private void calculateChildrenWeightBalanced(Map<String, Program> programs) {
            Map<Integer, List<String>> weights = new HashMap<Integer, List<String>>();

            int lastWeight = 0;
            for (String childName : get_children()) {
                Program child = programs.get(childName);
                child.calculateChildrenWeightBalanced(programs);

                Integer weight = child.get_totalWeight();
                _totalChildrenWeight += weight;
                if (!weights.containsKey(weight)) {
                    weights.put(weight, new ArrayList<String>());
                }
                weights.get(weight).add(child.get_name());

                if (lastWeight > 0) {
                    set_childrenAreBalanced(get_childrenAreBalanced() && lastWeight == weight);
                }
                lastWeight = weight;
            }

            if (get_childrenAreBalanced()) {
                return;
            }

            System.out.println(String.format("One of my children is not balanced! says %s", get_name()));

            Iterator<Integer> it = weights.keySet().iterator();
            Integer prev = 0;
            while(it.hasNext()) {
                Integer totalWeight = it.next();
                if (weights.get(totalWeight).size() == 1) {
                    if (prev == 0) {
                        prev = it.hasNext() ? it.next() : 0;
                    }
                    Program tp = programs.get(weights.get(totalWeight).get(0));

                    Integer tw = tp.get_weight() - (totalWeight - prev);

                    //System.out.println(String.format("Prog %s tw = %d, prev = %d w = %d", tp.get_name(), tp.get_totalWeight(), prev, tp.get_weight()));

                    System.out.println(String.format("The program %s needs it's weight to be %d", tp.get_name(), tw));
                    break;
                }
                prev = totalWeight;
            }
        }
    }
}

