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
    HOJA cabeza;
    int ident = 1;
    AFN afn;
    int g = 0;
    Map<String, String> CONJ = new HashMap<>();
    List<String> alfabeto = new ArrayList<>();
    List<String> terminales = new ArrayList<>();
    List<String[]> transiciones = new ArrayList<>();
    Map<String, String[]> lista_transiciones = new HashMap<>();
    Map<String, String> lista_alfabeto = new HashMap<>();
    Map<Integer, String[]> siguientes = new HashMap<>();
    String terminal;
    public String nombre;
    int num = 1;
    
    public AFD(HOJA cabeza, String nombre, Map<String, String> conjunto){
        this.cabeza = cabeza;
        this.nombre = nombre;
        this.CONJ.putAll(conjunto);
        generarDatos();
        CREARTRANSICIONES();
        CrearAFN();
    }
    
    public void GraficarTodo(){
        GRAFICARARBOL();
        GRAFICARSIGUIENTES();
        GraficarAFD();
        GRAFICARTRANSICIONES();
        this.afn.GenerarArbol();
    }
    
    public void generarDatos(){
        this.cabeza = _generarDatos(this.cabeza);
    }
    
    public HOJA _generarDatos(HOJA nodo){
        
        if (nodo.izquierda!=null){
            nodo.izquierda = _generarDatos(nodo.izquierda);
        }
        
        if(nodo.derecha !=null){
            nodo.derecha = _generarDatos(nodo.derecha);
        }
        
        if("hoja".equalsIgnoreCase(nodo.tipo)){
            nodo.primeros = this.ident +"";
            nodo.ultimos = this.ident +"";
            nodo.identificador = this.ident;
            if(lista_alfabeto.get(nodo.dato)==null && !nodo.dato.equals("#")){
                lista_alfabeto.put(nodo.dato, nodo.dato);
                this.alfabeto.add(nodo.dato);
            }
            String[] datos = {nodo.dato, this.ident+"", ""};
            this.siguientes.put(this.ident,datos);
            this.ident++;
        }
        else{
            nodo = tipo(nodo);
        }
        if(".".equals(nodo.tipo) || "*".equals(nodo.tipo) || "+".equals(nodo.tipo)){
            siguientes(nodo);
        }
        return nodo;
    }
    
    public HOJA tipo(HOJA nodo){
        switch(nodo.tipo){
            case ".":
                if (nodo.izquierda.anulable) {
                    nodo.primeros = nodo.izquierda.primeros+","+nodo.derecha.primeros;
                }else{
                    nodo.primeros = nodo.izquierda.primeros;
                }
                if (nodo.derecha.anulable) {
                    nodo.ultimos = nodo.izquierda.ultimos+","+nodo.derecha.ultimos;
                }else{
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

    public void siguientes(HOJA nodo){
        String[] anteriores;
        anteriores = nodo.izquierda.ultimos.split(",");
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
    
    public void GRAFICARARBOL(){
        this.num = 1;
        File directorio = new File("./ARBOLES_201700761");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        FileWriter fichero;
        PrintWriter escritor;
        try
        {
            fichero = new FileWriter("./ARBOLES_201700761/"+nombre+".dot");
            escritor = new PrintWriter(fichero);
            escritor.print("digraph grafica{\n"
                + "rankdir=TB;\n"
                + "forcelabels= true;\n"
                + "node [shape = plaintext];\n");
            escritor.print(_GRAFICARARBOL(this.cabeza, 0));
            escritor.print("\n}");
            fichero.close();
            Runtime rt = Runtime.getRuntime();
            rt.exec( "dot -Tjpg -o ./ARBOLES_201700761/"+nombre+".jpg graf ./ARBOLES_201700761/"+nombre+".dot");
        }catch(IOException e){
            System.out.println("error al crear la grafica");
        } 
    }
    
    public String _GRAFICARARBOL(HOJA nodo, int num){
        int cod = this.num;
        int cod2 = 0;
        int cod3 = 0;
        String anu = "";
        if (nodo.anulable){anu = "A";}else{anu = "N";}
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
                + "<td>"+nodo.identificador+"</td>\n"
                + "<td></td>\n"
                + "</tr>\n"
                + "</table>>";
        String text = "nodo"+this.num+" [label = "+tabla+"];\n";
        this.num++;
        if(nodo.izquierda!=null){
            cod2 = this.num;
            text+= _GRAFICARARBOL(nodo.izquierda, this.num);
            this.num++;
        }
        if(nodo.derecha!=null){
            cod3 = this.num;
            text+= _GRAFICARARBOL(nodo.derecha, this.num);
            this.num++;
        }
        if(!"hoja".equalsIgnoreCase(nodo.tipo)){
            if(cod2!=0){
                text+= "nodo"+cod+"->"+"nodo"+(cod2)+"\n";
            }
            if(cod3!=0){
                text+= "nodo"+cod+"->"+"nodo"+(cod3)+"\n";
            }
        }
        return text;
    }
    
    public void GRAFICARSIGUIENTES(){
        File directorio = new File("./SIGUIENTES_201700761");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        FileWriter fichero;
        PrintWriter escritor;
        try
        {
            fichero = new FileWriter("./SIGUIENTES_201700761/"+nombre+".dot");
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
            String text = "nodo"+this.num+" [label = "+tabla+"];\n";
            escritor.print(text);
            escritor.print("\n}");
            fichero.close();
            Runtime rt = Runtime.getRuntime();
            rt.exec( "dot -Tjpg -o ./SIGUIENTES_201700761/"+nombre+".jpg graf ./SIGUIENTES_201700761/"+nombre+".dot");
        }catch(IOException e){
            System.out.println("error al crear la grafica");
        }  
    }
    
    public void GRAFICARTRANSICIONES(){
        File directorio = new File("./TRANSICIONES_201700761");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        FileWriter fichero;
        PrintWriter escritor;
        try
        {
            fichero = new FileWriter("./TRANSICIONES_201700761/"+nombre+".dot");
            escritor = new PrintWriter(fichero);
            escritor.print("digraph grafica{\n"
                + "rankdir=LR;\n"
                + "forcelabels= true;\n"
                + "node [shape = plain];\n");
            String td = "";
            for(String[] y: lista_transiciones.values()){
                td+="<tr>\n";
                td+="<td> "+y[0]+" {"+y[1]+"} </td>\n";
                for (String x: alfabeto) {
                    boolean encontrado = false;
                    for(String[] dato: this.transiciones){
                        if (dato[2].equals(x) && y[0].equals(dato[0])) {
                            td+="<td> "+dato[1]+" </td>\n";
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        td+="<td> -- </td>\n";
                    }
                }
                td+= "</tr>\n";
            }
            String tabla = "<<table border = '1' cellboder = '1' cellspacing='0' cellpadding='10'>\n"
                    + "<tr>\n"
                    + "<td>ESTADO</td>\n";
            for (String x: alfabeto) {
                tabla+="<td>"+x+"</td>\n";
            }
            tabla += "</tr>\n"
                    + td
                    + "</table>>";
            String text = "nodo"+this.num+" [label = "+tabla+"];\n";
            escritor.print(text);
            escritor.print("\n}");
            fichero.close();
            Runtime rt = Runtime.getRuntime();
            rt.exec( "dot -Tjpg -o ./TRANSICIONES_201700761/"+nombre+".jpg graf ./TRANSICIONES_201700761/"+nombre+".dot");
        }catch(IOException e){
            System.out.println("error al crear la grafica");
        }
    }
    
    public void CREARTRANSICIONES(){
        String [] data = {"S0",this.cabeza.primeros};
        this.lista_transiciones.put(this.cabeza.primeros,data);
        _CREARTRANSICIONES(this.lista_transiciones.get(this.cabeza.primeros));
    }
    
    public void _CREARTRANSICIONES(String[] T){
        boolean ter = false;
        List<String[]> transicion = new ArrayList<>();
        for(String y: this.alfabeto){
            Map<String, String> tran = new HashMap<>();
            String trans = "";
            for(String k: T[1].split(",")){
                String[] sig = this.siguientes.get(Integer.parseInt(k));
                if(y.equals(sig[0])){
                    for(String g: sig[2].split(",")){
                        if(tran.get(g)==null){
                            if(Integer.parseInt(g) ==ident-1){
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
            if(this.lista_transiciones.get(trans)==null && !trans.isEmpty()){
                String[] D = {"S"+this.lista_transiciones.size(), trans};
                String[] dd = {T[0],D[0],y};
                transiciones.add(dd);
                transicion.add(D);
                if(ter){terminales.add(D[0]);}
                this.lista_transiciones.put(trans,D);
            }else{
                if(!trans.isEmpty()){
                    String [] N = this.lista_transiciones.get(trans);
                    String [] dd = {T[0],N[0],y};
                    transiciones.add(dd);
                }
            }
        }
        for(String[] G: transicion){
            _CREARTRANSICIONES(G);
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
            fichero = new FileWriter("./AFD_201700761/"+nombre+".dot");
            escritor = new PrintWriter(fichero);
            escritor.print("digraph grafica{\n"
                + "rankdir=LR;\n"
                + "forcelabels= true;\n"
                + "node [shape = circle];\n");
            for(int x=0; x<this.lista_transiciones.size();x++){
                if (terminales.contains("S"+x)){
                    escritor.print("S"+x+" [label = \""+"S"+x+"\", shape = doublecircle];\n");
                }else{
                    escritor.print("S"+x+" [label = \""+"S"+x+"\"];\n");
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
                escritor.print(k[0]+"->"+k[1]+"[label=\""+dat+"\"]\n");
            }
            escritor.print("\n}");
            fichero.close();
            Runtime rt = Runtime.getRuntime();
            rt.exec( "dot -Tjpg -o ./AFD_201700761/"+nombre+".jpg graf ./AFD_201700761/"+nombre+".dot");
        }catch(IOException e){
            System.out.println("error al crear la grafica");
        }
    }
    
    public boolean VeriConj(int caracter, String x){
        if(x.split("~").length==2){
            int max = x.charAt(2);
            int min = x.charAt(0);
            if(min>max){
                int aux = max;
                min = max;
                max = aux;
            }
            if((int)caracter >= min && (int)caracter<=max){
                return true;
            }
            return false;
        }
        else{
            for(String g: x.split(",")){
                int val = (int)g.charAt(0);
                if(caracter == val){
                    return true;
                }
            }
            return false;
        }
    }
    
    public boolean ValidarCadena(String cadena){
        String actual = "S0";
        boolean respuesta = false;
        for(int x=0; x<cadena.length(); x++){
            respuesta = false;
            char caracter = cadena.charAt(x);
            for(String[] estado: this.transiciones){
                if(caracter=='\\'){
                    if((x+1)<cadena.length()){
                        if(cadena.charAt(x+1)=='\''){
                            caracter = '\'';
                            x++;
                        }else if(cadena.charAt(x+1)=='n'){
                            caracter = '\n';
                            x++;
                        }else if(cadena.charAt(x+1)=='\"'){
                            caracter = '\"';
                            x++;
                        }
                    }
                }
                if(estado[0].equals(actual)){
                    String alfabeto = estado[2]; 
                    if(CONJ.get(estado[2])!=null){
                        actual = estado[1];
                        respuesta = VeriConj((int)caracter, CONJ.get(estado[2]));
                    }
                    else{
                        char alfa = estado[2].charAt(0);
                        if(estado[2].length()==2 && alfa=='\\'){
                            if(estado[2].charAt(1)=='\''){
                                alfa = '\'';
                            }else if(estado[2].charAt(1)=='n'){
                                alfa = '\n';
                            }else if(estado[2].charAt(1)=='\"'){
                                alfa = '\"';
                            }
                        }
                        if((int)caracter == (int)alfa){
                            actual = estado[1];
                            respuesta = true;
                        }else{respuesta = false;}
                    }
                }
                if(respuesta){break;}
            }
            if(!respuesta){break;}
        }
        if(respuesta){
            System.out.println("cadena: "+cadena+" es valida con la expresion: "+this.nombre);
        }else{
            System.out.println("cadena: "+cadena+" no es valida con la expresion: "+this.nombre);
        }
        return respuesta;
    }
    
    public void CrearAFN(){
        this.afn = new AFN(_CrearAFN(cabeza.izquierda) , nombre);
        afn.CREAR_AFN();
    }
    
    public HOJA_AFN _CrearAFN(HOJA nodo){
        if (!nodo.tipo.equals("hoja")){
            String az = "";
            String ad = "";
            String alfa[] = null;
            HOJA_AFN izquierda = null;
            HOJA_AFN derecha = null;
            if(nodo.izquierda!=null){
                if(!nodo.izquierda.tipo.equals("hoja")){izquierda = _CrearAFN(nodo.izquierda);}
                else{az = nodo.izquierda.dato;}
            }
            if(nodo.derecha!=null){
            if(!nodo.derecha.tipo.equals("hoja")){derecha = _CrearAFN(nodo.derecha);}
            else{ad = nodo.derecha.dato;}}
            
            if (!az.isEmpty() && !ad.isEmpty()) {
                alfa = new String[2];
                alfa[0] = az;
                alfa[1] = ad;
            }else if(!az.isEmpty()){alfa = new String[1]; alfa[0] = az;}
            else if(!ad.isEmpty()){alfa = new String[1]; alfa[0] = ad;}
            HOJA_AFN nueva = new HOJA_AFN(nodo.dato, alfa, izquierda, derecha, nodo.tipo);
            return nueva;
        }
        return new HOJA_AFN(nodo.dato, null, null, null, nodo.tipo);
    }
}