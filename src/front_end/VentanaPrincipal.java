package front_end;

import back_end.ArbolAVL;
import back_end.Empleado;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class VentanaPrincipal extends JFrame implements ActionListener {

    private ArbolAVL arbol;

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtPuesto;
    private JTextField txtDepartamento;
    private JTextField txtSalario;

    private JButton btnCrearArbol;
    private JButton btnAgregar;
    private JButton btnBuscar;
    private JButton btnEliminar;
    private JButton btnInOrden;
    private JButton btnPreOrden;
    private JButton btnPostOrden;
    private JButton btnAmplitud;
    private JButton btnProfundidad;
    private JButton btnDestruir;
    private JButton btnVerArbol;

    private JTextArea txtAreaResultados;
    private JScrollPane scrollResultados;
    private VistaArbolPanel vistaArbolPanel;

    public VentanaPrincipal() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Arbol AVL - Empleados");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelDatos = new JPanel(new GridLayout(6, 2, 5, 5));
        panelDatos.setBorder(BorderFactory.createTitledBorder("Datos del Empleado"));

        panelDatos.add(new JLabel("ID:"));
        txtId = new JTextField();
        panelDatos.add(txtId);

        panelDatos.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelDatos.add(txtNombre);

        panelDatos.add(new JLabel("Puesto:"));
        txtPuesto = new JTextField();
        panelDatos.add(txtPuesto);

        panelDatos.add(new JLabel("Departamento:"));
        txtDepartamento = new JTextField();
        panelDatos.add(txtDepartamento);

        panelDatos.add(new JLabel("Salario:"));
        txtSalario = new JTextField();
        panelDatos.add(txtSalario);

        JPanel panelBotonesIzq = new JPanel(new GridLayout(4, 1, 5, 5));
        panelBotonesIzq.setBorder(BorderFactory.createTitledBorder("Arbol"));

        btnCrearArbol = new JButton("Crear arbol");
        btnAgregar = new JButton("Agregar empleado");
        btnBuscar = new JButton("Buscar empleado");
        btnEliminar = new JButton("Eliminar empleado");

        btnCrearArbol.addActionListener(this);
        btnAgregar.addActionListener(this);
        btnBuscar.addActionListener(this);
        btnEliminar.addActionListener(this);

        panelBotonesIzq.add(btnCrearArbol);
        panelBotonesIzq.add(btnAgregar);
        panelBotonesIzq.add(btnBuscar);
        panelBotonesIzq.add(btnEliminar);

        JPanel panelBotonesDer = new JPanel(new GridLayout(7, 1, 5, 5));
        panelBotonesDer.setBorder(BorderFactory.createTitledBorder("Operaciones"));

        btnInOrden = new JButton("Mostrar InOrden");
        btnPreOrden = new JButton("Mostrar PreOrden");
        btnPostOrden = new JButton("Mostrar PostOrden");
        btnAmplitud = new JButton("Calcular amplitud");
        btnProfundidad = new JButton("Calcular profundidad");
        btnDestruir = new JButton("Destruir arbol");
        btnVerArbol = new JButton("Ver arbol");

        btnInOrden.addActionListener(this);
        btnPreOrden.addActionListener(this);
        btnPostOrden.addActionListener(this);
        btnAmplitud.addActionListener(this);
        btnProfundidad.addActionListener(this);
        btnDestruir.addActionListener(this);
        btnVerArbol.addActionListener(this);

        panelBotonesDer.add(btnInOrden);
        panelBotonesDer.add(btnPreOrden);
        panelBotonesDer.add(btnPostOrden);
        panelBotonesDer.add(btnAmplitud);
        panelBotonesDer.add(btnProfundidad);
        panelBotonesDer.add(btnDestruir);
        panelBotonesDer.add(btnVerArbol);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelDatos, BorderLayout.CENTER);
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        panelBotones.add(panelBotonesIzq);
        panelBotones.add(panelBotonesDer);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        txtAreaResultados = new JTextArea();
        txtAreaResultados.setEditable(false);
        scrollResultados = new JScrollPane(txtAreaResultados);
        scrollResultados.setBorder(BorderFactory.createTitledBorder("Resultados / Mensajes"));

        vistaArbolPanel = new VistaArbolPanel();

        JPanel panelInferior = new JPanel(new GridLayout(2, 1, 5, 5));
        panelInferior.add(scrollResultados);
        panelInferior.add(vistaArbolPanel);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelInferior, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCrearArbol) {
            crearArbol();
        } else if (e.getSource() == btnAgregar) {
            agregarEmpleado();
        } else if (e.getSource() == btnBuscar) {
            buscarEmpleado();
        } else if (e.getSource() == btnEliminar) {
            eliminarEmpleado();
        } else if (e.getSource() == btnInOrden) {
            mostrarInOrden();
        } else if (e.getSource() == btnPreOrden) {
            mostrarPreOrden();
        } else if (e.getSource() == btnPostOrden) {
            mostrarPostOrden();
        } else if (e.getSource() == btnAmplitud) {
            calcularAmplitud();
        } else if (e.getSource() == btnProfundidad) {
            calcularProfundidad();
        } else if (e.getSource() == btnDestruir) {
            destruirArbol();
        } else if (e.getSource() == btnVerArbol) {
            verArbol();
        }
    }

    private void crearArbol() {
        arbol = new ArbolAVL();
        vistaArbolPanel.limpiar();
        txtAreaResultados.setText("Arbol AVL inicializado y listo para utilizarse." + estadoAltura());
    }

    private void agregarEmpleado() {
        if (arbol == null) {
            txtAreaResultados.setText("No se ha creado el arbol. Presione Crear arbol primero.");
            return;
        }

        String idStr = txtId.getText().trim();
        String nombre = txtNombre.getText().trim();
        String puesto = txtPuesto.getText().trim();
        String departamento = txtDepartamento.getText().trim();
        String salarioStr = txtSalario.getText().trim();

        if (idStr.isEmpty() || nombre.isEmpty() || puesto.isEmpty() || departamento.isEmpty() || salarioStr.isEmpty()) {
            txtAreaResultados.setText("Todos los campos son obligatorios.");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            txtAreaResultados.setText("ID invalido. Debe ser un numero entero.");
            return;
        }

        if (id <= 0) {
            txtAreaResultados.setText("ID invalido. Debe ser un numero entero mayor que cero.");
            return;
        }

        if (arbol.buscar(id) != null) {
            txtAreaResultados.setText("Ya existe un empleado con ese ID.");
            return;
        }

        double salario;
        try {
            salario = Double.parseDouble(salarioStr);
        } catch (NumberFormatException ex) {
            txtAreaResultados.setText("Salario invalido. Debe ser un numero.");
            return;
        }

        if (!Double.isFinite(salario) || salario < 0) {
            txtAreaResultados.setText("Salario invalido. Debe ser un numero finito y no negativo.");
            return;
        }

        Empleado empleado = new Empleado(id, nombre, puesto, departamento, salario);
        arbol.insertar(empleado);

        if (arbol.buscar(id) == null) {
            txtAreaResultados.setText("No se pudo agregar el empleado. Verifique los datos ingresados.");
            return;
        }

        vistaArbolPanel.mostrarArbol(arbol);
        txtAreaResultados.setText("Empleado agregado correctamente:\n" + empleado.toString()
                + "\n\nEl arbol AVL se rebalanceo automaticamente si fue necesario."
                + "\nEstructura actualizada en 'Vista del arbol'." + estadoAltura());
    }

    private void buscarEmpleado() {
        if (arbol == null) {
            txtAreaResultados.setText("No se ha creado el arbol. Presione Crear arbol primero.");
            return;
        }

        String idStr = txtId.getText().trim();
        if (idStr.isEmpty()) {
            txtAreaResultados.setText("Ingrese el ID para buscar.");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            txtAreaResultados.setText("ID invalido. Debe ser un numero entero.");
            return;
        }

        if (id <= 0) {
            txtAreaResultados.setText("ID invalido. Debe ser un numero entero mayor que cero.");
            return;
        }

        Empleado encontrado = arbol.buscar(id);
        if (encontrado == null) {
            txtAreaResultados.setText("No se encontro el empleado con ese ID." + estadoAltura());
            return;
        }

        txtAreaResultados.setText("Empleado encontrado:\n" + encontrado.toString() + estadoAltura());
    }

    private void eliminarEmpleado() {
        if (arbol == null) {
            txtAreaResultados.setText("No se ha creado el arbol. Presione Crear arbol primero.");
            return;
        }

        if (arbol.estaVacio()) {
            txtAreaResultados.setText("El arbol esta vacio.");
            return;
        }

        String idStr = txtId.getText().trim();
        if (idStr.isEmpty()) {
            txtAreaResultados.setText("Ingrese el ID para eliminar.");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            txtAreaResultados.setText("ID invalido. Debe ser un numero entero.");
            return;
        }

        if (id <= 0) {
            txtAreaResultados.setText("ID invalido. Debe ser un numero entero mayor que cero.");
            return;
        }

        Empleado encontrado = arbol.buscar(id);
        if (encontrado == null) {
            txtAreaResultados.setText("No se encontro el empleado con ese ID." + estadoAltura());
            return;
        }

        arbol.eliminar(id);
        vistaArbolPanel.mostrarArbol(arbol);
        txtAreaResultados.setText("Empleado eliminado correctamente.\n" + encontrado.toString()
                + "\n\nEl arbol AVL se rebalanceo automaticamente si fue necesario."
                + "\nEstructura actualizada en 'Vista del arbol'." + estadoAltura());
    }

    private void mostrarInOrden() {
        if (arbol == null) {
            txtAreaResultados.setText("No se ha creado el arbol. Presione Crear arbol primero.");
            return;
        }
        if (arbol.estaVacio()) {
            txtAreaResultados.setText("El arbol esta vacio.");
            return;
        }
        txtAreaResultados.setText("Recorrido InOrden (ascendente por ID):\n" + arbol.mostrarInOrden() + estadoAltura());
    }

    private void mostrarPreOrden() {
        if (arbol == null) {
            txtAreaResultados.setText("No se ha creado el arbol. Presione Crear arbol primero.");
            return;
        }
        if (arbol.estaVacio()) {
            txtAreaResultados.setText("El arbol esta vacio.");
            return;
        }
        txtAreaResultados.setText("Recorrido PreOrden:\n" + arbol.mostrarPreOrden() + estadoAltura());
    }

    private void mostrarPostOrden() {
        if (arbol == null) {
            txtAreaResultados.setText("No se ha creado el arbol. Presione Crear arbol primero.");
            return;
        }
        if (arbol.estaVacio()) {
            txtAreaResultados.setText("El arbol esta vacio.");
            return;
        }
        txtAreaResultados.setText("Recorrido PostOrden:\n" + arbol.mostrarPostOrden() + estadoAltura());
    }

    private void calcularAmplitud() {
        if (arbol == null) {
            txtAreaResultados.setText("No se ha creado el arbol. Presione Crear arbol primero.");
            return;
        }
        if (arbol.estaVacio()) {
            txtAreaResultados.setText("El arbol esta vacio.");
            return;
        }
        txtAreaResultados.setText("Amplitud maxima (maximo de nodos por nivel): " + arbol.amplitud() + estadoAltura());
    }

    private void calcularProfundidad() {
        if (arbol == null) {
            txtAreaResultados.setText("No se ha creado el arbol. Presione Crear arbol primero.");
            return;
        }
        if (arbol.estaVacio()) {
            txtAreaResultados.setText("El arbol esta vacio.");
            return;
        }
        txtAreaResultados.setText("Profundidad maxima del arbol (en niveles): " + arbol.profundidad() + estadoAltura());
    }

    private void destruirArbol() {
        if (arbol == null) {
            txtAreaResultados.setText("El arbol ya no existe o ya fue destruido.");
            if (vistaArbolPanel != null) {
                vistaArbolPanel.limpiar();
            }
            return;
        }
        arbol.destruirArbol();
        arbol = null;
        txtAreaResultados.setText("Arbol destruido. Se ha eliminado toda la informacion." + estadoAltura());
        if (vistaArbolPanel != null) {
            vistaArbolPanel.limpiar();
        }
    }

    private void verArbol() {
        if (vistaArbolPanel != null) {
            vistaArbolPanel.mostrarArbol(arbol);
        }
    }

    private String estadoAltura() {
        if (arbol == null) {
            return "\n\nAltura actual: arbol no creado.";
        }
        return "\n\nAltura actual del arbol AVL: " + arbol.profundidad() + " nivel(es).";
    }
}