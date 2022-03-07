/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
        generarDatos();
        CrearTransiciones();
    }
    
    public void generarDatos(){
        
    }
    
    public Hoja _generarDatos(Hoja nodo){
        if (nodo.izquierda != null) {
            nodo.izquierda = _generarDatos(nodo.izquierda);
        }
        
        if (nodo.derecha != null) {
            nodo.derecha = _generarDatos(nodo.derecha);
        }
        
        if ("hoja".equalsIgnoreCase(nodo.tipo)) {
            nodo.primeros = this.identificador + "";
            nodo.ultimos = this.identificador + "";
            nodo.id = this.identificador;
            if (listaAlfabeto.get(nodo.dato) == null && !nodo.dato.equals("#")) {
                listaAlfabeto.put(nodo.dato, nodo.dato);
                this.alfabeto.add(nodo.dato);
            }
            String[] datos = {nodo.dato, this.identificador + "", ""};
            this.siguientes.put(this.identificador, datos);
            this.identificador++;
        } else {
            nodo = tipo(nodo);
        }
        if (".".equals(nodo.tipo) || "*".equals(nodo.tipo) || "+".equals(nodo.tipo)) {
            siguientes(nodo);
        }
        return nodo;
    }
    
    public Hoja tipo(Hoja nodo) {
        switch(nodo.tipo) {
            case ".":
                if (nodo.izquierda.anulable) {
                    nodo.primeros = nodo.izquierda.primeros + "," + nodo.derecha.primeros;
                } else {
                    nodo.primeros = nodo.primeros;
                }
                if (nodo.derecha.anulable) {
                    nodo.ultimos = nodo.izquierda.ultimos + "," + nodo.derecha.ultimos;
                } else {
                    nodo.ultimos = nodo.derecha.ultimos;
                }
                break;
            case "|":
                nodo.primeros = nodo.izquierda.primeros + "," + nodo.derecha.primeros;
                nodo.ultimos = nodo.izquierda.ultimos + "," + nodo.derecha.ultimos;
                break;
            case "*":
                nodo.primeros = nodo.izquierda.primeros;
                nodo.ultimos = nodo.izquierda.ultimos;
                break;
            case "+":
                nodo.primeros = nodo.izquierda.primeros;
                nodo.ultimos = nodo.izquierda.ultimos;
                break;
            default:
                nodo.primeros = nodo.izquierda.primeros;
                nodo.ultimos = nodo.izquierda.ultimos;
                break;
        }
        return nodo;
    }
    
    public void siguientes(Hoja nodo) {
        String[] anteriores = nodo.izquierda.ultimos.split(",");
        switch(nodo.tipo){
            case ".":
                for (String ante: anteriores){
                    String [] lista = this.siguientes.get(Integer.parseInt(ante));
                    if(lista[2].isEmpty()){
                        lista[2] = nodo.derecha.primeros;
                    }else{
                        lista[2] = lista[2]+","+nodo.derecha.primeros;
                    }
                    this.siguientes.replace(Integer.parseInt(ante), lista);    
                }
                break;
            default:
                for (String ante: anteriores){
                    String [] lista = this.siguientes.get(Integer.parseInt(ante));
                    if(lista[2].isEmpty()){
                        lista[2] = nodo.izquierda.primeros;
                    }else{
                        lista[2] = lista[2]+","+nodo.izquierda.primeros;
                    }
                    this.siguientes.replace(Integer.parseInt(ante), lista);
                }
                break;
        }
    }
    
    public void GraficaArbol(){
        this.num = 1;
        File directorio = new File("./ARBOLES_201700761");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        FileWriter fichero;
        PrintWriter escritor;
        try{
            fichero = new FileWriter("./ARBOLES_201700761/" + nombre + ".dot");
            escritor = new PrintWriter(fichero);
            escritor.print("digraph grafica{\n"
                    + "rankdir=TB;\n"
                    + "forcelabels=true;\n"
                    + "node [shape = plaintext];\n");
            escritor.print(_GraficaArbol(this.cabeza, 0));
            escritor.print("\n}");
            fichero.close();
            Runtime rt = Runtime.getRuntime();
            rt.exec("dot -Tjpg -o ./ARBOLES_201700761/" + nombre + ".jpg graf ./ARBOLES_201700761/" + nombre + ".dot");
        } catch (IOException e) {
            System.out.println("Error al crear el grafo");
        }
    }
    
    public String _GraficaArbol(Hoja nodo, int num){
        int cod = this.num;
        int cod2 = 0;
        int cod3 = 0;
        String anu = "";
        if (nodo.anulable) {
            anu = "A";
        } else {
            anu = "N";
        }
        String tabla = "<<table border = '0' cellboder = '1' CELLSPACIONG='0'>\n"+
                "<tr>\n"
                + "<td></td>\n"
                + "<td>"+anu+"</td>\n"
                + "<td></td>\n"
                + "</tr>\n"+
                "<tr>\n"
                + "<td>"+nodo.primeros+"</td>\n"
                + "<td border='1'>"+nodo.dato+"</td>\n"
                + "<td>"+nodo.ultimos+"</td>"
                + "</tr>\n"+
                "<tr>\n"
                + "<td></td>\n"
                + "<td>"+nodo.id+"</td>\n"
                + "<td></td>\n"
                + "</tr>\n"
                + "</table>>";
        String text = "nodo"+this.num+" [label = "+tabla+"];\n";
        this.num++;
        if (nodo.izquierda != null) {
            cod2 = this.num;
            text += _GraficaArbol(nodo.izquierda, this.num);
            this.num++;
        }
        if (nodo.derecha != null) {
            cod3 = this.num;
            text += _GraficaArbol(nodo.derecha, this.num);
            this.num++;
        }
        if (!"hoja".equalsIgnoreCase(nodo.tipo)) {
            if (cod2 != 0) {
                text += "nodo" + cod + "->" + "nodo" + (cod2) + "\n";
            }
            if (cod3 != 0) {
                text += "nodo" + cod + "->" + "nodo" + (cod3) + "\n";
            }
        }
        return text;
    }
    
    public void GraficarSiguientes() {
        File directorio = new File("./SIGUIENTES_201700761");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        FileWriter fichero;
        PrintWriter escritor;
        try {
            fichero = new FileWriter("./SIGUIENTES_201700761/" + nombre + ".dot");
            escritor = new PrintWriter(fichero);
            escritor.print("digraph grafica{\n"
                + "rankdir=LR;\n"
                + "forcelabels= true;\n"
                + "node [shape = plain];\n");
            String td = "";
            for(String[] dato: this.siguientes.values()){
                td+="<tr>\n"
                    + "<td>"+dato[0]+"</td>\n"
                    + "<td>"+dato[1]+"</td>\n"
                    + "<td>"+dato[2]+"</td>\n"
                    + "</tr>\n";
            }
            String tabla = "<<table border = '1' cellboder = '1' cellspacing='0' cellpadding='10'>\n"
                    + "<tr>\n"
                    + "<td COLSPAN='2'>HOJA</td>\n"
                    + "<td>Siguientes</td>\n"
                    + "</tr>\n"
                    + td
                    + "</table>>";
            String text = "nodo" + this.num + " [label = " + tabla + "];\n";
            escritor.print(text);
            escritor.print("\n}");
            fichero.close();
            Runtime rt = Runtime.getRuntime();
            rt.exec( "dot -Tjpg -o ./SIGUIENTES_201700761/" + nombre + ".jpg graf ./SIGUIENTES_201700761/" + nombre + ".dot");
        } catch (IOException e) {
            System.out.println("Error al crear la grafica de siguientes");
        }
    }
    
    public void GraficarTransiciones() {
        File directorio = new File("./TRANSICIONES_201700761");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        FileWriter fichero;
        PrintWriter escritor;
        try
        {
            fichero = new FileWriter("./TRANSICIONES_201700761/" + nombre + ".dot");
            escritor = new PrintWriter(fichero);
            escritor.print("digraph grafica{\n"
                + "rankdir=LR;\n"
                + "forcelabels= true;\n"
                + "node [shape = plain];\n");
            String td = "";
            for(String[] y: listaTransiciones.values()){
                td+="<tr>\n";
                td+="<td> " + y[0] + " {" + y[1] + "} </td>\n";
                for (String x: alfabeto) {
                    boolean encontrado = false;
                    for(String[] dato: this.transiciones){
                        if (dato[2].equals(x) && y[0].equals(dato[0])) {
                            td += "<td> " + dato[1] + " </td>\n";
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        td += "<td> -- </td>\n";
                    }
                }
                td += "</tr>\n";
            }
            String tabla = "<<table border = '1' cellboder = '1' cellspacing='0' cellpadding='10'>\n"
                    + "<tr>\n"
                    + "<td>ESTADO</td>\n";
            for (String x: alfabeto) {
                tabla += "<td>" + x + "</td>\n";
            }
            tabla += "</tr>\n"
                    + td
                    + "</table>>";
            String text = "nodo" + this.num + " [label = " + tabla + "];\n";
            escritor.print(text);
            escritor.print("\n}");
            fichero.close();
            Runtime rt = Runtime.getRuntime();
            rt.exec( "dot -Tjpg -o ./TRANSICIONES_201700761/" + nombre + ".jpg graf ./TRANSICIONES_201700761/" + nombre + ".dot");
        }catch(IOException e){
            System.out.println("error al crear la grafica de transiciones");
        }
    }
    
    public void CrearTransiciones(){
        String[] data = {"S0", this.cabeza.primeros};
        this.listaTransiciones.put(this.cabeza.primeros, data);
        _CrearTransiciones(this.listaTransiciones.get(this.cabeza.primeros));
    }
    
    public void _CrearTransiciones(String[] T){
        boolean ter = false;
        List<String[]> transicion = new ArrayList<>();
        for(String y: this.alfabeto){
            Map<String, String> tran = new HashMap<>();
            String trans = "";
            for(String k: T[1].split(",")){
                String[] sig = this.siguientes.get(Integer.parseInt(k));
                if(y.equals(sig[0])){
                    for(String g: sig[2].split(",")){
                        if(tran.get(g) == null){
                            if(Integer.parseInt(g) == identificador-1){
                                ter = true;
                            }
                            tran.put(g, g);
                            if(trans.isEmpty()){
                                trans = g;
                            }else{ trans+= ","+g;}
                        }
                    }
                }
            }
            if(this.listaTransiciones.get(trans)==null && !trans.isEmpty()){
                String[] D = {"S"+this.listaTransiciones.size(), trans};
                String[] dd = {T[0],D[0],y};
                transiciones.add(dd);
                transicion.add(D);
                if(ter){terminales.add(D[0]);}
                this.listaTransiciones.put(trans,D);
            }else{
                if(!trans.isEmpty()){
                    String [] N = this.listaTransiciones.get(trans);
                    String [] dd = {T[0],N[0],y};
                    transiciones.add(dd);
                }
            }
        }
        for(String[] G: transicion){
            _CrearTransiciones(G);
        }
    }
    
    public void GraficarAFD(){
        File directorio = new File("./AFD_201700761");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        FileWriter fichero;
        PrintWriter escritor;
        try
        {
            fichero = new FileWriter("./AFD_201700761/" + nombre + ".dot");
            escritor = new PrintWriter(fichero);
            escritor.print("digraph grafica{\n"
                + "rankdir=LR;\n"
                + "forcelabels= true;\n"
                + "node [shape = circle];\n");
            for(int x=0; x<this.listaTransiciones.size();x++){
                if (terminales.contains("S"+x)){
                    escritor.print("S" + x + " [label = \"" + "S" + x+ "\", shape = doublecircle];\n");
                }else{
                    escritor.print("S" + x + " [label = \"" + "S" + x+"\"];\n");
                }
            }
            for(String[]k: transiciones){
                String dat = k[2];
                if("\\n".equals(k[2])){
                    dat = "\\\\n";
                }
                if(" ".equals(k[2])){
                    dat = "\\\" \\\"";
                }
                escritor.print(k[0] + "->" + k[1] + "[label=\"" + dat + "\"]\n");
            }
            escritor.print("\n}");
            fichero.close();
            Runtime rt = Runtime.getRuntime();
            rt.exec( "dot -Tjpg -o ./AFD_201700761/" + nombre+".jpg graf ./AFD_201700761/" + nombre + ".dot");
        }catch(IOException e){
            System.out.println("Error al crear la grafica del AFD");
        }
    }
    
    public boolean VerificarConj(int caracter, String x){
        if (x.split("~").length == 2) {
            int max = x.charAt(2);
            int min = x.charAt(0);
            if (min > max) {
                int aux = max;
                min = max;
                max = aux;
            }
            if ((int)caracter >= min && (int)caracter <= max) {
                return true;
            }
            return false;
        } else {
            for (String g: x.split(",")) {
                int val = (int)g.charAt(0);
                if (caracter == val) {
                    return true;
                }
            }
            return false;
        }
    }
    
    public boolean ValidarCadena(String cadena) {
        String actual = "S0";
        boolean respuesta = false;
        for (int i = 0; i < cadena.length(); i++) {
            respuesta = false;
            char caracter = cadena.charAt(i);
            for (String[] estado: this.transiciones) {
                if (caracter == '\\') {
                    if ((i + 1) < cadena.length()) {
                        if (cadena.charAt(i + 1) == '\'') {
                            caracter = '\'';
                            i++;
                        } else if (cadena.charAt(i + 1) == 'n') {
                            caracter = '\n';
                            i++;
                        } else if (cadena.charAt(i + 1) == '\"') {
                            caracter = '\"';
                            i++;
                        }
                    }
                }
                if (estado[0].equals(actual)) {
                    String alfabeto = estado[2];
                    if (CONJ.get(estado[2]) != null) {
                        actual = estado[1];
                        respuesta = VerificarConj((int)caracter, CONJ.get(estado[2]));
                    } else {
                        char alfa = estado[2].charAt(0);
                        if (estado[2].length() == 2 && alfa == '\\') {
                            if (estado[2].charAt(i) == '\'') {
                                alfa = '\'';
                            } else if (estado[2].charAt(i) == 'n') {
                                alfa = '\n';
                            } else if (estado[2].charAt(i) == '\"') {
                                alfa = '\"';
                            }
                        }
                        if ((int)caracter == (int)alfa) {
                            actual = estado[1];
                            respuesta = true;
                        } else {
                            respuesta = false;
                        }
                    }
                }
                if (respuesta) {
                    break;
                }
            }
            if (!respuesta) {
                break;
            }
        }
        if (respuesta) {
            System.out.println("Cadena: " + cadena + " es valida con la expresion: " + this.nombre);
        } else {
            System.out.println("Cadena: " + cadena + " no es valida con la expresion: " + this.nombre);
        }
        return respuesta;
    }
}
