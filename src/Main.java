import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    gameBoard board;
    List<Ball> tempBall;


    private int getInput(){
        Scanner sc = new Scanner(System.in);
        int input = -1;
        do {
            try {
                input = Integer.parseInt(sc.nextLine());
            }
            catch (Exception e) {
                System.out.println("Enter an integer");
            }
        }
        while(input < 0);
        return input;
    }


    private int randRow(){
        Random rand = new Random();
        int row =rand.nextInt(board.filled);
        return row;
    }

    private int randCol(){
        Random rand = new Random();
        int col = rand.nextInt(board.height);
        return col;
    }

    private Ball randBall(){
        Random rand = new Random();
        Ball b = tempBall.get(rand.nextInt(tempBall.size()));
        tempBall.remove(b);
        return b;
    }


    private void addBalls(){
        int colors = board.filled;
        int ballsPerTower = board.height;
        tempBall = new ArrayList<>();

        for(int i=0;i<colors;i++){
            for(int j=0;j<ballsPerTower;j++){
                Ball b = new Ball(i);
                board.balls.add(b);
                tempBall.add(b);
            }
        }
        for(int i=0;i<board.filled;i++){
            for(int j=0;j<board.height;j++) {
                Ball b = randBall();
                board.boardState[i][j] = b;
                b.r = j;
                b.c = i;
            }
            }

    }


    private void buildBoard(String[] args){
        int towers = -1;
        int height = -1;
        int filled = -1;

        if(args.length != 3) {
            System.out.println("Command line arguments not supplied, taking user input");


            do {
                System.out.println("Enter value for:\n# of towers (4-12)");
                towers = getInput();
            }
            while (towers < 3 || towers > 12);

            do {
                System.out.println("Enter value for:\nheight of towers (4-8)");
                height = getInput();
            }
            while (height < 4 || height > 8);

            do {
                System.out.println("Enter value for:\n# of filled towers (2-" + (towers - 2) + ")");
                filled = getInput();
            }
            while (filled < 2 || filled > (towers - 2));

        }

        else{
            towers = Integer.parseInt(args[0]);
            if(towers < 3 || towers > 12){
                System.out.println("Enter value for:\n# of towers (4-12)");
                System.exit(0);
            }
            height = Integer.parseInt(args[1]);
            if(height < 4 || height > 8){
                System.out.println("Enter value for:\nheight of towers (4-8)");
                System.exit(0);
            }
            filled = Integer.parseInt(args[2]);
            if(filled < 2 || filled > (towers - 2)){
                System.out.println("Enter value for:\n# of filled towers (2-" + (towers - 2) + ")");
                System.exit(0);
            }
        }

        board = new gameBoard(towers, height, filled);
    }


    public static void main(String[] args){
        Main m = new Main();
        m.buildBoard(args);
        m.addBalls();
        System.out.println(m.board);

        Solver s = new Solver(m.board);

        s.solveIt();


    }
}
