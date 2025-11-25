package entities;

import java.util.Random;

public class JogadorComSorte extends Jogador {
    
	public JogadorComSorte(String nome, String cor) {
        super(nome, cor);
        this.tipoDeJogador = "Sortudo";
    }

    @Override
    public int rolarDados() {
        Random random = new Random();
        int dado1 = random.nextInt(6) + 1; //Dado 1 (1 a 6)
        int dado2 = random.nextInt(6) + 1; //Dado 1 (1 a 6)
        int soma = dado1 + dado2;
        if (soma < 7) {
        	soma = 7;
        }
        return soma;
    }

    @Override
    public boolean azarado() {
        return false;
    }
}