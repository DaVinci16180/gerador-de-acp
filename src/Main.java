import Entity.Automato;
import Util.FileParser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nDigite o caminho para o arquivo do automato. Caso esteja no mesmo diretorio do arquivo .jar, basta digitar o nome do arquivo.");

        Automato automato = null;
        while (automato == null) {
            try {
                String filePath = scanner.nextLine();
                FileParser parser = new FileParser(filePath);
                automato = parser.getAutomato();
            } catch (Exception e) {
                System.out.println("Caminho invalido. Tente novamente.");
            }
        }

        System.out.println("\nAutomato criado com sucesso.");

        boolean running = true;
        while (running) {
            System.out.println("\nDigite uma palavra a ser testada pelo automato:");
            String palavra = scanner.nextLine();
            boolean aceita = automato.assess(palavra);

            if (aceita) {
                System.out.println("\nA palavra e aceita pelo automato :D");
            } else {
                System.out.println("\nA palavra NAO e aceita pelo automato :(");
            }

            System.out.println("\nGostaria de submeter mais palavras? [s/n]");
            String op = scanner.nextLine();

            if (op.equals("n") || op.equals("N")) {
                running = false;
            }
        }
    }
}