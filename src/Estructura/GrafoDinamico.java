package Estructura;

import java.util.*;

public class GrafoDinamico {
    private Map<Integer, Nodo> nodos;

    public GrafoDinamico() {
        nodos = new HashMap<>();
    }

    public void agregarNodo(int id) {
        nodos.putIfAbsent(id, new Nodo(id));
    }

    public void agregarArista(int origen, int destino, int peso) {
        agregarNodo(origen);
        agregarNodo(destino);

        Arista arista = new Arista(origen, destino, peso);
        nodos.get(origen).agregarArista(arista);

        Arista aristaInversa = new Arista(destino, origen, peso);
        nodos.get(destino).agregarArista(aristaInversa);
    }

    public void mostrarGrafo() {
        for (Nodo nodo : nodos.values()) {
            System.out.println(nodo);
        }
    }

    public List<Arista> getAristas() {
        Set<Arista> aristasUnicas = new HashSet<>();
        for (Nodo nodo : nodos.values()) {
            aristasUnicas.addAll(nodo.getAdyacencias());
        }
        return new ArrayList<>(aristasUnicas);
    }

    public Collection<Nodo> getNodos() {
        return nodos.values();
    }

    // Algoritmo de Kruskal
    public List<Arista> kruskalMST() {
        List<Arista> mst = new ArrayList<>();
        UnionFind uf = new UnionFind();

        // Crear conjuntos disjuntos para cada nodo
        for (Integer nodoId : nodos.keySet()) {
            uf.makeSet(nodoId);
        }

        // Obtener todas las aristas y ordenarlas por peso
        List<Arista> aristas = getAristas();
        Collections.sort(aristas, Comparator.comparingInt(Arista::getPeso));

        // Iterar sobre aristas y construir el MST
        for (Arista arista : aristas) {
            int origen = arista.getOrigen();
            int destino = arista.getDestino();

            // Si los nodos no están conectados, añadimos la arista al MST
            if (uf.find(origen) != uf.find(destino)) {
                mst.add(arista);
                uf.union(origen, destino);
            }
        }
        return mst;
    }
}
