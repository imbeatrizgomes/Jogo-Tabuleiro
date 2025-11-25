package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import entities.JogadorComSorte;
import entities.JogadorNormal;
import entities.Jogador;
import entities.Tabuleiro;
import entities.JogadorSemSorte;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int opcao;
        do {
            System.out.println("===== MENU PRINCIPAL =====");
            System.out.println("1 - Iniciar novo jogo");
            System.out.println("2 - Regras do jogo");
            System.out.println("3 - Casas e cartas especiais");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            
            sc.nextLine();
            if (opcao == 1) {
                System.out.print("Modo DEBUG (S/N)? ");
                boolean debug = sc.nextLine().equalsIgnoreCase("S");

                List<Jogador> jogadores = new ArrayList<>();

                int qtd = 0;
                do {
                    System.out.print("Quantos jogadores participarão (2 a 6)? ");
                    qtd = sc.nextInt();
                    sc.nextLine();
                    if (qtd < 2 || qtd > 6) {
                        System.out.println("O número de jogadores deve ser entre 2 e 6.");
                    }
                } while (qtd < 2 || qtd > 6);

                for (int i = 1; i <= qtd; i++) {
                    System.out.println("\nJogador " + i + ":");
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("Cor: ");
                    String cor = sc.nextLine();
                    System.out.println("Tipo 1 - Normal");
                    System.out.println("Tipo 2 - Sortudo");
                    System.out.println("Tipo 3 - Azarado");
                    System.out.print("Escolha uma dos tipos: ");
                    String tipo = sc.nextLine();

                    Jogador p;
                    if (tipo.equalsIgnoreCase("Sortudo")) {
                        p = new JogadorComSorte(nome, cor);
                    } else if (tipo.equalsIgnoreCase("Azarado")) {
                        p = new JogadorSemSorte(nome, cor);
                    } else {
                        p = new JogadorNormal(nome, cor);
                    }
                    jogadores.add(p);
                }

                Tabuleiro tabuleiro = new Tabuleiro(jogadores);
                boolean jogoAtivo = true;

                while (jogoAtivo) {
                    Jogador atual = tabuleiro.jogadorAtual();

                    // Exibe o tabuleiro usando o método da classe Tabuleiro
                    System.out.println(tabuleiro.gerarTabuleiro());

                    if (atual.isPulaProximaRodada()) {
                        System.out.println(atual.getNome() + " vai pular esta rodada!");
                        atual.setPulaProximaRodada(false);
                        tabuleiro.proximoTurno();
                        continue;
                    }
                    
                    System.out.println();                    
                    System.out.println("Vez de " + atual.getNome() + " (" + atual.getTipoDeJogador() + ") - Casa " + atual.getPosicao());
                    System.out.print("Pressione ENTER para jogar...");
                    sc.nextLine();

                    if (debug) {
                        System.out.print("Informe a casa para onde deseja mover (1 a 40): ");
                        int novaPosicao = sc.nextInt();
                        sc.nextLine();
                        if (novaPosicao < 0) novaPosicao = 0;
                        if (novaPosicao > 40) novaPosicao = 40;
                        atual.setPosicao(novaPosicao);
                        System.out.println("(DEBUG) " + atual.getNome() + " foi movido para a casa " + novaPosicao + ".");
                    } else {
                        Random random = new Random();
                        int dado1 = random.nextInt(6) + 1;
                        int dado2 = random.nextInt(6) + 1;
                        int soma = dado1 + dado2;
                        atual.mover(soma);
                        System.out.println(atual.getNome() + " tirou " + dado1 + " e " + dado2 + " (soma " + soma + ").");
                        if (atual.getPosicao() > 40) atual.setPosicao(40);
                    }

                    // Aplica regras especiais
                    String evento = tabuleiro.aplicarRegrasEspeciais(atual);
                    if (evento.equals("ESCOLHER_JOGADOR")) {
                        List<Jogador> lista = tabuleiro.jogadores();
                        List<Jogador> oponentes = new ArrayList<>();
                        System.out.println("\n" + atual.getNome() + " pode escolher alguém para voltar ao início!");
                        for (Jogador j : lista) {
                            if (j != atual) {
                                oponentes.add(j);
                                System.out.println("- " + j.getNome() + " (" + j.getCor() + "), casa " + j.getPosicao());
                            }
                        }

                        Jogador escolhido = null;
                        while (escolhido == null) {
                            System.out.print("Digite o nome do jogador que deseja mandar ao início: ");
                            String nomeEscolhido = sc.nextLine().trim();
                            for (Jogador j : oponentes) {
                                if (j.getNome().equalsIgnoreCase(nomeEscolhido)) {
                                    escolhido = j;
                                    break;
                                }
                            }
                            if (escolhido == null) {
                            	System.out.println("Nome inválido. Tente novamente.");
                            }
                        }
                        escolhido.setPosicao(0);
                        System.out.println(escolhido.getNome() + " foi mandado de volta ao início!");
                    } else if (!evento.isEmpty()) {
                        System.out.println(evento);
                    }

                    if (tabuleiro.verificarVencedor(atual)) {
                        System.out.println("Parabéns! " + atual.getNome() + " é o vencedor!");
                        jogoAtivo = false;
                        break;
                    }

                    tabuleiro.proximoTurno();
                }
            }

            // Opção regras do jogo
            else if (opcao == 2) {
                System.out.println("\n===== REGRAS DO JOGO =====");
                System.out.println("- Somente 6 jogadores por partida");
                System.out.println("- O tabuleiro possui 40 casas");
                System.out.println("- Cada jogador lança dois dados por rodada");
                System.out.println("- Se tirar dois números iguais, joga novamente");
                System.out.println("- O primeiro a chegar ou ultrapassar a casa 40 vence");
                System.out.println("- Existem casas especiais que alteram o andamento do jogo");
                System.out.println();
            }

            // Opção casas especiais
            else if (opcao == 3) {
                System.out.println("\n===== CASAS ESPECIAIS =====");
                System.out.println("5, 15, 30 - Casa da Sorte: Jogador avança mais 3 casas, exceto jogador azarado");
                System.out.println("10, 25, 38 - Casa de Punição: Jogador perde a próxima rodada");
                System.out.println("13 - Casa Surpresa: Jogador muda de tipo (Normal, Sortudo, Azarado)");
                System.out.println("17, 27 - Casa Escolha: Jogador manda outro jogador ao início");
                System.out.println("20, 35 - Casa Mágica: Jogador troca de lugar com o último");
                System.out.println("Se o jogador tirar dois dados iguais ele anda as casas e joga novamente (Respeitando as regras acima!)");
                System.out.println();
            }

            else if (opcao == 0) {
                System.out.println("Saindo do jogo...");
            }

            else {
                System.out.println("Opção inválida, tente novamente!");
                System.out.println();
            }
        } while (opcao != 0);

        sc.close();
    }
}

