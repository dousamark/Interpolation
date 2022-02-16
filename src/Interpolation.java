public class Interpolation {

    public static int[] interpolate() {
        int[] funcPoints = new int[Global.ScreenWidth];

        Matrix matrix = new Matrix(Global.interpolPoints.size());
        matrix.fillMatrix(Global.interpolPoints);
        double[] result = matrix.solve();

        for (int i = 0; i < funcPoints.length; i++) {
            int sum =0;
            for (int j = 0; j < result.length; j++) {
                sum += result[j]*Math.pow(i,result.length-j-1);
            }
            funcPoints[i]=sum;
        }

        return funcPoints;
    }
}
