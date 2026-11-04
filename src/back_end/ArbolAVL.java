package back_end;

public class ArbolAVL {

    private NodoAVL raiz;

    public ArbolAVL() {
        this.raiz = null;
    }

    public void insertar(Empleado empleado) {
        if (!esValido(empleado)) {
            return;
        }
        raiz = insertarNodo(raiz, empleado);
    }

    private NodoAVL insertarNodo(NodoAVL nodo, Empleado empleado) {
        if (nodo == null) {
            return new NodoAVL(empleado);
        }

        int idActual = nodo.getDato().getId();
        int idNuevo = empleado.getId();

        if (idNuevo < idActual) {
            nodo.setIzquierdo(insertarNodo(nodo.getIzquierdo(), empleado));
        } else if (idNuevo > idActual) {
            nodo.setDerecho(insertarNodo(nodo.getDerecho(), empleado));
        } else {
            return nodo;
        }

        actualizarAltura(nodo);
        return rebalancear(nodo);
    }

    public Empleado buscar(Integer id) {
        if (id == null || id <= 0 || raiz == null) {
            return null;
        }

        NodoAVL encontrado = buscarNodo(raiz, id);

        if (encontrado == null) {
            return null;
        }

        return encontrado.getDato();
    }

    private NodoAVL buscarNodo(NodoAVL nodo, int id) {
        if (nodo == null) {
            return null;
        }

        int idActual = nodo.getDato().getId();

        if (id == idActual) {
            return nodo;
        } else if (id < idActual) {
            return buscarNodo(nodo.getIzquierdo(), id);
        } else {
            return buscarNodo(nodo.getDerecho(), id);
        }
    }

    public void eliminar(Integer id) {
        if (id == null || id <= 0 || raiz == null) {
            return;
        }
        raiz = eliminarNodo(raiz, id);
    }

    private NodoAVL eliminarNodo(NodoAVL nodo, int id) {
        if (nodo == null) {
            return null;
        }

        int idActual = nodo.getDato().getId();

        if (id < idActual) {
            nodo.setIzquierdo(eliminarNodo(nodo.getIzquierdo(), id));
        } else if (id > idActual) {
            nodo.setDerecho(eliminarNodo(nodo.getDerecho(), id));
        } else {
            if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
                return null;
            }

            if (nodo.getIzquierdo() == null) {
                return nodo.getDerecho();
            }

            if (nodo.getDerecho() == null) {
                return nodo.getIzquierdo();
            }

            NodoAVL sucesor = obtenerMinimo(nodo.getDerecho());
            nodo.setDato(sucesor.getDato());
            nodo.setDerecho(eliminarNodo(nodo.getDerecho(), sucesor.getDato().getId()));
        }

        actualizarAltura(nodo);
        return rebalancear(nodo);
    }

    private NodoAVL obtenerMinimo(NodoAVL nodo) {
        while (nodo.getIzquierdo() != null) {
            nodo = nodo.getIzquierdo();
        }
        return nodo;
    }

    public int obtenerAltura(NodoAVL nodo) {
        if (nodo == null) {
            return 0;
        }
        return nodo.getAltura();
    }

    public int factorEquilibrio(NodoAVL nodo) {
        if (nodo == null) {
            return 0;
        }
        return obtenerAltura(nodo.getDerecho()) - obtenerAltura(nodo.getIzquierdo());
    }

    private void actualizarAltura(NodoAVL nodo) {
        int alturaIzquierda = obtenerAltura(nodo.getIzquierdo());
        int alturaDerecha = obtenerAltura(nodo.getDerecho());
        nodo.setAltura(1 + Math.max(alturaIzquierda, alturaDerecha));
    }

    private NodoAVL rebalancear(NodoAVL nodo) {
        int fe = factorEquilibrio(nodo);

        if (fe > 1) {
            if (factorEquilibrio(nodo.getDerecho()) < 0) {
                nodo.setDerecho(rotacionDerecha(nodo.getDerecho()));
            }
            return rotacionIzquierda(nodo);
        }

        if (fe < -1) {
            if (factorEquilibrio(nodo.getIzquierdo()) > 0) {
                nodo.setIzquierdo(rotacionIzquierda(nodo.getIzquierdo()));
            }
            return rotacionDerecha(nodo);
        }

        return nodo;
    }

    private NodoAVL rotacionIzquierda(NodoAVL nodo) {
        NodoAVL nuevaRaiz = nodo.getDerecho();
        NodoAVL subarbol = nuevaRaiz.getIzquierdo();

        nuevaRaiz.setIzquierdo(nodo);
        nodo.setDerecho(subarbol);

        actualizarAltura(nodo);
        actualizarAltura(nuevaRaiz);

        return nuevaRaiz;
    }

    private NodoAVL rotacionDerecha(NodoAVL nodo) {
        NodoAVL nuevaRaiz = nodo.getIzquierdo();
        NodoAVL subarbol = nuevaRaiz.getDerecho();

        nuevaRaiz.setDerecho(nodo);
        nodo.setIzquierdo(subarbol);

        actualizarAltura(nodo);
        actualizarAltura(nuevaRaiz);

        return nuevaRaiz;
    }

    public String mostrarInOrden() {
        StringBuilder texto = new StringBuilder();
        recorrerInOrden(raiz, texto);
        return texto.toString();
    }

    private void recorrerInOrden(NodoAVL nodo, StringBuilder texto) {
        if (nodo != null) {
            recorrerInOrden(nodo.getIzquierdo(), texto);
            texto.append(nodo.getDato().toString()).append("\n");
            recorrerInOrden(nodo.getDerecho(), texto);
        }
    }

    public String mostrarPreOrden() {
        StringBuilder texto = new StringBuilder();
        recorrerPreOrden(raiz, texto);
        return texto.toString();
    }

    private void recorrerPreOrden(NodoAVL nodo, StringBuilder texto) {
        if (nodo != null) {
            texto.append(nodo.getDato().toString()).append("\n");
            recorrerPreOrden(nodo.getIzquierdo(), texto);
            recorrerPreOrden(nodo.getDerecho(), texto);
        }
    }

    public String mostrarPostOrden() {
        StringBuilder texto = new StringBuilder();
        recorrerPostOrden(raiz, texto);
        return texto.toString();
    }

    private void recorrerPostOrden(NodoAVL nodo, StringBuilder texto) {
        if (nodo != null) {
            recorrerPostOrden(nodo.getIzquierdo(), texto);
            recorrerPostOrden(nodo.getDerecho(), texto);
            texto.append(nodo.getDato().toString()).append("\n");
        }
    }

    public int profundidad() {
        return calcularProfundidad(raiz);
    }

    private int calcularProfundidad(NodoAVL nodo) {
        if (nodo == null) {
            return 0;
        }

        int profundidadIzquierda = calcularProfundidad(nodo.getIzquierdo());
        int profundidadDerecha = calcularProfundidad(nodo.getDerecho());

        if (profundidadIzquierda > profundidadDerecha) {
            return profundidadIzquierda + 1;
        } else {
            return profundidadDerecha + 1;
        }
    }

    public int amplitud() {
        if (raiz == null) {
            return 0;
        }

        int maxAmplitud = 0;
        NodoAVL[] cola = new NodoAVL[contarNodos(raiz)];
        int frente = 0;
        int fin = 0;

        cola[fin++] = raiz;

        while (frente < fin) {
            int nodosPorNivel = fin - frente;

            if (nodosPorNivel > maxAmplitud) {
                maxAmplitud = nodosPorNivel;
            }

            for (int i = 0; i < nodosPorNivel; i++) {
                NodoAVL actual = cola[frente++];

                if (actual.getIzquierdo() != null) {
                    cola[fin++] = actual.getIzquierdo();
                }

                if (actual.getDerecho() != null) {
                    cola[fin++] = actual.getDerecho();
                }
            }
        }

        return maxAmplitud;
    }

    private int contarNodos(NodoAVL nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + contarNodos(nodo.getIzquierdo()) + contarNodos(nodo.getDerecho());
    }

    public void destruirArbol() {
        raiz = null;
    }

    public boolean estaVacio() {
        return raiz == null;
    }

    public String mostrarEstructura() {
        StringBuilder sb = new StringBuilder();

        if (raiz == null) {
            return "Árbol vacío.";
        }

        mostrarEstructuraRec(raiz, "", true, sb);
        String resultado = sb.toString();

        return resultado.isEmpty() ? "Árbol vacío." : resultado;
    }

    private void mostrarEstructuraRec(NodoAVL nodo, String prefijo, boolean esUltimo, StringBuilder sb) {
        if (nodo == null) {
            return;
        }

        sb.append(prefijo);

        if (esUltimo) {
            sb.append("└── ");
        } else {
            sb.append("├── ");
        }

        sb.append(nodo.getDato().getId()).append(" - ").append(nodo.getDato().getNombre()).append("\n");
        mostrarEstructuraRec(nodo.getIzquierdo(), prefijo + (esUltimo ? "    " : "│   "), false, sb);
        mostrarEstructuraRec(nodo.getDerecho(), prefijo + (esUltimo ? "    " : "│   "), true, sb);
    }

    private boolean esValido(Empleado empleado) {
        if (empleado == null || empleado.getId() <= 0) {
            return false;
        }

        if (empleado.getNombre() == null || empleado.getNombre().trim().isEmpty()) {
            return false;
        }

        if (empleado.getPuesto() == null || empleado.getPuesto().trim().isEmpty()) {
            return false;
        }

        if (empleado.getDepartamento() == null || empleado.getDepartamento().trim().isEmpty()) {
            return false;
        }

        if (!Double.isFinite(empleado.getSalario()) || empleado.getSalario() < 0) {
            return false;
        }

        return true;
    }
}
