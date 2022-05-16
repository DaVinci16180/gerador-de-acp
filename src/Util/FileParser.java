package Util;

import Entity.Automato;
import Entity.Estado;
import Entity.FuncaoDeTransicao;

import Exception.WrongFileFormatException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class FileParser {
    private BufferedReader br;
    private Stack<String> stack = new Stack<>();

    public FileParser(String filePath) throws FileNotFoundException {
        br = new BufferedReader(new FileReader(filePath));
        List<String> lines = new ArrayList<>(br.lines().toList());
        Collections.reverse(lines);
        stack.addAll(lines);
    }

    private List<Estado> getEstados() {
        List<Estado> estados = new ArrayList<>();

        if (stack.peek().startsWith("Estados do automato")) {
            stack.pop();

            String row = stack.pop();
            while (!row.equals("")) {
                String[] inputs = row.split(",");

                String nome = inputs[0];
                boolean inicial = inputs[1].equals("1");
                boolean aceitacao = inputs[2].equals("1");

                Estado estado = new Estado(nome, inicial, aceitacao);
                estados.add(estado);

                row = stack.pop();
            }

            return estados;
        }

        throw new WrongFileFormatException("O arquivo não está no formato adequado.");
    }

    private List<Character> getAlfabeto() {
        List<Character> alfabeto = new ArrayList<>();

        if (stack.peek().startsWith("Alfabeto do automato")) {
            stack.pop();

            String row = stack.pop();
            String[] letras = row.split(",");

            for (String letra : letras) {
                if (letra.length() > 1) {
                    throw new WrongFileFormatException("O arquivo não está no formato adequado.");
                }

                alfabeto.add(letra.charAt(0));
            }

            stack.pop();

            return alfabeto;
        }

        throw new WrongFileFormatException("O arquivo não está no formato adequado.");
    }

    private List<Character> getAlfabetoDaPilha() {
        List<Character> alfabeto = new ArrayList<>();

        if (stack.peek().startsWith("Alfabeto da pilha")) {
            stack.pop();

            String row = stack.pop();
            String[] letras = row.split(",");

            for (String letra : letras) {
                if (letra.length() > 1) {
                    throw new WrongFileFormatException("O arquivo não está no formato adequado.");
                }

                alfabeto.add(letra.charAt(0));
            }

            stack.pop();

            return alfabeto;
        }

        throw new WrongFileFormatException("O arquivo não está no formato adequado.");
    }

    private Character getSimboloDeInicio() {
        if (stack.peek().startsWith("Simbolo de inicio da pilha")) {
            stack.pop();

            String row = stack.pop();

            if (row.length() > 1) {
                throw new WrongFileFormatException("O arquivo não está no formato adequado.");
            }

            stack.pop();

            return row.charAt(0);
        }

        throw new WrongFileFormatException("O arquivo não está no formato adequado.");
    }

    private void linkFuncoesDeTransicaoToEstados(List<Estado> estados) {
        if (stack.peek().startsWith("Funcoes de transicao")) {
            stack.pop();

            String row = stack.pop();
            while (row != null && !row.equals("")) {
                Stack<Character> characters = stackStringReverse(row);

                StringBuilder estadoOrigem = new StringBuilder();
                while(characters.peek() != '(') {
                    estadoOrigem.append(characters.pop());
                }
                characters.pop();

                Character entrada = characters.pop();

                if (characters.pop() != ',')
                    throw new WrongFileFormatException("O arquivo não está no formato adequado.");

                Character condicao = characters.pop();

                if (characters.pop() != ':')
                    throw new WrongFileFormatException("O arquivo não está no formato adequado.");

                StringBuilder saida = new StringBuilder();
                while(characters.peek() != ')') {
                    saida.append(characters.pop());
                }
                characters.pop();
                characters.pop();
                characters.pop();

                StringBuilder estadoDestino = new StringBuilder();
                while(!characters.isEmpty()) {
                    estadoDestino.append(characters.pop());
                }

                FuncaoDeTransicao funcaoDeTransicao = new FuncaoDeTransicao();

                funcaoDeTransicao.setCondicao(condicao);
                funcaoDeTransicao.setEntrada(entrada);
                funcaoDeTransicao.setSaida(saida.toString());

                for (Estado estado : estados) {
                    if (estado.getNome().equals(estadoDestino.toString())) {
                        funcaoDeTransicao.setDestino(estado);
                    }
                    if (estado.getNome().equals(estadoOrigem.toString())) {
                        funcaoDeTransicao.setOrigem(estado);
                        estado.getFuncoesDeTransicao().add(funcaoDeTransicao);
                    }
                }

                if (stack.isEmpty()) {
                    break;
                }

                row = stack.pop();
            }
        } else {
            throw new WrongFileFormatException("O arquivo não está no formato adequado.");
        }
    }

    private Stack<Character> stackStringReverse(String string) {
        StringBuilder builder = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        builder.append(string);
        builder.reverse();

        for (char c : builder.toString().toCharArray()) {
            stack.push(c);
        }

        return stack;
    }

    public Automato getAutomato() {
        Automato automato = new Automato();

        List<Estado> estados = getEstados();
        List<Character> alfabeto = getAlfabeto();
        Character simboloDeInicio = getSimboloDeInicio();
        List<Character> alfabetoDaPilha = getAlfabetoDaPilha();

        if (alfabeto.contains('e') || alfabetoDaPilha.contains('e')) {
            throw new WrongFileFormatException("A letra 'e' nao pode ser utilizada nos alfabetos do automato e da pilha.");
        }

        automato.setEstados(estados);
        automato.setAlfabeto(alfabeto);
        automato.setSimboloDeInicio(simboloDeInicio);
        automato.setAlfabetoDaPilha(alfabetoDaPilha);
        linkFuncoesDeTransicaoToEstados(estados);

        return automato;
    }

    public void close() throws IOException {
        br.close();
    }

}
