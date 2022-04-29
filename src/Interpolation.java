import java.util.List;

public class Interpolation {
    int screenWidth;
    int screenHeight;
    DataContainer dataContainer;
    public Interpolation(int screenWidth, int screenHeight, DataContainer dataContainer){
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.dataContainer = dataContainer;
    }
    public int[] interpolate() {
        int[] funcPoints = new int[screenWidth];

        Matrix matrix = new Matrix(dataContainer.interpolPoints.size());
        matrix.fillMatrix(dataContainer.interpolPoints);
        double[] result = matrix.solve();

        if(result== null){
            return null;
        }
        for (int i = 0; i < funcPoints.length; i++) {
            int sum =0;
            for (int j = 0; j < result.length; j++) {
                sum += result[j]*Math.pow(i,result.length-j-1);
            }
            funcPoints[i]=sum;
        }

        return funcPoints;
    }

    public boolean canInterpolate(int x, int y) {
        Matrix matrix = new Matrix(dataContainer.interpolPoints.size()+1);

        Point testingPoint = new Point(x,y);
        dataContainer.interpolPoints.add(testingPoint);
        matrix.fillMatrix(dataContainer.interpolPoints);
        double[] result = matrix.solve();
        dataContainer.interpolPoints.remove(testingPoint);

        if(result== null){
            return false;
        }

        return true;
    }
}
