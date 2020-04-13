public class MatrixTest {

    public static void main(String[] args) throws CloneNotSupportedException {
        double[][] list = new double[][]{   {1,2,3,4},
                                            {4,5,6,7},
                                            {7,8,6,0}};
        Matrix m = new Matrix(list, new String[]{"a","b","c","d"});
        CellArray s = m.getRow();
        m.divide(s,"c","a");
        System.out.print("");
    }
}
