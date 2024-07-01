/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Matricula;

import Alumno.AlumnoBean;
import java.beans.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EventListener;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author diego
 */
public class MatriculaBean implements Serializable {

    protected String DNI;

    protected String NombreModulo;

    protected String Curso;

    protected double Nota;

    /**
     * Get the value of Nota
     *
     * @return the value of Nota
     */
    public double getNota() {
        return Nota;
    }

    /**
     * Set the value of Nota
     *
     * @param Nota new value of Nota
     */
    public void setNota(double Nota) {
        this.Nota = Nota;
    }

    /**
     * Get the value of Curso
     *
     * @return the value of Curso
     */
    public String getCurso() {
        return Curso;
    }

    /**
     * Set the value of Curso
     *
     * @param Curso new value of Curso
     */
    public void setCurso(String Curso) {
        this.Curso = Curso;
    }

    /**
     * Get the value of NombreModulo
     *
     * @return the value of NombreModulo
     */
    public String getNombreModulo() {
        return NombreModulo;
    }

    /**
     * Set the value of NombreModulo
     *
     * @param NombreModulo new value of NombreModulo
     */
    public void setNombreModulo(String NombreModulo) {
        this.NombreModulo = NombreModulo;
    }

    /**
     * Get the value of DNI
     *
     * @return the value of DNI
     */
    public String getDNI() {
        return DNI;
    }

    /**
     * Set the value of DNI
     *
     * @param DNI new value of DNI
     */
    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    private class Matricula {

        String DNI;
        String NombreModulo;
        String Curso;
        double Nota;

        public Matricula() {
        }

        public Matricula(String nDNI, String nNombreModulo, String nCurso, double nNota) {
            this.DNI = nDNI;
            this.NombreModulo = nNombreModulo;
            this.Curso = nCurso;
            this.Nota = nNota;
        }

    }

    private Vector Matriculas = new Vector();
    
    public int tamanioVector(){
        int longVector = Matriculas.size();
        return longVector;
    };

    private void recargarFilas() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/alumnos", "root", "diego");
            java.sql.Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select * from matriculas");
            while (rs.next()) {
                Matricula m = new Matricula(rs.getString("DNI"),
                        rs.getString("NombreModulo"),
                        rs.getString("Curso"),
                        rs.getDouble("Nota"));
                Matriculas.add(m);
            }
            Matricula m = new Matricula();
            m = (Matricula) Matriculas.elementAt(1);
            this.DNI = m.DNI;
            this.NombreModulo = m.NombreModulo;
            this.Curso = m.Curso;
            this.Nota = m.Nota;
            rs.close();
            con.close();
        } catch (SQLException ex) {
            this.DNI = "";
            this.NombreModulo = "";
            this.Curso = "";
            this.Nota = 0.0;
            Logger.getLogger(MatriculaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void filasAgregadas() {
        if (!Matriculas.isEmpty()) {
            receptorTodos.capturarModoTodos(new ModoTodosEvent(this));
        }
    }

    public void unDNI() {
        receptorUno.capturarModoUno(new ModoUnoEvent(this));
    }

    public void seleccionarFila(int i) {

        if (i <= Matriculas.size()) {
            Matricula m = new Matricula();
            m = (Matricula) Matriculas.elementAt(i);
            this.DNI = m.DNI;
            this.NombreModulo = m.NombreModulo;
            this.Curso = m.Curso;
            this.Nota = m.Nota;

        } else {
            this.DNI = "";
            this.NombreModulo = "";
            this.Curso = "";
            this.Nota = 0.0;
        }
    }

    public void recargarDNI(String nDNI) {
        Matricula m = new Matricula();
        int i = 0;

        this.DNI = "";
        this.NombreModulo = "";
        this.Curso = "";
        this.Nota = 0.0;

        while (this.DNI.equals("") && i <= Matriculas.size()) {
            m = (Matricula) Matriculas.elementAt(i);
            if (m.DNI.equals(nDNI)) {
                this.DNI = "";
                this.NombreModulo = "";
                this.Curso = "";
                this.Nota = 0.0;
            }
        }
    }

    public void addMatricula() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/alumnos", "root", "diego");
            PreparedStatement s = con.prepareStatement("insert into matriculas values (?,?,?,?)");

            s.setString(1, DNI);
            s.setString(2, NombreModulo);
            s.setString(3, Curso);
            s.setDouble(4, Nota);

            s.executeUpdate();
            recargarFilas();
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        receptorAnadir.capturarAnadir(new AnadirEvent(this));
    }

    // Modo Todos
    private ModoTodosListener receptorTodos;

    public class ModoTodosEvent extends java.util.EventObject {

        // constructor
        public ModoTodosEvent(Object source) {
            super(source);
        }
    }

    //Define la interfaz para el nuevo tipo de evento
    public interface ModoTodosListener extends EventListener {

        public void capturarModoTodos(ModoTodosEvent ev);
    }

    public void addModoTodosListener(ModoTodosListener receptorTodos) {
        this.receptorTodos = receptorTodos;
    }

    public void removeModoTodosListener(ModoTodosListener receptorTodos) {
        this.receptorTodos = null;
    }

    // Modo Uno
    private ModoUnoListener receptorUno;

    public class ModoUnoEvent extends java.util.EventObject {

        // constructor
        public ModoUnoEvent(Object source) {
            super(source);
        }
    }

    //Define la interfaz para el nuevo tipo de evento
    public interface ModoUnoListener extends EventListener {

        public void capturarModoUno(ModoUnoEvent ev);
    }

    public void addModoUnoListener(ModoUnoListener receptorUno) {
        this.receptorUno = receptorUno;
    }

    public void removeModoUnoListener(ModoUnoListener receptorUno) {
        this.receptorUno = null;
    }

    // Evento BD modificada al añadir una matricula
    private AnadirListener receptorAnadir;

    public class AnadirEvent extends java.util.EventObject {

        // constructor
        public AnadirEvent(Object source) {
            super(source);
        }
    }

    //Define la interfaz para el nuevo tipo de evento
    public interface AnadirListener extends EventListener {

        public void capturarAnadir(AnadirEvent ev);
    }

    public void addAnadirListener(AnadirListener receptorAnadir) {
        this.receptorAnadir = receptorAnadir;
    }

    public void removeAnadirListener(AnadirListener receptorAndir) {
        this.receptorAnadir = null;
    }

    public static final String PROP_SAMPLE_PROPERTY = "sampleProperty";

    private String sampleProperty;

    private PropertyChangeSupport propertySupport;

    public MatriculaBean() {
        propertySupport = new PropertyChangeSupport(this);
        try {
            recargarFilas();
        } catch (ClassNotFoundException ex) {
            this.DNI = "";
            this.NombreModulo = "";
            this.Curso = "";
            this.Nota = 0.0;
        }
    }

    public String getSampleProperty() {
        return sampleProperty;
    }

    public void setSampleProperty(String value) {
        String oldValue = sampleProperty;
        sampleProperty = value;
        propertySupport.firePropertyChange(PROP_SAMPLE_PROPERTY, oldValue, sampleProperty);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }

}
