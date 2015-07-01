package com.example.android.npuzzle;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * n-puzzle board class
 * each board serves as a search node.
 */
public class BoardNode implements Comparable<BoardNode>, Comparator<BoardNode> {
    private int [][] blocks;
    private int dim = 0;
    private int EMPTY = 0;
    public int numMoves = 0;// TODO: change back to private
    private BoardNode prevBoardNode = null;

    public BoardNode(int[][] blocks) {
        this.blocks = blocks;
        this.dim = this.blocks.length;
        this.EMPTY = dim * dim - 1;
    }

    public void setPrevBoardNode(BoardNode prevBoardNode) {
        this.prevBoardNode = prevBoardNode;
    }

    public BoardNode getPrevBoardNode() {
        return prevBoardNode;
    }

    public void setMove(int move) {
        this.numMoves = move;
    }

    public int getDimension() {
        return this.dim;
    }

    public int getHammingPriority() {
        int hamming = 0;
        for (int i = 0; i < this.dim; i++){
            for (int j = 0; j < this.dim; j++) {
                int curr = blocks[i][j];
                // Check if the curr block is the empty block
                if (curr != EMPTY) {
                    int tgt = i * dim + j;
                    if (curr != tgt) {
                        hamming++;
                    }
                }
            }
        }
        return hamming + numMoves;
    }

    public int getManhattanPriority(){
        int manhattan = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                // check if the curr block is the empty block
                if (blocks[i][j] != EMPTY) {
                    int currRow = blocks[i][j] / dim;
                    int currCol = blocks[i][j] % dim;
                    int currManhattan = Math.abs(currRow - i) + Math.abs(currCol - j);
                    manhattan += currManhattan;
                }
            }
        }
        return manhattan + numMoves;
    }

    public boolean isGoal() {
        for (int i = 0; i < dim; i++){
            for (int j = 0; j < dim; j++) {
                int index = i * dim + j;
                // goal has same value and index
                if (blocks[i][j] != index) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * function related to detect unsolvable puzzle
     * @return
     */
    public BoardNode twin() {
        return null;
    }

    public Iterable<BoardNode> getNeighbors() {
        ArrayList<BoardNode> neighbors = new ArrayList<BoardNode>();
        BoardNode neighbor;
        // find where is the EMPTY block
        int emptyRow = 0;
        int emptyCol = 0;
        for(int i = 0; i < dim; i++){
            for (int j = 0; j < dim; j++){
                if (blocks[i][j] == EMPTY) {
                    emptyRow = i;
                    emptyCol = j;
                }
            }
        }
        // try to find neighbors in all four directions
        // System.out.println(blocks[2][2]);
        int[][] leftNeighbor = new int[dim][dim];
        int[][] rightNeighbor = new int[dim][dim];
        int[][] upNeighbor = new int[dim][dim];
        int[][] downNeighbor = new int[dim][dim];
        for(int i = 0; i < dim; i++)
        {
            for (int j = 0; j < dim; j++)
            {
                leftNeighbor[i][j] = blocks[i][j];
                rightNeighbor[i][j] = blocks[i][j];
                upNeighbor[i][j] = blocks[i][j];
                downNeighbor[i][j] = blocks[i][j];
            }
        }
        // left
        if (emptyCol > 0) {
            leftNeighbor[emptyRow][emptyCol] = leftNeighbor[emptyRow][emptyCol-1];
            leftNeighbor[emptyRow][emptyCol-1] = EMPTY;
            neighbor = new BoardNode(leftNeighbor);
            neighbors.add(neighbor);
        }
        // right
        if (emptyCol < dim - 1) {
            rightNeighbor[emptyRow][emptyCol] = rightNeighbor[emptyRow][emptyCol+1];
            rightNeighbor[emptyRow][emptyCol+1] = EMPTY;
            neighbor = new BoardNode(rightNeighbor);
            neighbors.add(neighbor);
        }
        // up
        if (emptyRow > 0) {
            upNeighbor[emptyRow][emptyCol] = upNeighbor[emptyRow-1][emptyCol];
            upNeighbor[emptyRow-1][emptyCol] = EMPTY;
            neighbor = new BoardNode(upNeighbor);
            neighbors.add(neighbor);
        }
        // down
        if (emptyRow < dim - 1) {
            downNeighbor[emptyRow][emptyCol] = downNeighbor[emptyRow+1][emptyCol];
            downNeighbor[emptyRow+1][emptyCol] = EMPTY;
            neighbor = new BoardNode(downNeighbor);
            neighbors.add(neighbor);
        }
        return neighbors; // arrayList is an iterable
    }

    public String toString() {
        String boardString = "";
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                boardString += Integer.toString(blocks[i][j]) + " ";
            }
            boardString += "\n";
        }
        return boardString;
    }

    @Override
    public  int compareTo(BoardNode otherBoardNode)
    {
        return compare(this, otherBoardNode);
    }

    @Override
    public boolean equals (Object other) {
        /* In Java, the equals() method that is inherited from Object is:
            public boolean equals(Object other);
        In other words, the parameter must be of type Object.*/
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (!(other instanceof BoardNode))
        {
            return false;
        }
        BoardNode otherBoardNode = (BoardNode) other;
        if (dim != otherBoardNode.dim)
        {
            return false;
        }
        for (int i = 0; i < dim; i ++) {
            for (int j = 0; j < dim; j++) {
                if (blocks[i][j] != otherBoardNode.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int compare(BoardNode boardNode1, BoardNode boardNode2) {
        // use manhattanPriority as optimization strategy
        int priority1 = boardNode1.getManhattanPriority();
        int priority2 = boardNode2.getManhattanPriority();
        // smaller priority value means higher priority
        if (priority1 == priority2) {
            return boardNode2.numMoves - boardNode1.numMoves; // TODO: BUGS
        }
        return priority1 - priority2; // TODO: BUGS
    }
}


