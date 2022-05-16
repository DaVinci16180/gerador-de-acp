import Entity.Automato;
import Entity.Estado;
import Entity.FuncaoDeTransicao;
import Util.FileParser;

import java.io.IOException;
import java.util.List;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        FileParser parser = new FileParser("modelo.txt");
        Automato automato = parser.getAutomato();

        String string = "ab";


        boolean aceita = automato.assess(string);

        System.out.println("aceita: " + aceita);
    }
}