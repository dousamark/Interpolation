public class Points {
    public Point[][] field;


    public Points(int screenWidth, int screenHeight){
        field = new Point[screenWidth][screenHeight];
        for (int i = 0; i < screenWidth; i++) {
            for (int j = 0; j < screenHeight; j++) {
                field[i][j]= new Point(i,j);
            }
        }
    }
}
