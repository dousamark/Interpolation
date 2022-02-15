
public class Points {
    public Point[][] field = new Point[Global.ScreenWidth][Global.ScreenHeight];

    public Points(){
        for (int i = 0; i < Global.ScreenWidth; i++) {
            for (int j = 0; j <Global.ScreenHeight; j++) {
                field[i][j]= new Point(i,j);
            }
        }
    }
}
