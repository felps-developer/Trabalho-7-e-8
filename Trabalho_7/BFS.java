//import org.graphstream.graph.Graph;
//import org.graphstream.graph.Node;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.*;

public class BFS {

    public static HashSet<No> CaminhoBFS (No inicio){
        HashSet<No[]> arvore = new HashSet<>();
        LinkedList<No> ListaNosRestantes = new LinkedList<>();
        HashSet<No> visitado = new HashSet<>();
        inicio.valor = 0;
        ListaNosRestantes.add(inicio);


        while (!ListaNosRestantes.isEmpty()){
//            System.out.println(ListaNosRestantes);
            No no = ListaNosRestantes.remove();

            if (visitado.contains(no)){
                continue;
            }

            visitado.add(no);

            for (No filho: no.getConexoes()){
                if (!(ListaNosRestantes.contains(filho) || visitado.contains(filho))) {

                    filho.valor = Math.min(filho.valor, no.valor+1);
                    ListaNosRestantes.add(filho);

                    arvore.add(new No[]{no, filho});
                }
            }
        }

        return visitado;
    }

    public static void desenharGrafo(HashSet<No[]> conexoes) {
        // Ao invés de ter "Grafo grafo" como parâmetro, alterar para um ArrayList, onde cada elemento é adicionado por
        // fora e depois passa
        System.setProperty("org.graphstream.ui", "swing");
        MultiGraph graph = new MultiGraph("Tutorial 1");
        graph.setAttribute("ui.stylesheet",
                "node {" +
                        "fill-color: white;" +
                        "size: 50px;" +
                        "stroke-mode: plain;" +
                        "stroke-color: black;" +
                        "stroke-width: 1px;" +
                        "}"
        );

        HashSet<No> nosParaAdicionar = new HashSet<>();
        conexoes.forEach(
                (conexao) -> {
                    nosParaAdicionar.add(conexao[0]);
                    nosParaAdicionar.add(conexao[1]);
                }
        );

        for(No n: nosParaAdicionar) {
            graph.addNode(n.id);
        }

        for(No[] n: conexoes) {
            graph.addEdge(String.format("%s-%s", n[0].id, n[1].id), n[0].id, n[1].id);
        }


        graph.edges().forEach(
                (origem) -> {
                    Node node0 = origem.getNode0();
                    Node node1 = origem.getNode1();

                    node0.setAttribute("ui.label", node0.getId());
                    node1.setAttribute("ui.label", node1.getId());
                    node0.setAttribute("ui.class", "node");
                }
        );

        graph.display();
    }

    public static void medirDistanciaMedia(Grafo grafo) {
        int tamanhoGrafo = grafo.getConexoes().size();
        for(int i = 0; i < tamanhoGrafo; i++) {
            No n = grafo.getConexoes().get(i);

            BFS.CaminhoBFS(n);
            float media = 0;

            for(No no: grafo.getConexoes()) {
                media+=no.valor;
            }

            media /= tamanhoGrafo;


            System.out.println(String.format("Valores médios com nó %s: %s\nDistância média total: %f\n", n.id ,grafo.getConexoes().subList(i+1, tamanhoGrafo), media));

            grafo.reiniciarValores();
        }

    }

    public static void medirDiametro(Grafo grafo) {
        ArrayList<Integer> vetorDistancias = new ArrayList<>();

        // Realizar o algorítmo BFS pra cada nó do grafo
        // Cada vez realizado, obtém a maior distância de um nó para a raiz
        for(No no: grafo.getConexoes()) {
            grafo.reiniciarValores();
            int maiorValorNo = 0;

            for(No noTemp: BFS.CaminhoBFS(no)) {
                maiorValorNo = Math.max(maiorValorNo, noTemp.valor);
            }
            System.out.println("Nó: " + no.id);
            System.out.println("Maior distância: " + maiorValorNo + "\n");
            vetorDistancias.add(maiorValorNo);
        }


        // Depois comparar no "vetorDistancias" quem é o maior
        int diametro = 0;

        for(int distancia: vetorDistancias) {
            diametro = Math.max(distancia, diametro);
        }

        System.out.println(vetorDistancias);
        System.out.println("Diâmetro:" + diametro);
    }

    public static void main(String[] args) {
        Grafo grafo = new Grafo();

        int[][] matriz = {
                {0, 1, 0, 0, 1, 0, 0, 0}, // r
                {1, 0, 0, 0, 0, 1, 0, 0}, // s
                {0, 0, 0, 1, 0, 1, 1, 0}, // t
                {0, 0, 1, 0, 0, 0, 1, 1}, // u
                {1, 0, 0, 0, 0, 0, 0, 0}, // v
                {0, 1, 1, 0, 0, 0, 1, 0}, // w
                {0, 0, 1, 1, 0, 1, 0, 1}, // x
                {0, 0, 0, 1, 0, 0, 1, 0}, // y
        };

        String[] nos = {"r", "s", "t", "u", "v", "w", "x", "y"};

        for(String no: nos) {
            grafo.adicionar(no);
        }

        for (int i = 0 ; i < nos.length; i++){
            for(int j = 0; j < nos.length; j++) {
                if(matriz[i][j] == 1) grafo.conectarNo(nos[i], nos[j]);
            }
        }

        medirDistanciaMedia(grafo);
//        medirDiametro(grafo);
    }
}