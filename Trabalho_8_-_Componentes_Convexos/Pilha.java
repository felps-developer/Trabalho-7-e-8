import java.util.ArrayList;

public class Pilha {
    private ArrayList<No[]> topo;

    public Pilha() {
        this.topo = new ArrayList<>();
    }

    public void push(No[] no) {
        topo.add(no);
    }

    public No[] pop() {
        return topo.remove(topo.size()-1);
    }

    public int tamanho() {
        return topo.size();
    }

    @Override
    public String toString() {
        String result = "";
        for(No[] no: topo) {
            result += String.format("{} -> {},", no[0].toString(), no[1].toString());
        }

        return result;
    }
}
