public class Point {
    public int x;
    public int y;
    public boolean set;
    public String ID = "default";

    public Point(int x, int y){
        this.x = x;
        this.y = y;
        this.set = false;
    }
    public Point(int x, int y, String ID){
        this.x = x;
        this.y = y;
        this.set = false;
        this.ID = ID;
    }

    @Override
    public String toString(){
        //inverted Y axis
        return "Point at [" + x+","+(650-y)+"]";
    }
}

