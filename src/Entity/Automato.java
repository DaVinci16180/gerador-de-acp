package Entity;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Automato {

    private List<Estado> estados;
    private List<Character> alfabeto;
    private Character simboloDeInicio;
    private List<Character> alfabetoDaPilha;

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public void setAlfabeto(List<Character> alfabeto) {
        this.alfabeto = alfabeto;
    }

    public void setSimboloDeInicio(Character simboloDeInicio) {
        this.simboloDeInicio = simboloDeInicio;
    }

    public void setAlfabetoDaPilha(List<Character> alfabetoDaPilha) {
        this.alfabetoDaPilha = alfabetoDaPilha;
    }

    private Queue<Character> getPalavra(String string) {
        Queue<Character> palavra = new LinkedList<>();

        for (char c : string.toCharArray()) {
            palavra.add(c);
        }

        return palavra;
    }

    private Estado getEstadoInicial() {
        Optional<Estado> opt = estados.stream().filter(Estado::isInicial).findFirst();

        if (opt.isEmpty()) {
            throw new RuntimeException("Não existe estado inicial");
        }

        return opt.get();
    }

    public boolean assess(@NotNull String string) {
        Stack<Character> stack = new Stack<>();
        stack.push(simboloDeInicio);

        Queue<Character> palavra = getPalavra(string);

        Estado estadoAtual = getEstadoInicial();
        try {
            return doAssessments(palavra, stack, estadoAtual);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean doAssessments(Queue<Character> palavra, Stack<Character> stack, Estado estadoAtual) {
        Character letra = palavra.peek();
        for (FuncaoDeTransicao funcao : estadoAtual.getFuncoesDeTransicao()) {
            if ((funcao.getEntrada().equals(letra) || funcao.getEntrada().equals('e')) && stack.peek().equals(funcao.getCondicao())) {
                Stack<Character> tempStack = cloneStack(stack);
                Queue<Character> tempPalavra = cloneQueue(palavra);
                if (!funcao.getEntrada().equals('e')) {
                    tempPalavra.poll();
                }

                estadoAtual = transicao(tempStack, funcao);

                if (tempPalavra.isEmpty() && (tempStack.peek().equals(simboloDeInicio) || estadoAtual.isAceitacao())) {
                    return true;
                }

                boolean res = doAssessments(tempPalavra, tempStack, new Estado(estadoAtual));

                if (res) {
                    return true;
                }
            }
        }

        return false;
    }

    private Estado transicao(Stack<Character> stack, FuncaoDeTransicao funcao) {
        stack.pop();

        if (!funcao.getSaida().equals("e")) {
            StringBuilder builder = new StringBuilder();
            builder.append(funcao.getSaida()).reverse();
            for (char c : builder.toString().toCharArray()) {
                if (!alfabetoDaPilha.contains(c) && !simboloDeInicio.equals(c)) {
                    throw new RuntimeException();
                }

                stack.push(c);
            }
        }

        return funcao.getDestino();
    }

    private Queue<Character> cloneQueue(Queue<Character> queue) {
        return new LinkedList<>(queue);
    }

    private Stack<Character> cloneStack(Stack<Character> stack) {
        Stack<Character> newStack = new Stack<>();
        List<Character> chars = new ArrayList<>(stack);
        newStack.addAll(chars);

        return newStack;
    }

}
