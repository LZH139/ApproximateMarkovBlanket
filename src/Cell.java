public class Cell {
    private String name;
    private int length;
    private double[] list;

    public Cell(String name, double[] list){
        this.list = list;
        this.length = list.length;
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public double[] getData() {
        return list;
    }

    public String getName() {
        return name;
    }

}
