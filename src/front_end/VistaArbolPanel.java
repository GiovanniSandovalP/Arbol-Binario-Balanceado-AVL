package front_end;

import back_end.ArbolAVL;
import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class VistaArbolPanel extends JPanel {

    private JTextArea txtVistaArbol;
    private JTextArea txtVistaColumnas;

    public VistaArbolPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Vista del árbol balanceado"));

        txtVistaArbol = crearAreaTexto();
        txtVistaColumnas = crearAreaTexto();

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.addTab("Estructura (texto)", new JScrollPane(txtVistaArbol));
        pestanas.addTab("Columnas (izq / centro / der)", new JScrollPane(txtVistaColumnas));
        add(pestanas, BorderLayout.CENTER);

        limpiar();
    }

    private JTextArea crearAreaTexto() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        return area;
    }

    public void mostrarArbol(ArbolAVL arbol) {
        if (arbol == null || arbol.estaVacio()) {
            limpiar();
            return;
        }

        String estructura = arbol.mostrarEstructura();
        if (estructura == null || estructura.trim().isEmpty()) {
            txtVistaArbol.setText("Árbol vacío.");
        } else {
            txtVistaArbol.setText(estructura);
        }

        txtVistaColumnas.setText(generarColumnas(arbol));
    }

    public void limpiar() {
        txtVistaArbol.setText("Árbol vacío.");
        txtVistaColumnas.setText("Árbol vacío.");
    }

    static String generarColumnas(ArbolAVL arbol) {
        if (arbol == null || arbol.estaVacio()) {
            return "Árbol vacío.";
        }

        List<String> preOrden = parsearRecorrido(arbol.mostrarPreOrden());
        List<String> inOrden = parsearRecorrido(arbol.mostrarInOrden());

        if (preOrden.isEmpty() || preOrden.size() != inOrden.size()) {
            return "Árbol vacío.";
        }

        NodoVista raiz = reconstruir(preOrden, 0, preOrden.size() - 1, inOrden, 0, inOrden.size() - 1);
        if (raiz == null) {
            return "Árbol vacío.";
        }

        int[] contador = new int[] { 0 };
        asignarPosiciones(raiz, 0, contador);

        List<NodoVista> nodos = new ArrayList<>();
        recolectar(raiz, nodos);

        int anchoMaximo = 1;
        for (NodoVista nodo : nodos) {
            if (nodo.etiqueta.length() > anchoMaximo) {
                anchoMaximo = nodo.etiqueta.length();
            }
        }

        if (nodos.size() <= 2) {
            return generarColumnasCentradas(nodos, anchoMaximo + 4);
        }

        int anchoCelda = anchoMaximo + 2;
        int anchoTotal = inOrden.size() * anchoCelda;

        int niveles = 0;
        for (NodoVista nodo : nodos) {
            if (nodo.nivel + 1 > niveles) {
                niveles = nodo.nivel + 1;
            }
        }

        StringBuilder salida = new StringBuilder();
        for (int nivel = 0; nivel < niveles; nivel++) {
            char[] linea = new char[anchoTotal];
            for (int i = 0; i < linea.length; i++) {
                linea[i] = ' ';
            }

            for (NodoVista nodo : nodos) {
                if (nodo.nivel == nivel) {
                    int inicio = nodo.columna * anchoCelda + (anchoCelda - nodo.etiqueta.length()) / 2;
                    for (int i = 0; i < nodo.etiqueta.length(); i++) {
                        int posicion = inicio + i;
                        if (posicion >= 0 && posicion < linea.length) {
                            linea[posicion] = nodo.etiqueta.charAt(i);
                        }
                    }
                }
            }

            if (nivel > 0) {
                salida.append('\n');
            }
            salida.append(String.format("Nivel %2d: ", nivel));
            salida.append(recortarDerecha(new String(linea)));
        }

        return salida.toString();
    }

    private static String generarColumnasCentradas(List<NodoVista> nodos, int ancho) {
        nodos.sort((a, b) -> Integer.compare(a.nivel, b.nivel));

        StringBuilder salida = new StringBuilder();
        int nivelActual = -1;

        for (NodoVista nodo : nodos) {
            if (nivelActual != -1 && nodo.nivel != nivelActual) {
                salida.append('\n');
            }
            nivelActual = nodo.nivel;

            int relleno = Math.max(0, (ancho - nodo.etiqueta.length()) / 2);
            for (int i = 0; i < relleno; i++) {
                salida.append(' ');
            }
            salida.append(nodo.etiqueta);
        }

        return salida.toString();
    }

    private static List<String> parsearRecorrido(String recorrido) {
        List<String> valores = new ArrayList<>();
        if (recorrido == null) {
            return valores;
        }

        for (String linea : recorrido.split("\\R")) {
            String limpia = linea.trim();
            if (!limpia.isEmpty()) {
                valores.add(extraerId(limpia));
            }
        }

        return valores;
    }

    private static String extraerId(String linea) {
        int posicion = linea.indexOf(" - ");
        if (posicion >= 0) {
            return linea.substring(0, posicion).trim();
        }
        return linea.trim();
    }

    private static NodoVista reconstruir(List<String> preOrden, int preInicio, int preFin,
            List<String> inOrden, int inInicio, int inFin) {
        if (preInicio > preFin || inInicio > inFin) {
            return null;
        }

        String id = preOrden.get(preInicio);

        int indice = -1;
        for (int i = inInicio; i <= inFin; i++) {
            if (inOrden.get(i).equals(id)) {
                indice = i;
                break;
            }
        }

        if (indice == -1) {
            return null;
        }

        NodoVista nodo = new NodoVista(id);
        int tamanoIzquierdo = indice - inInicio;

        nodo.izquierdo = reconstruir(preOrden, preInicio + 1, preInicio + tamanoIzquierdo,
                inOrden, inInicio, indice - 1);
        nodo.derecho = reconstruir(preOrden, preInicio + tamanoIzquierdo + 1, preFin,
                inOrden, indice + 1, inFin);

        return nodo;
    }

    private static void asignarPosiciones(NodoVista nodo, int nivel, int[] contador) {
        if (nodo == null) {
            return;
        }

        asignarPosiciones(nodo.izquierdo, nivel + 1, contador);
        nodo.nivel = nivel;
        nodo.columna = contador[0]++;
        asignarPosiciones(nodo.derecho, nivel + 1, contador);
    }

    private static void recolectar(NodoVista nodo, List<NodoVista> nodos) {
        if (nodo == null) {
            return;
        }

        nodos.add(nodo);
        recolectar(nodo.izquierdo, nodos);
        recolectar(nodo.derecho, nodos);
    }

    private static String recortarDerecha(String texto) {
        int fin = texto.length();
        while (fin > 0 && texto.charAt(fin - 1) == ' ') {
            fin--;
        }
        return texto.substring(0, fin);
    }

    private static class NodoVista {

        private final String etiqueta;
        private NodoVista izquierdo;
        private NodoVista derecho;
        private int nivel;
        private int columna;

        NodoVista(String etiqueta) {
            this.etiqueta = etiqueta;
        }
    }
}
