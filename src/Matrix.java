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
    private String[] columnName;

    public Matrix(double[][] m, String[] nameList){
        rowNum = m.length;
        columnNum = m[0].length;
        ArrayList<String> columnName = new ArrayList<>();
        Set<String> set = new HashSet<>();
        for(String name:nameList){
            if(set.contains(name)){
                throw new RuntimeException("Duplicate column name");
            }else{
                set.add(name);
                columnName.add(name);
            }
        }
        this.columnName = columnName.toArray(new String[0]);
        generateMatrix(m,nameList);
    }

    /**
     * 生成特征矩阵，默认最后一列为决策属性，其余为条件属性
     */
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

    public String[] getColumnName() {
        String[] res = new String[columnName.length];
        System.arraycopy(columnName, 0, res, 0, columnName.length);
        return res;
    }

    public int getRowNum() {
        return rowNum;
    }

    public int getColumnNum() {
        return columnNum;
    }

    public CellArray getRow() throws CloneNotSupportedException {
        return row.clone();
    }

    public String getDecisionAttr(){
        return columnName[columnName.length-1];
    }

    public String[] getConditionAttr(){
        String[] res = new String[columnName.length];
        System.arraycopy(columnName, 0, res, 0, columnName.length-1);
        return res;
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
