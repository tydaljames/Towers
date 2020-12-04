import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solver {
    gameBoard board;
    List<Integer> unsolvedColors;
    List<Integer> solvedColors;
    List<Ball> moveList;
    List<Integer> towerList;
    List<Integer> solvedTowers;
    int nextColor = -1;
    int nextTower = -1;

    public Solver(gameBoard board) {
        this.board = board;
    }


    private int towerHeight(int tower) {

        for (int j = 0; j < board.height; j++) {
            if (board.boardState[tower][j] == null) {

                return j;
            }
        }

        return board.height; //if tower is full
    }

    public Ball getBall(int row, int col) {
        for (int k = 0; k < board.balls.size(); k++) {
            Ball b = board.balls.get(k);
            if (row == b.r && col == b.c) {
                return b;
            }
        }
        return null;
    }

    private Ball topBall(int tower) {
        for (int j = (board.height - 1); j > -1; --j) {
            if (board.boardState[tower][j] != null) {
                return board.boardState[tower][j];
            }
        }
        return null;
    }


    private void shiftBall(Ball b, int endTower) {
        int c = b.c;
        int r = b.r;

        if (towerHeight(endTower) == board.height) {
            System.out.println("Tower full, can't move");
            return;
        } else {
            board.boardState[endTower][towerHeight(endTower)] = b;
            board.boardState[b.c][b.r] = null;

            b.c = endTower;
            b.r = towerHeight(endTower) - 1;
        }

        System.out.println("Ball " + b + " moved from: " + c + "," + r + " to: " + b.c + "," + b.r);

    }

    private boolean towerSolved(int tower) {
        Ball b = board.boardState[tower][0];
        if (b == null) {
            return false;
        }
        int color = b.color;
        for (int j = 1; j < board.height; j++) {
            b = board.boardState[tower][j];

            if (b == null || b.color != color) {
                return false;
            }
        }
        return true;
    }

    private List<Integer> buildUnsolved() {
        unsolvedColors = new ArrayList<>();

        for (Ball b : board.balls) {
            if (!unsolvedColors.contains(b.color)) {
                if (!solvedColors.contains(b.color)) {
                    unsolvedColors.add(b.color);
                }
            }
        }
        return unsolvedColors;
    }

    private int findNextSolution() {
        Map<Integer, Integer> weight = new HashMap();
        int count = 0;
        int cost = 0;
        int lowest = Integer.MAX_VALUE;
        int returnColor = -1;
        int curWeight = -1;
        Ball b;

        for (Integer color : unsolvedColors) {
            //System.out.println("Remaining: " + color);


            for (int j = 0; j < board.height; j++) {


                for (int i = 0; i < board.towers; i++) {
                    if (count == board.height) {
                        weight.put(color, cost);
                        //System.out.println(color + ", " + cost);
                        count = 0;
                        cost = 0;
                    }
                    if (towerHeight(i) - j > 0) {
                        b = board.boardState[i][towerHeight(i) - 1 - j];
                        //System.out.println(b + ", " + i + ", " + b.r);

                        if (b.color == color) {
                            count++;
                            cost += towerHeight(i) - b.r - 1;
                            //System.out.println(cost);
                        }
                    }
                }
            }
        }

        //for(int k=0;k<weight.size();k++) {


        for (Integer k : unsolvedColors) {
            if (weight.get(k) != null) {
                curWeight = weight.get(k);
            }

            if (curWeight < lowest) {
                returnColor = k;
                lowest = curWeight;
            }
        }


        // }
        //System.out.println("Next solution: " + returnColor);
        return returnColor;
    }

    private int findNextTower() {
        for (int j = 0; j < board.height; j++) {
            for (int i = 0; i < board.towers; i++) {
                if (board.boardState[i][j] != null) {
                    if (board.boardState[i][j].color == nextColor) {
                        for (int k = 0; k < towerHeight(i); k++) {
                            if (board.boardState[i][k].color == nextColor) {
                                if (k == towerHeight(i)) {
                                    return i;
                                }
                            }
                        }

                    }
                } else if (board.boardState[i][j] == null) {
                    return i;
                }
            }
        }
        return -1;
    }


    private void findAllColor() {
        moveList = new ArrayList<>();

        for (int i = board.height - 1; i > -1; --i) {
            for (int j = 0; j < board.towers; j++) {
                Ball b = board.boardState[j][i];
                if (b != null) {

                    //System.out.println(j + " " + i + " " + b);

                    if (b.color == nextColor) {
                        moveList.add(b);
                    }

                }

            }
        }
    }

    private void findTowers() {
        towerList = new ArrayList<>();

        towerList.add(nextTower);
        for (int tower = 0; tower < board.towers; tower++) {
            if (solvedTowers.contains(tower)) {
                towerList.add(tower);
            }


            for (Ball b : moveList) {
                if (b.c == tower) {
                    towerList.add(tower);

                }
            }
        }


    }

    private boolean checkTrapped(int tower) {
        for (Ball b : moveList) {
            if (b.c == tower) {

                return true;
            }
        }

        return false;
    }

    private int nextOpenTower() {

        for (int j = board.height - 1; j > -1; --j) {
            for (int i = 0; i < board.towers; i++) {
                if (board.boardState[i][j] == null && !towerList.contains(i)) {
                    return i;
                }

            }
        }

        return -1;
    }

    private int nextTowerRegardless() {
        for (int j = board.height - 1; j > -1; --j) {
            for (int i = 0; i < board.towers; i++) {
                if (board.boardState[i][j] == null) {
                    return i;
                }

            }
        }

        return -1;
    }




        private void freeThem(){

            for(Ball b : moveList){
                int tower = b.c;
                int height = b.r;

                for(int j=towerHeight(tower)-1;j>height;--j) {
                    Ball move = topBall(tower);
                    if(move.color != b.color) {
                        shiftBall(move, nextOpenTower());
                    }
                    else{
                        shiftBall(move, nextTower);
                    }
                }
                shiftBall(b, nextTower);
            }

        }

        private void takeTwo(){
            for(Ball b : moveList){
                int tower = b.c;
                int height = b.r;

                for(int j=towerHeight(tower)-1;j>height;--j) {
                    Ball move = topBall(tower);
                    if(move.color != b.color) {
                        shiftBall(move, nextTowerRegardless());
                    }
                    else{
                        shiftBall(move, nextTower);
                    }
                }
                shiftBall(b, nextTower);
            }


        }



    public void solveIt() {
        solvedTowers = new ArrayList<>();
        solvedColors = new ArrayList<>();


        int color;


        while (solvedTowers.size() < board.filled) {
            unsolvedColors = buildUnsolved();

            nextColor = findNextSolution();
            nextTower = findNextTower();
            findAllColor();
            findTowers();
            //System.out.println("Next color is: " + nextColor);
            freeThem();
            System.out.println(board);

            //System.out.println("Tower solved? : " +  towerSolved(nextTower));



            if (towerSolved(nextTower)) {
                solvedTowers.add(nextTower);
                solvedColors.add(nextColor);
            }


        }

    }
    }


