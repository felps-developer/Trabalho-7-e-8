import java.util.ArrayList;

public class No {
    public String id;
    public int valor;
    private ArrayList<No> conexoes;
    public boolean visitado;
    public No proximo;

    public No(String id) {
        this.id = id;
        this.valor = Integer.MAX_VALUE;
        this.conexoes = new ArrayList<>();
        this.visitado = false;
        this.proximo = null;
    }

    public ArrayList<No> getConexoes() {
        return conexoes;
    }

    // MOSTRAR APENAS PROFUNDIDADE
    public String toStringProf() {
        return String.format("%s", valor);
    }

    // MOSTRAR NÓ + DISTÂNCIA PARA NÓ RAIZ - Nó
    @Override
    public String toString() {
        return String.format("%s(%d)", id, valor);
    }

    public void adicionar(No destino) {
        for (No no: conexoes) {
            if(no.id.equals(destino.id))
                return;
        }

        conexoes.add(destino);
    }
}