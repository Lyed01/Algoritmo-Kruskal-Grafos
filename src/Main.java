import Estructura.*;

import javax.swing.*;
import java.util.List;

import static Estructura.KruskalAnimation.mst;


public class Main {
    public static void main(String[] args) {
        GrafoDinamico grafo = new GrafoDinamico();
        // Agregar nodos y aristas al grafo
        grafo.agregarArista(1, 2, 3);
        grafo.agregarArista(1, 3, 2);
        grafo.agregarArista(1, 4, 3);
        grafo.agregarArista(2, 3, 1);
        grafo.agregarArista(2, 5, 5);
        grafo.agregarArista(3, 4, 2);
        grafo.agregarArista(3, 5, 7);
        grafo.agregarArista(3, 6, 8);
        grafo.agregarArista(4, 6, 6);
        grafo.agregarArista(5, 6, 4);

        JFrame frame = new JFrame("Animación de Kruskal");
        KruskalAnimation animacion = new KruskalAnimation(grafo);
        frame.add(animacion);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        System.out.println("Arbol de Expansión Mínima (MST):");
        for (Arista arista : mst) {
            System.out.println("Nodo " + arista.getOrigen() + " - Nodo " + arista.getDestino() + " (Peso: " + arista.getPeso() + ")");
        }
    }
}