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
public class Hoja {
    public int id;
    public String dato;
    public boolean anulable;
    public String primeros;
    public String ultimos;
    public Hoja izquierda;
    public Hoja derecha;
    public String tipo;

    public Hoja(String dato, Hoja izquierda, Hoja derecha, String tipo) {
        this.dato = dato;
        this.izquierda = izquierda;
        this.derecha = derecha;
        this.tipo = tipo;
        this.primeros = primeros;
        this.ultimos = ultimos;
        try{
            DefinirAnulables(derecha, izquierda, tipo);
        } catch (Exception e){
            System.out.println("Error al crear anulables");
        }
    }
    
    public void DefinirAnulables(Hoja derecha, Hoja izquierda, String tipo){
        switch(tipo) {
            case "hoja":
                this.anulable = false;
                break;
            case ".":
                this.anulable = derecha.anulable && izquierda.anulable;
                break;
            case "|":
                this.anulable = derecha.anulable && izquierda.anulable;
                break;
            case "?":
                this.anulable = true;
                break;
            case "*":
                this.anulable = true;
                break;
            case "+":
                this.anulable = false;
                break;
            default:
                break;
        }
    }
}
