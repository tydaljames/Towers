import java.awt.desktop.ScreenSleepEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class gameBoard {
    public int towers;
    public int height;
    public int filled;

    public List<Ball> balls;
    public Ball[][] boardState;


    public boolean checkFilled(int r, int c){
        if(boardState[r][c] == null){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public String toString() {
        String output = "";

        for(int i=(height-1);i>-1;--i){
            output += "| ";
            for(int j=0;j<towers;j++){

                if(boardState[j][i] != null) {
                    output += boardState[j][i] + " | ";
                }
                else{
                    output += "  | ";
                }
            }
            output += "\n";
        }

        return output;
    }

    public gameBoard(int towers, int height, int filled){
        balls = new ArrayList<Ball>();
        this.towers = towers;
        this.height = height;
        this.filled = filled;

        boardState = new Ball[towers][height];
        for(int i=0;i<towers;i++){
            for(int j=0;j<height;j++){
                    boardState[i][j] = null;

            }
        }
    }


}
