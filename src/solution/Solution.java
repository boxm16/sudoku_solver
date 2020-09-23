/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solution;

import java.math.BigInteger;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.HashSet;


/**
 *
 * @author Michail Sitmalidis
 */
public class Solution {

    public static void main(String[] args) {
        Solution_1 s_1 = new Solution_1();
       

        char[][] board = {
            {'.', '.', '.', '.', '.', '8', '.', '.', '.'},
            {'7', '.', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '2', '.', '1', '.', '9', '.', '.', '.'},
            {'.', '.', '7', '.', '.', '.', '2', '4', '.'},
            {'.', '6', '4', '.', '1', '.', '5', '9', '.'},
            {'.', '9', '8', '.', '.', '.', '3', '.', '.'},
            {'.', '.', '.', '8', '.', '3', '.', '2', '.'},
            {'.', '.', '.', '.', '.', '.', '.', '.', '6'},
            {'.', '.', '.', '2', '7', '5', '9', '.', '.'}
        };
        s_1.solveSudoku(board);

    }

    public static class Solution_1 {

        char[][] board;
        char[][] initialNumbersBoard = new char[9][9];
        int establishedNumbersCount = 0;
        HashMap<Integer, HashSet<Character>> blocksMap = new HashMap();
        ArrayList<int[]> blockPoints = new ArrayList();

        public void solveSudoku(char[][] board) {
            this.board = board;
            mapBlockPoints();
            initialScan();
            int[] startingPoint = getStartingPoint();
            backtrack(startingPoint, '1');

        }

        public int[] getStartingPoint() {
            int x = 0;
            int y = 0;
            int[] point = new int[2];
            outerloop:
            while (x < 9) {
                while (y < 9) {
                    if (board[x][y] == '.') {

                        break outerloop;
                    }
                    y++;
                }
                x++;
            }
            point[0] = x;
            point[1] = y;
            return point;
        }

        public void backtrack(int[] point, char number) {
          while(true){
            if (numberChecks(number, point)) {

                establishedNumbersCount++;
                board[point[0]][point[1]] = number;//saving number in board
                int blockID = findBlockID(point);
                HashSet<Character> set = blocksMap.get(blockID);
                set.add(number);
                   System.out.println("Establishing new Number: " + number + " at:" + point[0] + ":" + point[1]);
                
                number = '1';
                if (establishedNumbersCount == 9 * 9) {
                   break;
                }
                point = getNextPoint(point);

            } else {
                if (number == '9') {
                    //returning to the previous state
                    establishedNumbersCount--;
                    board[point[0]][point[1]] = '.';
                    point = getPreviousPoint(point);
                    number = board[point[0]][point[1]];
            
                       System.out.println("Returning Back to:" + point[0] + ":" + point[1]);
                    
                    int blockID = findBlockID(point);
                    HashSet<Character> set = blocksMap.get(blockID);
                    set.remove(number);

                } else {
                    number += 1;

                }
            }
          }
            System.out.println(establishedNumbersCount);
            //backtrack(point, number);
        }

        public int[] getNextPoint(int[] point) {
            int x = point[0];
            int y = point[1];
            if (y == 8) {
                x++;
                y = 0;
            } else {
                y++;
            }
            point[0] = x;
            point[1] = y;
            if (initialNumbersBoard[x][y] != '.') {

                point = getNextPoint(point);
            }

            return point;
        }

        public int[] getPreviousPoint(int[] point) {
            int x = point[0];
            int y = point[1];

            if (y == 0) {
                x--;
                y = 8;
            } else {
                y--;
            }
            point[0] = x;
            point[1] = y;
            if (initialNumbersBoard[x][y] != '.') {

                point = getPreviousPoint(point);
            }
            return point;
        }

        public boolean numberChecks(char number, int[] point) {
            int blockID = findBlockID(point);
            HashSet<Character> establishedBlockNumbers = this.blocksMap.get(blockID);
            if (establishedBlockNumbers.contains(number)) {
                return false;
            }
            char establishedSideNumber;
            for (int y = 0; y < 9; y++) {
                establishedSideNumber = board[point[0]][y];
                if (establishedSideNumber == number) {
                    return false;
                }
            }
            for (int x = 0; x < 9; x++) {
                establishedSideNumber = board[x][point[1]];
                if (establishedSideNumber == number) {
                    return false;
                }
            }

            return true;
        }

        public int findBlockID(int[] point) {
            int x = point[0];
            int y = point[1];
            if (x >= 0 && x <= 2 && y >= 0 && y <= 2) {
                return 1;
            }
            if (x >= 0 && x <= 2 && y >= 3 && y <= 5) {
                return 2;
            }
            if (x >= 0 && x <= 2 && y >= 6 && y <= 8) {
                return 3;
            }
            if (x >= 3 && x <= 5 && y >= 0 && y <= 2) {
                return 4;
            }
            if (x >= 3 && x <= 5 && y >= 3 && y <= 5) {
                return 5;
            }
            if (x >= 3 && x <= 5 && y >= 6 && y <= 8) {
                return 6;
            }
            if (x >= 6 && x <= 8 && y >= 0 && y <= 2) {
                return 7;
            }
            if (x >= 6 && x <= 8 && y >= 2 && y <= 5) {
                return 8;
            }
            if (x >= 6 && x <= 8 && y >= 6 && y <= 8) {
                return 9;
            }
            return 0;
        }

        public void mapBlockPoints() {

            int[] point1 = {-1, -1};
            int[] point2 = {0, -1};
            int[] point3 = {1, -1};
            int[] point4 = {-1, 0};
            int[] point5 = {0, 0};
            int[] point6 = {1, 0};
            int[] point7 = {-1, 1};
            int[] point8 = {0, 1};
            int[] point9 = {1, 1};
            blockPoints.add(point1);
            blockPoints.add(point2);
            blockPoints.add(point3);
            blockPoints.add(point4);
            blockPoints.add(point5);
            blockPoints.add(point6);
            blockPoints.add(point7);
            blockPoints.add(point8);
            blockPoints.add(point9);

        }

        public void initialScan() {
            int X = 1;
            int Y = 1;
            int blockID = 0;
            while (X < 9) {
                Y = 1;
                while (Y < 9) {

                    blockID++;
                    HashSet<Character> initialBlockNumbers = getInitialBlockNumbers(X, Y);
                    blocksMap.put(blockID, initialBlockNumbers);
                    establishedNumbersCount += initialBlockNumbers.size();
                    Y += 3;
                }

                X += 3;
            }
        }

        public HashSet<Character> getInitialBlockNumbers(int X, int Y) {
            HashSet<Character> initialBlockNumbers = new HashSet();
            int x;
            int y;
            char c;
            for (int[] point : blockPoints) {
                x = point[0] + X;
                y = point[1] + Y;
                c = board[x][y];
                if (c != '.') {
                    initialBlockNumbers.add(c);
                    initialNumbersBoard[x][y] = c;
                } else {
                    initialNumbersBoard[x][y] = '.';
                }
            }
            return initialBlockNumbers;
        }

    }

//--------//-----------//-----------//-----------------//-----------
    public void solveSudoku1(char[][] board) {

        int counter;
        int x = 0;
        int y = 0;
        outerloop:
        while (true) {
            int X = 1;
            int Y = 1;
            counter = 0;
            while (X < 9) {

                Y = 1;
                while (Y < 9) {
                    counter += establishedNumbersInBlock(board, X, Y);
                    System.out.println("counter:" + counter);
                    if (counter == 9 * 9) {
                        break outerloop;
                    }
                    Y = Y + 3;
                }
                X = X + 3;
            }

        }
    }

    public int establishedNumbersInBlock(char[][] board, int X, int Y) {
        ArrayList<int[]> points = new ArrayList();
        int[] point1 = {-1, -1};
        int[] point2 = {0, -1};
        int[] point3 = {1, -1};
        int[] point4 = {-1, 0};
        int[] point5 = {0, 0};
        int[] point6 = {1, 0};
        int[] point7 = {-1, 1};
        int[] point8 = {0, 1};
        int[] point9 = {1, 1};
        points.add(point1);
        points.add(point2);
        points.add(point3);
        points.add(point4);
        points.add(point5);
        points.add(point6);
        points.add(point7);
        points.add(point8);
        points.add(point9);
        HashSet<Character> set = new HashSet();
        //
        for (int[] point : points) {
            int xC = point[0] + X;
            int yC = point[1] + Y;
            char c = board[xC][yC];
            if (c != '.') {
                if (set.contains(c)) {
//do nothing
                } else {
                    set.add(c);
                }
            }
        }

        //
        for (int[] point : points) {

            int xC = point[0] + X;
            int yC = point[1] + Y;
            char c = board[xC][yC];
            if (c == '.') {

                establishCellNumber(board, set, xC, yC);
            } else {

            }
        }

        return set.size();
    }

    public void establishCellNumber(char[][] board, HashSet<Character> set, int xC, int yC) {
        HashSet<Character> sideSet = new HashSet();
        sideSet.addAll(set);
        sideSet = checkRow(board, sideSet, xC);
        sideSet = checkColumn(board, sideSet, yC);
        System.out.println("sideSet size" + sideSet.size());
        if (sideSet.size() == 8) {//finding number
            char number = '1';
            while (number <= '9') {
                if (!sideSet.contains(number)) {
                    System.out.println("FOUND NUMBER " + number + " IN--" + xC + ":" + yC);
                    board[xC][yC] = number;
                    set.add(number);
                    break;
                }
                number++;
            }
        }

    }

    public HashSet checkRow(char[][] board, HashSet<Character> sideSet, int xC) {

        for (int y = 0; y < 9; y++) {
            char c = board[xC][y];
            if (c != '.') {
                sideSet.add(c);
            }
        }

        return sideSet;
    }

    public HashSet checkColumn(char[][] board, HashSet<Character> sideSet, int yC) {

        for (int x = 0; x < 9; x++) {
            char c = board[x][yC];
            if (c != '.') {
                sideSet.add(c);
            }
        }

        return sideSet;
    }

    public static class ListNode {

        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
