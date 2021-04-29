//import org.graphstream.graph.Graph;
//import org.graphstream.graph.Node;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.ArrayList;
import java.util.HashSet;

public class DFS {
    public static int distancia;
    public static Pilha arvoreGeradora;

    // Trabalho 8
    public static void DFS_Pilha(No n) {

        n.visitado = true;

        for(No filho: n.getConexoes()) {
            if(!filho.visitado) {
                arvoreGeradora.push(new No[]{n, filho});
                distancia++;
                filho.valor = distancia;
                DFS_Pilha(filho);
            }
        }
    }

    public static void verificarConvexo(Grafo grafo) {
        // Realizar o esquema de fazer DFS em todos os nós e ir adicionando

        ArrayList<ArrayList<No>> componentesConexos = new ArrayList<>();

        for(No no1: grafo.getConexoes()) {

            boolean pularIteracaoAtual = false;

            // verificar se não foi adicionado ao vetor de componentesConexos
            for(ArrayList<No> no3: componentesConexos) {
                if(no3.contains(no1)){
                    pularIteracaoAtual = true;
                    break;
                }
            }

            // Se já estiver no vetor, pular o nó atual
            if(pularIteracaoAtual) continue;

            // reiniciando valores do DFS anterior
            arvoreGeradora = new Pilha();
            grafo.reiniciarValores();
            distancia = 0;
            no1.valor = 0;

            // Realizar DFS para pegar a árvore geradora do método
            // Todos com valor menor que infinito é pq tem conexao entre si
            DFS.DFS_Pilha(no1);

            ArrayList<No> componente = new ArrayList<>();
            componente.add(no1);

            while(arvoreGeradora.tamanho() > 0) {
                No[] conexao = arvoreGeradora.pop();
                if (!componente.contains(conexao[0])) {
                    componente.add(conexao[0]);
                }
//
                if (!componente.contains(conexao[1])) {
                    componente.add(conexao[1]);
                }
            }

            componentesConexos.add(componente);
        }

        // Ordenar do maior pro menor tamanho
        componentesConexos.sort((a, b) -> b.size() - a.size());

        // Quantia de componentes
        String resultado = "";
        for(ArrayList<No> nos: componentesConexos) {
            resultado += nos.size() + " ";
        }

        System.out.println("Quantia de componentes conexos: " + componentesConexos.size());
        System.out.println("Lista contendo os componentes e suas conexoes: " + componentesConexos);
        System.out.println("Tamanho por componente conexo: " + resultado);
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

    public static void comecarDFSPilha(Grafo grafo, String nomeNo) {
        System.out.println("Trabalho 8");
        for(No no: grafo.getConexoes()) {
            if(no.id.equalsIgnoreCase(nomeNo)) {
                // DFS
                arvoreGeradora = new Pilha();
                grafo.reiniciarValores();
                no.valor = 0;
                DFS.DFS_Pilha(no);
                System.out.println(arvoreGeradora);
                System.out.println(grafo);

                // Desenhar grafo inicial
                DFS.desenharGrafo(DFS.gerarGrafoDesenho(grafo), "pink");

                // Desenhar arvore geradora
                DFS.desenharGrafo(DFS.gerarGrafoDesenho(arvoreGeradora), "green");
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
                {0, 0, 1, 0, 0, 0, 0, 0}, // 1
                {0, 0, 1, 0, 0, 0, 0, 0}, // 2
                {1, 1, 0, 0, 0, 0, 0, 0}, // 3
                {0, 0, 0, 0, 0, 0, 0, 0}, // 4
                {0, 0, 0, 0, 0, 1, 1, 0}, // 5
                {0, 0, 0, 0, 1, 0, 1, 0}, // 6
                {0, 0, 0, 0, 1, 1, 0, 0}, // 7
                {0, 0, 0, 0, 0, 0, 0, 0}, // 8
        };

//        int[][] matriz = {
//                {0, 1, 0, 1, 0}, // 1
//                {1, 0, 1, 0, 0}, // 2
//                {0, 1, 0, 1, 0}, // 3
//                {1, 0, 1, 0, 0}, // 4
//                {0, 0, 0, 0, 0}, // 5
//        };

//        String[] nos = {"1", "2", "3", "4", "5"};
        String[] nos = {"1", "2", "3", "4", "5", "6", "7", "8"};

        for(String no: nos) {
            grafo.adicionar(no);
        }

        for (int i = 0 ; i < nos.length; i++){
            for(int j = 0; j < nos.length; j++) {
                if(matriz[i][j] == 1) grafo.conectarNo(nos[i], nos[j]);
            }
        }

//     DFS.comecarDFSPilha(grafo, "5"); // Trabalho 08 - Pilha
      DFS.verificarConvexo(grafo); // Trabalho 08 - Convexo
    }
}