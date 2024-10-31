package Estructura;

import java.util.ArrayList;
import java.util.List;

public class Nodo {
    private int id;
    private List<Arista> adyacencias;

    public Nodo(int id) {
        this.id = id;
        this.adyacencias = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public List<Arista> getAdyacencias() {
        return adyacencias;
    }

    public void agregarArista(Arista arista) {
        adyacencias.add(arista);
    }

    @Override
    public String toString() {
        return "Nodo " + id + " conectado a: " + adyacencias;
    }
}
