/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaalumnobean;

import Matricula.MatriculaBean;
import Matricula.MatriculaBean.AnadirEvent;
import Matricula.MatriculaBean.AnadirListener;
import Matricula.MatriculaBean.ModoTodosEvent;
import Matricula.MatriculaBean.ModoTodosListener;
import Matricula.MatriculaBean.ModoUnoEvent;
import Matricula.MatriculaBean.ModoUnoListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class AccedeBD implements ModoTodosListener, ModoUnoListener, AnadirListener {

    MatriculaBean matriculas;

    AccedeBD() {
        matriculas = new MatriculaBean();
        matriculas.addModoTodosListener(this);
        matriculas.addModoUnoListener(this);
        matriculas.addAnadirListener(this);
    }

    public void listado() {
        for (int i = 0; i < matriculas.tamanioVector(); i++) {
            matriculas.seleccionarFila(i);
            System.out.println("Alumno " + (i + 1) + "\n\tDNI:" + matriculas.getDNI());
            System.out.println("\tNombre del modulo: " + matriculas.getNombreModulo());
            System.out.println("\tCurso: " + matriculas.getCurso());
            System.out.println("\tNota: " + matriculas.getNota());
        }

        matriculas.filasAgregadas();
    }

    public void unDNI(String nDNI) {

        for (int i = 0; i < matriculas.tamanioVector(); i++) {
            matriculas.seleccionarFila(i);
            String DNI = matriculas.getDNI().toString();
            if (nDNI.equalsIgnoreCase(DNI)) {
                System.out.println("Alumno con DNI:" + matriculas.getDNI());
                System.out.println("\tNombre del modulo: " + matriculas.getNombreModulo());
                System.out.println("\tCurso: " + matriculas.getCurso());
                System.out.println("\tNota: " + matriculas.getNota());
            }
        }
        
        matriculas.unDNI();

    }

    public void anadeMatricula() {
        matriculas.setDNI("87654321X");
        matriculas.setNombreModulo("Programacion Multimedia y Dispositivos Moviles");
        matriculas.setCurso("23-24");
        matriculas.setNota(9.8);
        try {
            matriculas.addMatricula();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AccedeBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void capturarModoTodos(ModoTodosEvent mte) {
        System.out.println("Se han cargado todas las matriculas");
    }

    public void capturarModoUno(ModoUnoEvent mue) {
        System.out.println("Se ha cargado la matricula del alumno con DNI 12345678A");
    }
    
    public void capturarAnadir(AnadirEvent av){
        System.out.println("Se ha añadido una nueva matricula a la base de datos");
    }
    
}
