/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pruebaalumnobean;

/**
 *
 * @author usuario
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        AccedeBD gestion = new AccedeBD();
        System.out.println("Todas las matriculas");
        System.out.println("-------------------------------------------------");
        gestion.listado();
        System.out.println("-------------------------------------------------");
        System.out.println("Solo la matricula del DNI 12345678A");
        System.out.println("-------------------------------------------------");
        gestion.unDNI("12345678A");
        System.out.println("-------------------------------------------------");
        System.out.println("Añadir matricula a la base de datos");
        gestion.anadeMatricula();
        
    }

}
