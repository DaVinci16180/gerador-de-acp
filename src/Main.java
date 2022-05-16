import Entity.Automato;
import Entity.Estado;
import Entity.FuncaoDeTransicao;
import Util.FileParser;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o caminho para o arquivo do automato. Caso esteja na raiz do projeto, basta digitar o nome do arquivo.");
        String filePath = null;
        Automato automato = null;

        while (automato == null) {
            try {
                filePath = scanner.nextLine();
                FileParser parser = new FileParser(filePath);
                automato = parser.getAutomato();
            } catch (Exception e) {
                System.out.println("Caminho invalido. Tente novamente.");
            }
        }

        System.out.println("\nAutomato criado com sucesso.\n");

        boolean running = true;
        while (running) {
            System.out.println("Digite uma palavra a ser testada pelo automato:");
            String palavra = scanner.nextLine();
            boolean aceita = automato.assess(palavra);

            if (aceita) {
                System.out.println("A palavra e aceita pelo automato :D");
            } else {
                System.out.println("A palavra NAO e aceita pelo automato :(");
            }

            System.out.println("\nGostaria de submeter mais palavras? [s/n]");
            String op = scanner.nextLine();

            if (op.equals("n") || op.equals("N")) {
                running = false;
            }
        }




    }
}