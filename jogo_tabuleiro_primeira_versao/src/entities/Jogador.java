package entities;

public abstract class Jogador {
    protected String nome;
    protected String cor;
    protected int posicao;
    protected int jogadas;
    protected String tipoDeJogador;
    protected boolean pulaProximaRodada;

    public Jogador(String nome, String cor) {
        this.nome = nome;
        this.cor = cor;
        this.posicao = 0;
        this.jogadas = 0;
        this.tipoDeJogador = "Normal";
        this.pulaProximaRodada = false; //Inicia como não pula
    }

    public String getNome() {
        return nome;
    }

    public String getCor() {
        return cor;
    }

    public int getPosicao() {
        return posicao;
    }

    public int getJogadas() {
        return jogadas;
    }

    public String getTipoDeJogador() {
        return tipoDeJogador;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public boolean isPulaProximaRodada() {
        return pulaProximaRodada;
    }

    public void setPulaProximaRodada(boolean pula) {
        this.pulaProximaRodada = pula;
    }

    public void incrementarJogada() {
        this.jogadas++;
    }

    public void mover(int somaDeDados) {
        this.posicao += somaDeDados;
        jogadas++;
    }

    public void mudarTipo(String novoTipo) {
        this.tipoDeJogador = novoTipo;
    }

    public abstract int rolarDados();

    public abstract boolean azarado();
}

