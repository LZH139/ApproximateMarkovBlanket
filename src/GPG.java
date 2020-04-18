import java.util.ArrayList;

public class GPG {
    ArrayList<ArrayList<String>> G;
    ArrayList<String> S;

    public GPG(ArrayList<ArrayList<String>> G, ArrayList<String> S){
        this.G = G;
        this.S = S;
    }

    public ArrayList<ArrayList<String>> getG() {
        return G;
    }

    public void setG(ArrayList<ArrayList<String>> g) {
        G = g;
    }

    public ArrayList<String> getS() {
        return S;
    }

    public void setS(ArrayList<String> s) {
        S = s;
    }
}
