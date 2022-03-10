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
    private String Valor="";
    private String ExpresionRegular="";
    private String Resultado="";
    
    public Validation(){
    
    }
    public Validation(String valor, String expre, String result) {
        this.Valor = valor;
        this.ExpresionRegular = expre;
        this.Resultado = result;
    }

    public String getValor() {
        return Valor;
    }

    public void setValor(String Valor) {
        this.Valor = Valor;
    }

    public String getExpresionRegular() {
        return ExpresionRegular;
    }

    public void setExpresionRegular(String ExpresionRegular) {
        this.ExpresionRegular = ExpresionRegular;
    }

    public String getResultado() {
        return Resultado;
    }

    public void setResultado(String Resultado) {
        this.Resultado = Resultado;
    }

    @Override
    public String toString() {
        return "Validation{" + "Valor=" + Valor + ", ExpresionRegular=" + ExpresionRegular + ", Resultado=" + Resultado + '}';
    }
}
