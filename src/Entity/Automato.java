package Entity;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Automato {

    private List<Estado> estados;
    private List<Character> alfabeto;
    private Character simboloDeInicio;
    private List<Character> alfabetoDaPilha;
    private List<FuncaoDeTransicao> funcoesDeTransicao;

    public List<Estado> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public List<Character> getAlfabeto() {
        return alfabeto;
    }

    public void setAlfabeto(List<Character> alfabeto) {
        this.alfabeto = alfabeto;
    }

    public Character getSimboloDeInicio() {
        return simboloDeInicio;
    }

    public void setSimboloDeInicio(Character simboloDeInicio) {
        this.simboloDeInicio = simboloDeInicio;
    }

    public List<Character> getAlfabetoDaPilha() {
        return alfabetoDaPilha;
    }

    public void setAlfabetoDaPilha(List<Character> alfabetoDaPilha) {
        this.alfabetoDaPilha = alfabetoDaPilha;
    }

    public List<FuncaoDeTransicao> getFuncoesDeTransicao() {
        return funcoesDeTransicao;
    }

    public void setFuncoesDeTransicao(List<FuncaoDeTransicao> funcoesDeTransicao) {
        this.funcoesDeTransicao = funcoesDeTransicao;
    }

    public boolean assess(@NotNull String string, @NotNull Stack<Character> stack) {
        stack.push(simboloDeInicio);
        Queue<Character> palavra = new LinkedList<>();
        for (char c : string.toCharArray()) {
            palavra.add(c);
        }

        Optional<Estado> opt = estados.stream().filter(s -> {
           return s.isInicial();
        }).findFirst();

        if (opt.isEmpty()) {
            throw new RuntimeException("Não existe estado inicial");
        }

        Estado inicial = opt.get();

        List<Estado> finais = estados.stream().filter(s -> {
            return s.isAceitacao();
        }).toList();

        Estado estadoAtual = inicial;
        try {
            while(!palavra.isEmpty()) {
                Character letra = palavra.poll();
                boolean transitioned = false;

                for (FuncaoDeTransicao funcao : estadoAtual.getFuncoesDeTransicao()) {
                    if (letra.equals(funcao.getEntrada()) && stack.peek().equals(funcao.getCondicao())) {
                        stack.pop();

                        if (!funcao.getSaida().equals("e")) {
                            StringBuilder builder = new StringBuilder();
                            builder.append(funcao.getSaida()).reverse();
                            for (char c : builder.toString().toCharArray()) {
                                stack.push(c);
                            }
                        }

                        estadoAtual = funcao.getDestino();
                        transitioned = true;

                        break;
                    }
                }

                if (!transitioned) {
                    throw new RuntimeException();
                }
            }
        } catch (Exception e) {
            return false;
        }

        return stack.pop().equals('z');
    }
}
