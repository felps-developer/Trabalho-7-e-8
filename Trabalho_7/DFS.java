//import org.graphstream.graph.Graph;
//import org.graphstream.graph.Node;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.HashSet;

public class DFS {
    public static int distancia;
    public static Pilha arvoreGeradora;

    // Trabalho 7
    public static void DFS_Recursivo(No n) {

        n.visitado = true;

        for(No filho: n.getConexoes()) {

            if(!filho.visitado) {
                arvoreGeradora.push(new No[]{n, filho});
                distancia++;
                filho.valor = distancia;
                DFS_Recursivo(filho);
            }

        }

    }

    public static void desenharGrafo(HashSet<No[]> conexoes, String cor) {
        // Ao invés de ter "Grafo grafo" como parâmetro, alterar para um ArrayList, onde cada elemento é adicionado por
        // fora e depois passa
        System.setProperty("org.graphstream.ui", "swing");
        MultiGraph graph = new MultiGraph("Tutorial 1");
        graph.setAttribute("ui.stylesheet",
                "node {" +
                        "fill-color: " + cor +
                        ";size: 60px, 60px;" +
                        "stroke-mode: plain;" +
                        "stroke-color: black;" +
                        "stroke-width: 1px;" +
                        "text-size: 20px;" +
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
            graph.addNode(n.toString());
        }

        for(No[] n: conexoes) {
            graph.addEdge(String.format("%s-%s", n[0].toString(), n[1].toString()), n[0].toString(), n[1].toString());
        }


        graph.edges().forEach(
                (origem) -> {
                    Node node0 = origem.getNode0();
                    Node node1 = origem.getNode1();

                    node0.setAttribute("ui.label", node0.toString());
                    node1.setAttribute("ui.label", node1.toString());

                    node0.setAttribute("ui.class", "node");
                }
        );

        graph.display();
    }

    public static void comecarDFSRecursivo(Grafo grafo, String nomeNo) {
        System.out.println("Trabalho 7");
        for(No no: grafo.getConexoes()) {
            if(no.id.equalsIgnoreCase(nomeNo)) {
                // DFS
                arvoreGeradora = new Pilha();
                grafo.reiniciarValores();
                no.valor = 0;
                distancia = 0;
                DFS.DFS_Recursivo(no);

                // Desenhar grafo inicial
                DFS.desenharGrafo(DFS.gerarGrafoDesenho(grafo), "orange");

                // Árvore de profundidade
                DFS.desenharGrafo(DFS.gerarGrafoDesenho(arvoreGeradora), "cyan");
                return;
            }
        }

        System.out.println("Nome não encontrado no grafo!");
    }

    public static HashSet<No[]> gerarGrafoDesenho(Grafo grafo) {
        HashSet<No[]> grafoDesenho = new HashSet<>();
        for(No filho1: grafo.getConexoes()) {
            for(No filho2: filho1.getConexoes()) {
                grafoDesenho.add(new No[]{filho1, filho2});
            }
        }

        return grafoDesenho;
    }

    public static HashSet<No[]> gerarGrafoDesenho(Pilha pilha) {
        HashSet<No[]> grafoDesenho = new HashSet<>();
        while(pilha.tamanho() > 0) {
            No[] removidos = pilha.pop();
            grafoDesenho.add(new No[]{removidos[0], removidos[1]});
        }

        return grafoDesenho;
    }

    public static void main(String[] args) {
        Grafo grafo = new Grafo();

        int[][] matriz = {
                {0, 0, 1, 1, 0, 0, 0, 0}, // 1
                {0, 0, 1, 1, 0, 0, 0, 0}, // 2
                {1, 1, 0, 1, 0, 0, 0, 1}, // 3
                {1, 1, 1, 0, 0, 1, 1, 0}, // 4
                {0, 0, 0, 0, 0, 1, 1, 0}, // 5
                {0, 0, 0, 1, 1, 0, 1, 0}, // 6
                {0, 0, 0, 1, 1, 1, 0, 0}, // 7
                {0, 0, 1, 0, 0, 0, 0, 0}, // 8
        };

        String[] nos = {"1", "2", "3", "4", "5", "6", "7", "8"};

        for(String no: nos) {
            grafo.adicionar(no);
        }

        for (int i = 0 ; i < nos.length; i++){
            for(int j = 0; j < nos.length; j++) {
                if(matriz[i][j] == 1) grafo.conectarNo(nos[i], nos[j]);
            }
        }

        DFS.comecarDFSRecursivo(grafo, "1"); // Trabalho 07
    }
}