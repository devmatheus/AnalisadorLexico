package analisador.lexico;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JOptionPane;

public class AnalisadorLexico 
{
    public static void main(String[] args) throws Exception
    {
        //String enderecoArquivo = JOptionPane.showInputDialog("Endereço do arquivo");
        String enderecoArquivo = "file.txt";
        
        String[][] estados = new String[4][4];
        estados[0][1] = "[A-Za-z]";
        estados[0][2] = "[0-9]";
        estados[0][3] = "[\\s]";
        estados[1][1] = "[A-Za-z0-9]";
        estados[1][3] = "[*/.,-=\\s]";
        estados[2][2] = "[0-9]";
        estados[2][3] = "[*/.,-=\\s]";
        
        
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
        
        BufferedReader bufferFile = new BufferedReader(new FileReader(new File(enderecoArquivo)));
        char letra;
        String token = "";
        int estado = 0, i;
        
        while (bufferFile.ready()) {
            letra = (char) bufferFile.read();
            for (i=0;i<4;i++) {
                if (estados[estado][i] != null &&
                        String.valueOf(letra).matches(estados[estado][i])) {
                    estado = i;
                    break;
                }
            }
            if (estado == 3) {
                for (Entry<String, ArrayList> entry : listas.entrySet()) {
                    if (entry.getValue().contains(token)) {
                        System.out.println(token + " pertence a " + entry.getKey());
                    } else if (token.equals(" ")) {
                        System.out.println("espaço em branco ignorado");
                    } else {
                        System.out.println(token + " não reconhecido");
                    }
                }
                
                token = String.valueOf(letra);
                estado = 0;
            } else {
                token += letra;
            }
        }
    }
}
