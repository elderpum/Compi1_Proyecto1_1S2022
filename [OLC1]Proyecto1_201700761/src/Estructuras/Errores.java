/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

/**
 *
 * @author Elder
 */
public class Errores {
    public int numero = 0;
    public String tipo;
    public String descripcion;
    public int linea;
    public int colummna;
    
    public Errores(int num, String tipo, String desc, int linea, int col){
        this.numero = num;
        this.tipo = tipo;
        this.descripcion = desc;
        this.linea = linea;
        this.colummna = col;
    }
}
