import java.util.List;

public class Matrix {
    double[][] matrix;
    double[] solution;
    int size;
    double norm = 1e-10;
    
    public Matrix(int size){
        this.size = size;
        matrix = new double[size][size];
        solution = new double[size];
    }

    public void fillMatrix(List<Point> interpolPoints) {
        int line = 0;
        for (Point point: interpolPoints) {
            for (int row = 0; row < size; row++) {
                //filled from back, example: x^3 + x^2 + x^1 + c = d
                matrix[line][size - row-1]= Math.pow(point.x,row);
            }
            solution[line]=(long)point.y;
            line++;
        }
    }

    public double[] solve() {
        for (int pivot = 0; pivot < size; pivot++) {
            // find pivot row and swap
            int max = pivot;
            for (int i = pivot + 1; i < size; i++) {
                if (Math.abs(matrix[i][pivot]) > Math.abs(matrix[max][pivot])) {
                    max = i;
                }
            }

            //swaping values depending on found pivot
            double[] temp = matrix[pivot];
            matrix[pivot] = matrix[max];
            matrix[max] = temp;

            double temp2 = solution[pivot];
            solution[pivot] = solution[max];
            solution[max] = temp2;

            // if matrix doesnt have nice result
            if (Math.abs(matrix[pivot][pivot]) <= norm) { return null; }

            // substracting pivotal row from rest
            for (int i = pivot + 1; i < size; i++) {
                double alpha = matrix[i][pivot] / matrix[pivot][pivot];
                solution[i] -= alpha * solution[pivot];
                for (int j = pivot; j < size; j++) {
                    matrix[i][j] -= alpha * matrix[pivot][j];
                }
            }
        }

        // back substitution
        double[] x = new double[size];
        for (int i = size - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < size; j++) {
                sum += matrix[i][j] * x[j];
            }
            x[i] = (solution[i] - sum) / matrix[i][i];
        }
        return x;
    }
}
