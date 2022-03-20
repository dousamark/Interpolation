public class Interpolation {
    public static int[] interpolate() {
        int[] funcPoints = new int[Helper.ScreenWidth];

        Matrix matrix = new Matrix(Helper.interpolPoints.size());
        matrix.fillMatrix(Helper.interpolPoints);
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
}
