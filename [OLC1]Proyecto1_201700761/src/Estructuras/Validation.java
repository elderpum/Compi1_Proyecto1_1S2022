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
public class Validation {
    private String valor = "";
    private String expresionRegular = "";
    private String resultado = "";

    public Validation() {
    }
    
    public Validation(String val, String expresion, String result){
        this.valor = val;
        this.expresionRegular = expresion;
        this.resultado = result;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getExpresionRegular() {
        return expresionRegular;
    }

    public void setExpresionRegular(String expresionRegular) {
        this.expresionRegular = expresionRegular;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return "Validation{" + "Valor=" + valor + ", ExpresionRegular=" + expresionRegular + ", Resultado=" + "}"; //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
