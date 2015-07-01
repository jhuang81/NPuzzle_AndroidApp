package com.example.android.npuzzle;

import android.util.Log;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * n-puzzle solver
 */
public class Solver {
    private int move = 0;
    private ArrayList<BoardNode> solution = new ArrayList<BoardNode>();

    public Solver(BoardNode initial) {
        Log.i("Solver", "starts");
        PriorityQueue<BoardNode> priorityQueue = new PriorityQueue<BoardNode>();
        // enqueue
        priorityQueue.add(initial);
        // looping
        while (!priorityQueue.peek().isGoal() && // the puzzle is solved
                priorityQueue.peek() != null) { // the puzzle is not solvable
            BoardNode front = priorityQueue.poll(); // remove the board with smallest priority value
            move ++;
            Iterable<BoardNode> neighborsNodes = front.getNeighbors();
            for (BoardNode node: neighborsNodes) {
                // do not enqueue neighbor that already explored
                if (front.getPrevBoardNode() != node) {
                    // update node before enqueue
                    node.setMove(move);
                    node.setPrevBoardNode(front);
                    // enqueue node
                    priorityQueue.add(node);
                }
            }
        }
        BoardNode goal = priorityQueue.peek();
        while (goal != null) {
            solution.add(0, goal); // insert at front
            goal = goal.getPrevBoardNode();
        }
    }


    public boolean isSolvable() {
        return true;
    }

    /**
     *  min number of moves to solve initial board;
     *  -1 if unsolvable
     * @return int
     */
    public int moves() {
        if (solution.size() == 0) {
            return -1;
        }
        return solution.size();
    }

    public Iterable<BoardNode> solution() {
        return solution;
    }

}
