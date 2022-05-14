package Entity;

public class FuncaoDeTransicao {
    private Character entrada;
    private Character condicao;
    private String saida;
    private Estado origem;
    private Estado destino;

    public Character getEntrada() {
        return entrada;
    }

    public void setEntrada(Character entrada) {
        this.entrada = entrada;
    }

    public Character getCondicao() {
        return condicao;
    }

    public void setCondicao(Character condicao) {
        this.condicao = condicao;
    }

    public String getSaida() {
        return saida;
    }

    public void setSaida(String saida) {
        this.saida = saida;
    }

    public Estado getOrigem() {
        return origem;
    }

    public void setOrigem(Estado origem) {
        this.origem = origem;
    }

    public Estado getDestino() {
        return destino;
    }

    public void setDestino(Estado destino) {
        this.destino = destino;
    }
}
