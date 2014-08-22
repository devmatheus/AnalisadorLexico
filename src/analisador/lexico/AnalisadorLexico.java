package analisador.lexico;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;;
import java.util.Map.Entry;
import javax.swing.JOptionPane;

public class AnalisadorLexico 
{
    public static void classifica(String token) {
        
        ArrayList<String> palavrasReservadas = new ArrayList();
        palavrasReservadas.add("if");
        palavrasReservadas.add("else");
        palavrasReservadas.add("while");
        palavrasReservadas.add("for");
        palavrasReservadas.add("do");
        palavrasReservadas.add("for");
        palavrasReservadas.add("int");
        palavrasReservadas.add("float");
        palavrasReservadas.add("double");
        palavrasReservadas.add("char");
        
        ArrayList<String> delimiteadores = new ArrayList();
        delimiteadores.add("(");
        delimiteadores.add(")");
        delimiteadores.add("{");
        delimiteadores.add("}");
        delimiteadores.add("[");
        delimiteadores.add("]");
        delimiteadores.add(";");
        
        ArrayList<String> operadoresRelacionais = new ArrayList();
        operadoresRelacionais.add("<");
        operadoresRelacionais.add(">");
        operadoresRelacionais.add("<=");
        operadoresRelacionais.add(">=");
        operadoresRelacionais.add("!=");
        operadoresRelacionais.add("==");
        
        ArrayList<String> operadoresAritmeticos = new ArrayList();
        operadoresAritmeticos.add("+");
        operadoresAritmeticos.add("-");
        operadoresAritmeticos.add("*");
        operadoresAritmeticos.add("/");
        operadoresAritmeticos.add("=");
        
        HashMap<String,ArrayList> listas = new HashMap();
        listas.put("Palavras Reservadas", palavrasReservadas);
        listas.put("Delimitadores", delimiteadores);
        listas.put("Operadores Relacionais", operadoresRelacionais);
        listas.put("Operadores Aritméticos", operadoresAritmeticos);
        
        int encontrouLista = 0;
        if (token.contains(" ")){
            write.println("espaço em branco");
            token = token.replaceAll("\\s+", "");
        }

        for (Entry<String, ArrayList> entry : listas.entrySet()) {
            if (encontrouLista==1) break;
            if (token.length() > 0 && entry.getValue().contains(token)) {
                write.println(token + " pertence a " + entry.getKey());
                encontrouLista = 1;
            }
        }

        if (encontrouLista == 0 && !token.matches("^\\s*$")) {
            if (token.matches("[A-Za-z]+[0-9A-Za-z_]*")) {
                write.println(token + " identificador");
                encontrouLista = 1;
            } else if (token.matches("[0-9]+")) {
                write.println(token + " numérico inteiro");
                encontrouLista = 1;
            } else if (token.matches("[0-9]+[.][0-9]+")) {
                write.println(token + " numérico real");
                encontrouLista = 1;
            } else {
                write.println(token + " não reconhecido");
            }
        }
    }
    
    public static PrintStream write;
    
    public static void main(String[] args) throws Exception
    {
        String enderecoArquivo = JOptionPane.showInputDialog("Endereço do arquivo");
        
        String[][] estados = new String[10][10];
        estados[0][1] = "[A-Za-z_]";
        estados[0][2] = "[0-9]";
        estados[0][3] = "[^A-Za-z0-9_]";
        estados[1][1] = "[A-Za-z0-9_]";
        estados[1][3] = "[^A-Za-z0-9_]";
        estados[2][2] = "[0-9]";
        estados[2][3] = "(?![A-Za-z])[^0-9]";
        estados[2][4] = "[A-Za-z]";
        
        BufferedReader bufferFile = new BufferedReader(new FileReader(new File(enderecoArquivo)));
        write = new PrintStream("resultado.txt");
        char letra;
        String token = "";
        int estado = 0, i;
        
        while (bufferFile.ready()) {
            letra = (char) bufferFile.read();
            if (letra == '.') {
                token += letra;
            } else {
                for (i=0;i<5;i++) {
                    if (estados[estado][i] != null &&
                            String.valueOf(letra).matches(estados[estado][i])) {
                        estado = i;
                        token += letra;
                        break;
                    }
                }
                if (estado == 3) {
                    AnalisadorLexico.classifica(token.substring(0, token.length()-1));
                    AnalisadorLexico.classifica(token.substring(token.length()-1));
                    token = "";
                    estado = 0;
                } else if (estado == 4) {
                    write.println("Erro em \"" + token + "\"");
                    break;
                }
            }
        }
        bufferFile.close();
    }
}
