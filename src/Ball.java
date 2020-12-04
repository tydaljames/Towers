public class Ball {
    int color;
    int r = -1; //row
    int c = -1; //column


    @Override
    public String toString() {
        return "" + color;
    }

    public Ball(int color){
        this.color = color;
    }

}
