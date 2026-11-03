
public class Empleado {

    private int id;
    private String nombre;
    private String puesto;
    private String departamento;
    private double salario;

    public Empleado(int id, String nombre, String puesto, String departamento, double salario) {
        this.id = id;
        this.nombre = nombre;
        this.puesto = puesto;
        this.departamento =  departamento;
        this.salario = salario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    @Override
    public String toString(){
        return id + " - " + nombre + " - " + puesto + " - " + departamento + " - " + salario;
    }
}
