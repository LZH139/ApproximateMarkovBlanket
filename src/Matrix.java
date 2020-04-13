import java.util.*;

/**
 * @author lzh
 */
public class Matrix {

    private CellArray row;
    private CellArray column;
    private int rowNum;
    private int columnNum;
    private HashMap<String,Integer> cMap = new HashMap<>();

    public Matrix(double[][] m, String[] nameList){
        rowNum = m.length;
        columnNum = m[0].length;
        Set<String> set = new HashSet<>();
        for(String name:nameList){
            if(set.contains(name)){
                throw new RuntimeException("Duplicate column name");
            }else{
                set.add(name);
            }
        }
        generateMatrix(m,nameList);
    }

    private void generateMatrix(double[][] m, String[] columnName){
        Cell[] trow = new Cell[rowNum];
        Cell[] tcolumn = new Cell[columnNum];

        if(m[0].length != columnName.length) {
            throw new RuntimeException("The list of names does not match the number of columns");
        }

        for(int i=0;i<rowNum;i++){
            trow[i] = new Cell("x"+i, m[i]);
        }

        for(int j=0;j<columnNum;j++){
            double[] l = new double[rowNum];
            for(int i=0;i<rowNum;i++){
                l[i] = m[i][j];
            }
            tcolumn[j] = new Cell(columnName[j], l);
        }

        this.row = new CellArray(trow);
        this.row.setSum(trow.length);
        this.column = new CellArray(tcolumn);
        this.column.setSum(tcolumn.length);

        for(int i=0;i<columnName.length;i++){
            cMap.put(columnName[i],i);
        }
    }

    /**
     * @param name 将该名字的列进行划分
     * @return 返回划分后的数组
     */

    public void divide(CellArray ca, String...name){
        ca.setSum(ca.getData().length);
        HashMap<Double,ArrayList<Cell>> map = new HashMap<>();
        Cell[] templist = ca.getData();
        int index = cMap.get(name[0]);
        for(Cell c:templist){
            if(map.containsKey(c.getData()[index])){
                map.get(c.getData()[index]).add(c);
            }else{
                ArrayList<Cell> l = new ArrayList<>();
                l.add(c);
                map.put(c.getData()[index],l);
            }
        }
        int mapSize = map.size();
        int cur = 0;
        CellArray[] nca = new CellArray[mapSize];

        for(Double d:map.keySet()){
            ArrayList<Cell> tlist = map.get(d);
            nca[cur++] = new CellArray(tlist.toArray(new Cell[0]));
            if(name.length>1){
                divide(nca[cur-1],Arrays.copyOfRange(name, 1, name.length));
            }

        }
        ca.setData(nca);

    }

//    public Cell[][] divide(String name){
//        HashMap<Double,Integer> map = new HashMap<>();
//        ArrayList<ArrayList<Cell>> result = new ArrayList<>();
//
//        // 用来检测是否有符合名称的列
//        boolean flag = false;
//
//        for(Cell c:column){
//            if(name.equals(c.getName())){
//                flag = true;
//                double[] templist = c.getData();
//                for(int i=0;i<templist.length;i++){
//                    if(!map.containsKey(templist[i])){
//                        map.put(templist[i],i);
//                        ArrayList<Cell> nlist = new ArrayList<>();
//                        nlist.add(row[i]);
//                        result.add(nlist);
//                    }else{
//                        result.get(map.get(templist[i])).add(row[i]);
//                    }
//                }
//            }
//            // 只划分第一个名称匹配的列
//            if(flag){
//                break;
//            }
//        }
//
//        if(!flag){
//            throw new RuntimeException("The column name was not found");
//        }
//
//        Cell[][] res = new Cell[result.size()][];
//        for(int i=0;i<res.length;i++){
//            res[i] = result.get(i).toArray(new Cell[0]);
//        }
//
//        return res;
//
//    }

//    public Cell[][] divide(int index){
//        HashMap<Double,Integer> map = new HashMap<>();
//        ArrayList<ArrayList<Cell>> result = new ArrayList<>();
//
//        if(index>columnNum || index < 1){
//            throw new RuntimeException("The number of columns does not match");
//        }
//
//        double[] templist = column[index-1].getData();
//        for(int i=0;i<templist.length;i++){
//            if(!map.containsKey(templist[i])){
//                map.put(templist[i],i);
//                ArrayList<Cell> nlist = new ArrayList<>();
//                nlist.add(row[i]);
//                result.add(nlist);
//            }else{
//                result.get(map.get(templist[i])).add(row[i]);
//            }
//        }
//
//        Cell[][] res = new Cell[result.size()][];
//        for(int i=0;i<res.length;i++){
//            res[i] = result.get(i).toArray(new Cell[0]);
//        }
//
//        return res;
//    }


    public int getRowNum() {
        return rowNum;
    }

    public int getColumnNum() {
        return columnNum;
    }

    public CellArray getRow() throws CloneNotSupportedException {
        return row.clone();
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("     ");
        for(Cell c:column.getData()){
            res.append(c.getName()).append("    ");
        }
        res.append("\n");
        for(Cell r:row.getData()){
            res.append(r.getName()).append(": ");
            res.append(Arrays.toString(r.getData()));
            res.append("\n");
        }
        return res.toString();
    }
}
