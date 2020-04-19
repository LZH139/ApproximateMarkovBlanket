import java.util.*;

/**
 * @author lzh
 */
public class ApproximateMarkovBlanket {
    private Matrix mat;

    public ApproximateMarkovBlanket(double[][] m, String[] namelist){
        this.mat = new Matrix(m, namelist);
    }

    private double count_HX(String X) throws CloneNotSupportedException {
        //返回的是克隆后的副本，不会对原对象产生影响，原对象可重复使用
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

    private double count_js(String[] X,String Y) throws CloneNotSupportedException{
        int m = X.length;
        double SU_XX = 0d;
        double SU_SY = 0d;
        for(String _x:X){
            SU_SY+=count_symmetrical_uncertainty(_x,Y);
            for(String __x:X){
                if(_x.equals(__x)){
                    continue;
                }
                SU_XX += count_symmetrical_uncertainty(_x,__x);

            }
        }
        SU_SY = SU_SY/m;
        SU_XX = SU_XX * 2/(m*(m-1));
        return m*SU_SY/Math.pow(m+m*(m-1)*SU_XX,0.5);
    }


    /***
     *返回的数据结构 -> [[String columnName, String delta],[...],...]
     */
    private String[][] feature_selection(double delta) throws CloneNotSupportedException {
        ArrayList<String[]> s = new ArrayList<>();
        String D = mat.getDecisionAttr();
        String[] C = mat.getConditionAttr();
        double su;
        for(String _c:C){
            su = count_symmetrical_uncertainty(_c,D);
            if(su > delta){
                s.add(new String[]{_c,String.valueOf(su)});
            }
        }
        return s.toArray(new String[0][0]);
    }
    

    private GPG greedy_predominant_groups_generator(double delta) throws CloneNotSupportedException {
        String[][] L = feature_selection(delta);
        HashSet<String> temp = new HashSet<>();
        ArrayList<String> Gt = new ArrayList<>();
        ArrayList<String> S = new ArrayList<>();
        ArrayList<ArrayList<String>> G = new ArrayList<>();

        String D = mat.getDecisionAttr();

        Arrays.sort(L, (s1, s2) -> {
            if(Double.parseDouble(s1[1])>Double.parseDouble(s2[1])){
                return 1;
            }else if(Double.parseDouble(s1[1])<Double.parseDouble(s2[1])){
                return -1;
            }
            return 0;
        });

        for(String[] f:L){
            if(!temp.contains(f[0])){
                Gt.add(f[0]);
                S.add(f[0]);
            }
            for(String[] _f:L){
                if(f[0].equals(_f[0])){
                   continue;
                }
                if(count_symmetrical_uncertainty(f[0],_f[0])>=count_symmetrical_uncertainty(_f[0],D)){
                    Gt.add(_f[0]);
                    temp.add(_f[0]);
                }
            }

            G.add(Gt);
        }
        return new GPG(G,S);
    }

    private double shaking_method(int d, GPG s,int k){
        int i=1;
        int SLen = s.getS().size();
        Random r=new Random();
        while (i!=k){
            int j = r.nextInt(d+1);
            if(j<SLen){

            }
        }
    }

}
