package entities;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Tabuleiro {
    private List<Jogador> jogadores;
    private int turno;

    public Tabuleiro(List<Jogador> jogadores) {
        this.jogadores = jogadores;
        this.turno = 0;
    }

    //De quem é a vez de jogar
    public Jogador jogadorAtual() {
        return jogadores.get(turno % jogadores.size());
    }

    public void proximoTurno() {
        turno++;
    }

    public boolean verificarVencedor(Jogador jogador) {
        return jogador.getPosicao() == 40;
    }

    public String aplicarRegrasEspeciais(Jogador jogador) {
        int pos = jogador.getPosicao();
        String mensagem = "";

        // Casas da sorte
        if (pos == 5 || pos == 15 || pos == 30) {
            if (jogador.getTipoDeJogador().equalsIgnoreCase("Azarado")) {
                mensagem = "Que pena! " + jogador.getNome() + " é azarado e não pode avançar na casa da sorte. Continua na casa " + pos + ".";
            } else {
                jogador.mover(3);
                mensagem = " Que sorte! " + jogador.getNome() + " caiu em uma casa da sorte e avançou 3 casas!";
            }
        }

        // Casas que fazem pular a rodada
        else if (pos == 10 || pos == 25 || pos == 38) {
            jogador.setPulaProximaRodada(true);
            mensagem = jogador.getNome() + " caiu em uma casa especial e perderá a próxima rodada!";
        }

        // Casa surpresa
        else if (pos == 13) {
            Random r = new Random();
            int tipo = r.nextInt(3);
            String novoTipo;

            if (tipo == 0) {
                novoTipo = "Normal";
            } else if (tipo == 1) {
                novoTipo = "Azarado";
            } else {
                novoTipo = "Sortudo";
            }

            jogador.mudarTipo(novoTipo);
            mensagem = jogador.getNome() + " caiu na Casa Surpresa! Agora é um jogador " + novoTipo + ".";
        }

        // Casas 17 e 27 — jogador escolhe alguém para voltar ao início
        else if (pos == 17 || pos == 27) {
            mensagem = "ESCOLHER_JOGADOR";
        }

        // Casas mágicas
        else if (pos == 20 || pos == 35) {
            mensagem = trocarComMaisAtras(jogador);
        }

        return mensagem;
    }

    private String trocarComMaisAtras(Jogador jogador) {
        Jogador atras = maisAtrasExcetoAtual(jogador);
        if (atras != null && atras.getPosicao() < jogador.getPosicao()) {
            int temp = jogador.getPosicao();
            jogador.setPosicao(atras.getPosicao());
            atras.setPosicao(temp);
            return jogador.getNome() + " caiu em uma casa mágica e trocou de lugar com " + atras.getNome() + "!";
        } else {
            return jogador.getNome() + " caiu em uma casa mágica, mas já é o último";
        }
    }
    
    private Jogador maisAtrasExcetoAtual(Jogador atual) {
        return jogadores.stream()
                .filter(j -> j != atual)
                .min(Comparator.comparingInt(Jogador::getPosicao))
                .orElse(null);
    }
    
    public String enviarOutroAoInicio(Jogador atual, String nomeEscolhido) {
        for (Jogador j : jogadores) {
            if (j != atual && j.getNome().equalsIgnoreCase(nomeEscolhido)) {
                j.setPosicao(0);
                return "🔄 " + atual.getNome() + " escolheu " + j.getNome() + " para voltar ao início do jogo!";
            }
        }
        return "";
    }

    public List<Jogador> jogadores() {
        return jogadores;
    }

    //Tabuleiro
    public String gerarTabuleiro() {
    	StringBuilder sb = new StringBuilder();
        int totalDeCasas = 40;
        int casasPorLinha = 10;
        int linhas = totalDeCasas / casasPorLinha; //4 linhas, cada uma contendo 10 casas.

        sb.append("\n============================================================\n");
        sb.append(String.format("%35s%n", " TABULEIRO"));
        sb.append("============================================================\n");

        // Mostra a casa 0 (início do jogo)
        sb.append("Casa inicial (0): " + "[");
        StringBuilder casaInicial = new StringBuilder();
        for (Jogador p : jogadores) {
            if (p.getPosicao() == 0) {
                casaInicial.append(p.getCor().toUpperCase().charAt(0));
            }
        }
        casaInicial.append("]");
        sb.append(casaInicial.toString()).append("\n\n");

        // Linhas do tabuleiro
        for (int linha = 0; linha < linhas; linha++) {
            //Intervalo de casas de cada linha
        	int inicio = linha * casasPorLinha + 1;
            int fim = inicio + casasPorLinha - 1;

            // Linha com as casas
            for (int i = inicio; i <= fim; i++) {
                StringBuilder simbolos = new StringBuilder();

                // Adiciona todas as letras dos jogadores na mesma casa
                for (Jogador p : jogadores) {
                    if (p.getPosicao() == i) {
                    	simbolos.append(p.getCor().toUpperCase().charAt(0));
                    }
                }

                // Exibe de acordo com a quantidade de jogadores na casa
                if (simbolos.length() == 0) {
                    sb.append("[ ] ");
                } else {
                    sb.append("[").append(simbolos).append("] ");
                }
            }

            sb.append("\n");

            // Números de cada casa
            for (int i = inicio; i <= fim; i++) {
                sb.append(String.format("%3d ", i));
            }

            sb.append("\n\n");
        }

        sb.append("============================================================\n");

        return sb.toString();
    }

}

