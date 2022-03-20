public class Points {
    public Point[][] field = new Point[Helper.ScreenWidth][Helper.ScreenHeight];

    public Points(){
        for (int i = 0; i < Helper.ScreenWidth; i++) {
            for (int j = 0; j <Helper.ScreenHeight; j++) {
                field[i][j]= new Point(i,j);
            }
        }
    }
}
