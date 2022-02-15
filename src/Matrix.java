import java.util.List;

public class Matrix {
    double[][] matrix;
    double[] solution;
    int size;
    private static final double EPSILON = 1e-10;
    
    public Matrix(int size){
        this.size = size;
        matrix = new double[size][size];
        solution = new double[size];
    }

    public void fillMatrix(List<Point> interpolPoints) {
        int line = 0;
        for (Point point: interpolPoints) {
            for (int row = 0; row < size; row++) {
                //vyplnuji zezadu tedy x^3 + x^2 + x^1 + c = d
                matrix[line][size - row-1]= (double) Math.pow(point.x,row);
            }
            solution[line]=(long)point.y;
            line++;
        }
    }

    public double[] solve() {
        int n = solution.length;

        for (int p = 0; p < n; p++) {

            // find pivot row and swap
            int max = p;
            for (int i = p + 1; i < n; i++) {
                if (Math.abs(matrix[i][p]) > Math.abs(matrix[max][p])) {
                    max = i;
                }
            }
            double[] temp = matrix[p]; matrix[p] = matrix[max]; matrix[max] = temp;
            double   t    = solution[p]; solution[p] = solution[max]; solution[max] = t;

            // singular or nearly singular
            if (Math.abs(matrix[p][p]) <= EPSILON) {
                throw new ArithmeticException("Matrix is singular or nearly singular");
            }

            // pivot within A and b
            for (int i = p + 1; i < n; i++) {
                double alpha = matrix[i][p] / matrix[p][p];
                solution[i] -= alpha * solution[p];
                for (int j = p; j < n; j++) {
                    matrix[i][j] -= alpha * matrix[p][j];
                }
            }
        }

        // back substitution
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += matrix[i][j] * x[j];
            }
            x[i] = (solution[i] - sum) / matrix[i][i];
        }
        return x;
    }
}
