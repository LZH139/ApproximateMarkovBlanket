import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author lzh
 */
public class ApproximateMarkovBlanket {
    private Matrix mat;

    public ApproximateMarkovBlanket(double[][] m, String[] namelist){
        this.mat = new Matrix(m, namelist);
    }

    private double count_HX(String X) throws CloneNotSupportedException {
        CellArray ca = mat.getRow();
        mat.divide(ca,X);
        double H = 0;
        double rowNum = mat.getRowNum();
        int size;
        for(CellArray c:ca.getList()){
            size = c.getData().length;
            H = H - (size/rowNum)*Math.log(size/rowNum);
        }
        return H;
    }

    private double count_HXY(String X,String Y) throws CloneNotSupportedException {
        CellArray ca = mat.getRow();
        mat.divide(ca,X,Y);
        double rowNum = mat.getRowNum();
        double H = 0;
        double size;
        for(CellArray cel:ca.getList()){
            for(CellArray ce:cel.getList()){
                size = ce.getData().length;
                H = H - (size/rowNum)*Math.log(size/ce.sum());
            }
        }
        return H;
    }

    private double count_Iyx(String X,String Y) throws CloneNotSupportedException {
        return count_HX(X)-count_HXY(X,Y);

    }

    private double count_symmetrical_uncertainty(String X,String Y) throws CloneNotSupportedException {
        return 2*count_Iyx(Y,X)/(count_HX(X)+count_HX(Y));
    }

    

}
