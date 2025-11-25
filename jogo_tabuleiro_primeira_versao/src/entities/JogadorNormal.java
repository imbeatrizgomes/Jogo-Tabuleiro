package entities;

import java.util.Random;

public class JogadorNormal extends Jogador {
   
	public JogadorNormal(String nome, String cor) {
        super(nome, cor);
        this.tipoDeJogador = "Normal";
    }

    @Override
    public int rolarDados() {
        Random random = new Random();
        int dado1 = random.nextInt(6) + 1;
        int dado2 = random.nextInt(6) + 1;
        return dado1 + dado2;
    }

    @Override
    public boolean azarado() {
        return false;
    }
}
