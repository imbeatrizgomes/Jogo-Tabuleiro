package entities;

import java.util.Random;

public class JogadorSemSorte extends Jogador {
    
	public JogadorSemSorte(String nome, String cor) {
        super(nome, cor);
        this.tipoDeJogador = "Azarado";
    }

    @Override
    public int rolarDados() {
        Random random = new Random();
        int dado1 = random.nextInt(6) + 1;
        int dado2 = random.nextInt(6) + 1;
        int soma = dado1 + dado2;
        if (soma > 6) { 
        	soma = 6;
        }
        return soma;
    }

    @Override
    public boolean azarado() {
        return true;
    }
}
