/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Elder
 */
public class AFD {
    Hoja cabeza;
    int identificador = 1;
    int g = 0;
    Map<String, String> CONJ = new HashMap<>();
    List<String> alfabeto = new ArrayList<>();
    List<String> terminales = new ArrayList<>();
    List<String[]> transiciones = new ArrayList<>();
    Map<String, String[]> listaTransiciones = new HashMap<>();
    Map<String, String> listaAlfabeto = new HashMap<>();
    Map<Integer, String[]> siguientes = new HashMap<>();
    String terminal;
    public String nombre;
    int num = 1;

    public AFD(Hoja cabeza, String nombre, Map<String, String> conjunto) {
        this.cabeza = cabeza;
        this.nombre = nombre;
        this.CONJ.putAll(conjunto);
    }
    
    
}
