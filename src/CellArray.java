/**
 * @author lzh
 */
public class CellArray implements Cloneable{
    /**
     * type:0->Cell[] 1->CellArray[]
     */
    private int sum;
    private CellArray[] list;
    private Cell[] data;
    private int type;

    public CellArray(Cell[] list){
        data = list;
        this.type = 0;
    }

    public CellArray(CellArray[] list){
        this.list = list;
        this.type = 1;
    }

    @Override
    public CellArray clone() throws CloneNotSupportedException {
        return (CellArray)super.clone();
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int sum() {
        return sum;
    }

    public CellArray[] getList() {
        return list;
    }

    public Cell[] getData() {
        return data;
    }

    public void setData(Cell[] d){
        this.list = null;
        this.data = d;
        this.type = 0;
    }

    public void setData(CellArray[] l){
        this.data = null;
        this.list = l;
        this.type = 1;
    }

    public int type() {
        return type;
    }
}
