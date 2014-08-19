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
        
        HashMap<String,ArrayList> listas = new HashMap();
        listas.put("Palavras Reservadas", palavrasReservadas);
        
        for (Entry<String, ArrayList> entry : listas.entrySet()) {
            if (token.substring(token.length()-1).equals(" ") && token.length()-1>0) {
                token = token.substring(0,token.length()-1);
                if (entry.getValue().contains(token)) {
                    System.out.println(token + " pertence a " + entry.getKey());
                } else {
                    System.out.println(token + " não reconhecido");
                }
                System.out.println("espaço em branco");
            } else {
                System.out.println("espaço em branco");
            }
        }
    }
    
    public static void main(String[] args) throws Exception
    {
        //String enderecoArquivo = JOptionPane.showInputDialog("Endereço do arquivo");
        String enderecoArquivo = "file.txt";
        
        String[][] estados = new String[10][10];
        
        BufferedReader bufferFile = new BufferedReader(new FileReader(new File(enderecoArquivo)));
        char letra;
        String token = "";
        int estado = 0, i;
        
        while (bufferFile.ready()) {
            letra = (char) bufferFile.read();
            for (i=0;i<5;i++) {
                if (estados[estado][i] != null &&
                        String.valueOf(letra).matches(estados[estado][i])) {
                    estado = i;
                    token += letra;
                    break;
                }
            }
            if (estado == 3 || estado == 4) {
                AnalisadorLexico.classifica(token);
                token = "";
                estado = 0;
            }
        }
    }
}
