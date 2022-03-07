/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olc1.proyecto1_201700761;

import Analizadores.Lexico;
import Estructuras.AFD;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Map;

/**
 *
 * @author Elder
 */
public class OLC1Proyecto1_201700761 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        main pantalla = new main();
        pantalla.setVisible(true);
    }
    
    //Vamos a hacer unas pruebas 
    public static void interpretar(String path){
        Analizadores.Sintactico parser;
        try{
            parser = new Analizadores.Sintactico(new Analizadores.Lexico(new BufferedReader(new FileReader(path))));
            parser.parse();
            Map<String, AFD> arbol = parser.List_AFD;
            System.out.println(arbol);
        } catch (Exception ex){
            System.out.println("Error fatal en compilacion de la entrada");
            System.out.println("Causa: " + ex.getCause());
        }
    }
}
