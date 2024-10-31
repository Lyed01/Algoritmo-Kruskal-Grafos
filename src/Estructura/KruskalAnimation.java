package Estructura;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class KruskalAnimation extends JPanel {
    private GrafoDinamico grafo;
    public static List<Arista> mst;
    private List<Arista> selectedEdges = new ArrayList<>(); // Lista para las aristas seleccionadas
    private int currentEdgeIndex = 0;
    private Timer timer; // Timer para controlar la animación

    // Nuevas coordenadas ajustadas
    private int[][] coordenadas = {
            {375, 75},   // Nodo 1 (arriba a la izquierda)
            {525, 75},   // Nodo 2 (arriba a la derecha)
            {450, 150},  // Nodo 3 (centro)
            {350, 225},  // Nodo 4 (abajo a la izquierda)
            {550, 225},  // Nodo 5 (abajo a la derecha)
            {450, 300}   // Nodo 6 (abajo centro)
    };

    public KruskalAnimation(GrafoDinamico grafo) {
        this.grafo = grafo;
        this.mst = grafo.kruskalMST();
        setPreferredSize(new Dimension(800, 600));

        // Inicializar el Timer
        timer = new Timer(1000, e -> {
            if (currentEdgeIndex < grafo.getAristas().size()) {
                repaint(); // Actualiza la visualización antes de avanzar
                currentEdgeIndex++; // Avanza a la siguiente arista
            } else {
                timer.stop(); // Detener el timer si hemos procesado todas las aristas
            }
        });
        timer.start(); // Comienza la animación
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujarGrafo(g);
        dibujarMST(g);
    }

    private void dibujarGrafo(Graphics g) {
        // Dibuja los nodos
        for (Nodo nodo : grafo.getNodos()) {
            int index = nodo.getId() - 1; // Asegúrate de que el ID comienza desde 1
            if (index >= 0 && index < coordenadas.length) {
                int x = coordenadas[index][0];
                int y = coordenadas[index][1];

                // Dibuja el nodo
                g.fillOval(x, y, 50, 50); // Aumenta el tamaño del nodo
                g.drawString("Nodo " + nodo.getId(), x + 10, y - 10); // Aumenta el tamaño de la etiqueta

                // Dibuja las aristas en negro inicialmente
                for (Arista arista : nodo.getAdyacencias()) {
                    int destinoIndex = arista.getDestino() - 1; // Asumiendo que el destino es un ID válido
                    if (destinoIndex >= 0 && destinoIndex < coordenadas.length) {
                        int destinoX = coordenadas[destinoIndex][0];
                        int destinoY = coordenadas[destinoIndex][1];

                        // Dibuja la arista en negro
                        g.setColor(Color.DARK_GRAY);
                        Graphics2D g2d = (Graphics2D) g; // Usar Graphics2D para más opciones
                        g2d.setStroke(new BasicStroke(3)); // Establecer grosor de línea
                        g2d.drawLine(x + 25, y + 25, destinoX + 25, destinoY + 25); // Dibuja las aristas

                        // Dibuja el peso de la arista (más grande)
                        int pesoX = (x + destinoX) / 2;
                        int pesoY = (y + destinoY) / 2;
                        g.setFont(new Font("Arial", Font.BOLD, 16)); // Cambia el tamaño de la fuente
                        g.drawString(String.valueOf(arista.getPeso()), pesoX, pesoY);
                    }
                }
            }
        }
    }

    private void dibujarMST(Graphics g) {
        // Dibuja todas las aristas seleccionadas en el MST
        g.setColor(Color.RED); // Cambia el color para resaltar el MST
        Graphics2D g2d = (Graphics2D) g; // Usar Graphics2D para más opciones
        g2d.setStroke(new BasicStroke(4)); // Establecer grosor de línea para el MST

        // Verifica si hay aristas disponibles para analizar
        if (currentEdgeIndex < grafo.getAristas().size()) {
            Arista arista = grafo.getAristas().get(currentEdgeIndex);
            int origenIndex = arista.getOrigen() - 1; // Asumiendo que el origen es un ID válido
            int destinoIndex = arista.getDestino() - 1; // Asumiendo que el destino es un ID válido

            if (origenIndex >= 0 && origenIndex < coordenadas.length && destinoIndex >= 0 && destinoIndex < coordenadas.length) {
                int origenX = coordenadas[origenIndex][0];
                int origenY = coordenadas[origenIndex][1];
                int destinoX = coordenadas[destinoIndex][0];
                int destinoY = coordenadas[destinoIndex][1];

                // Dibuja la arista que se está analizando en azul
                g.setColor(Color.BLUE);
                g2d.drawLine(origenX + 25, origenY + 25, destinoX + 25, destinoY + 25);

                // Espera un momento antes de evaluar si la arista pertenece al MST
                SwingUtilities.invokeLater(() -> {
                    try {
                        Thread.sleep(500); // Espera medio segundo
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Verifica si la arista pertenece al MST
                    if (mst.contains(arista)) {
                        selectedEdges.add(arista); // Agrega la arista al MST
                        g.setColor(Color.GREEN); // Color para aristas que están en el MST
                    } else {
                        g.setColor(Color.RED); // Color para aristas que no están en el MST
                    }

                    // Dibuja la arista en el color correspondiente
                    g2d.drawLine(origenX + 25, origenY + 25, destinoX + 25, destinoY + 25);

                    // Dibuja el peso de la arista
                    int pesoX = (origenX + destinoX) / 2;
                    int pesoY = (origenY + destinoY) / 2;
                    g.setFont(new Font("Arial", Font.BOLD, 16)); // Cambia el tamaño de la fuente
                    g.drawString(String.valueOf(arista.getPeso()), pesoX, pesoY);
                });
            }
        }

        // Dibuja el MST completo en verde después de haber analizado todas las aristas
        g.setColor(Color.GREEN); // Cambia el color para resaltar el MST
        for (Arista arista : selectedEdges) {
            int origenIndex = arista.getOrigen() - 1; // Asumiendo que el origen es un ID válido
            int destinoIndex = arista.getDestino() - 1; // Asumiendo que el destino es un ID válido

            if (origenIndex >= 0 && origenIndex < coordenadas.length && destinoIndex >= 0 && destinoIndex < coordenadas.length) {
                int origenX = coordenadas[origenIndex][0];
                int origenY = coordenadas[origenIndex][1];
                int destinoX = coordenadas[destinoIndex][0];
                int destinoY = coordenadas[destinoIndex][1];

                // Dibuja la arista del MST
                g2d.drawLine(origenX + 25, origenY + 25, destinoX + 25, destinoY + 25);

                // Dibuja el peso de la arista MST
                int pesoX = (origenX + destinoX) / 2;
                int pesoY = (origenY + destinoY) / 2;
                g.setFont(new Font("Arial", Font.BOLD, 16)); // Cambia el tamaño de la fuente
                g.drawString(String.valueOf(arista.getPeso()), pesoX, pesoY);
            }
        }
    }

}

