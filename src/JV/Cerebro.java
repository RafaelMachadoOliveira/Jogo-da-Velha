package JV;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cerebro {

    public char[] estado = new char[9];//declarando vetor principal que armazenará as jogadas.
    public char jogador;// variavel que armazena quem é o jogador.

    //construtor inicia com o jogador x e setando todas as posições do vetor principal com espaço vazio.
    public Cerebro() {
        for (int i = 0; i < estado.length; i++) {
            estado[i] = ' ';
        }
        jogador = 'x';
    }

    //função que rezeta o vetor principal se o jogador decidir fazer uma nova partida.
    public void resetVetor() {
        for (int i = 0; i < estado.length; i++) {
            estado[i] = ' ';
        }
    }

    //construtor carregado com o tabuleiro e proximo jogador.
    public Cerebro(char[] e, char j) {
        estado = e;
        jogador = j;
    }

    //função que realiza a jogada 
    public char[] jogar(char vez, int jogada) {
        if (estado[jogada - 1] == ' ') {
            char[] resp = new char[9];
            for (int i = 0; i < 9; i++) {
                resp[i] = estado[i];
            }
            resp[jogada - 1] = vez;
            return resp;
        } else {
            return null;
        }
    }
    
    //função que muda o jogador.
    public void mudarVez(char vez, int jogada) {
        char[] newEstado = jogar(vez, jogada);
        if (newEstado != null) {
            estado = newEstado;
        }
        if (vez == 'x') {
            jogador = 'o';
        } else {
            jogador = 'x';
        }
    }
    //função que retorna o vetor principal.
    public char[] saida() {
        return estado;
    }

    //função que verifica quem é o ganhador.
    public char verificaGanhador() {
        char resultado = 0;
        if (estado[0] == ('x') && estado[1] == ('x') && estado[2] == ('x')) {
            resultado = 'x';
        } else if (estado[3] == ('x') && estado[4] == ('x') && estado[5] == ('x')) {
            resultado = 'x';
        } else if (estado[6] == ('x') && estado[7] == ('x') && estado[8] == ('x')) {
            resultado = 'x';
        } else if (estado[0] == ('x') && estado[3] == ('x') && estado[6] == ('x')) {
            resultado = 'x';
        } else if (estado[1] == ('x') && estado[4] == ('x') && estado[7] == ('x')) {
            resultado = 'x';
        } else if (estado[2] == ('x') && estado[5] == ('x') && estado[8] == ('x')) {
            resultado = 'x';
        } else if (estado[0] == ('x') && estado[4] == ('x') && estado[8] == ('x')) {
            resultado = 'x';
        } else if (estado[2] == ('x') && estado[4] == ('x') && estado[6] == ('x')) {
            resultado = 'x';
        } else if (estado[0] == ('o') && estado[1] == ('o') && estado[2] == ('o')) {
            resultado = 'o';
        } else if (estado[3] == ('o') && estado[4] == ('o') && estado[5] == ('o')) {
            resultado = 'o';
        } else if (estado[6] == ('o') && estado[7] == ('o') && estado[8] == ('o')) {
            resultado = 'o';
        } else if (estado[0] == ('o') && estado[3] == ('o') && estado[6] == ('o')) {
            resultado = 'o';
        } else if (estado[1] == ('o') && estado[4] == ('o') && estado[7] == ('o')) {
            resultado = 'o';
        } else if (estado[2] == ('o') && estado[5] == ('o') && estado[8] == ('o')) {
            resultado = 'o';
        } else if (estado[0] == ('o') && estado[4] == ('o') && estado[8] == ('o')) {
            resultado = 'o';
        } else if (estado[2] == ('o') && estado[4] == ('o') && estado[6] == ('o')) {
            resultado = 'o';
        } else if (estado[0] != (' ') && estado[1] != (' ') && estado[2] != (' ') && estado[3] != (' ') && estado[4] != (' ') && estado[5] != (' ') && estado[6] != (' ') && estado[7] != (' ') && estado[8] != (' ')) {
            resultado = 'e';
        }
        return resultado;
    }

    //função que realiza jogada aleatória do jogador bolinha(IA).
    public void aleatorio() {
        Random random = new Random();
        int aux = 0;
        for (int i = 0; i < 9; i++) {
            if (estado[i] == (' ')) {
                do {
                    aux = random.nextInt(9);
                } while ('o' == (estado[aux]) || 'x' == (estado[aux]));
            }
        }
        estado[aux] = 'o';
    }

    //lista que obtem os filhos apartir da jogada.
    List<Cerebro> prox() {
        List<Cerebro> resp = new ArrayList<Cerebro>();
        char prox_jogador;
        if (jogador == 'x') {//verifica qual é o próximo jogador.
            prox_jogador = 'o';
        } else {
            prox_jogador = 'x';
        }
        for (int i = 1; i <= 9; i++) {
            char[] e = getTabuleiro(jogador, i); //recebe o tabuleiro
            if (e != null) {
                resp.add(new Cerebro(e, prox_jogador)); //adicionando na lista os filhos.
            }
        }
        return resp;
    }
    
    // função que retorna o tabuleiro no estado atual.
    public char[] getTabuleiro(char player, int n) {
        if (estado[n - 1] == ' ') {
            char[] resp = new char[9];
            for (int i = 0; i < 9; i++) {
                resp[i] = estado[i];
            }
            resp[n - 1] = player;
            return resp;
        } else {
            return null;
        }
    }

    //função do algoritmo alfa-beta.
    public int alphabeta(Cerebro g, int alpha, int beta, char vez) {
        // Se fim retorna 1 para ganhador x -1 para ganhador o e 0 para empate
        if (g.fim()) {
            char ganhador = g.verificaGanhador();
            if (ganhador == 'x') {
                return 1;
            } else if (ganhador == 'o') {
                return -1;
            } else {
                return 0;
            }
        }
        // Se for a vez de MAX
        if (vez == 'x') {
            // v = -inf
            int v = Integer.MIN_VALUE;
            // Para cada no filho de g
            for (Cerebro next : g.prox()) {
                // v = max(v, alphabeta(filho, alpha, beta, min)
                v = Integer.max(v, alphabeta(next, alpha, beta, 'o'));
                // alpha = max(alpha, v)
                alpha = Integer.max(alpha, v);
                // se beta <= alpha => poda em beta
                if (beta <= alpha) {
                    break;
                }
            }
            return v;
        } else {
            // v = inf
            int v = Integer.MAX_VALUE;
            // Para cada filho de g
            for (Cerebro next : g.prox()) {
                // v = min (v, alphabeta(filho,alpha,beta,max))
                v = Integer.min(v, alphabeta(next, alpha, beta, 'x'));
                // beta = min(beta,v)
                beta = Integer.min(beta, v);
                // se beta <= alpha => poda em alpha
                if (beta <= alpha) {
                    break;
                }
            }
            return v;
        }
    }

    //função do algoritmo poda minimax
    public int minimax(Cerebro g, char vez) {
       // Se fim retorna 1 para ganhador x, -1 para ganhador o, e 0 para empate.
        if (g.fim()) {
            char ganhador = g.verificaGanhador();
            if (ganhador == 'x') {
                return 1;
            } else if (ganhador == 'o') {
                return -1;
            } else {
                return 0;
            }
        }

        // Se for a vez de MAX
        if (vez == 'x') {
            // best = -inf
            int best = Integer.MIN_VALUE;
            // para cada filho de g
            for (Cerebro next : g.prox()) {
                // v = minimax(filho, min)
                int v = minimax(next, 'o');
                //best = max(best,v)
                best = Integer.max(best, v);
            }
            return best;
        } else { // Se for a vez de min
            // best = inf
            int best = Integer.MAX_VALUE;
            // Para cada filho de g
            for (Cerebro next : g.prox()) {
                // v = minimax(filho, max)
                int v = minimax(next, 'x');
                // best = min(best, v)
                best = Integer.min(best, v);
            }
            return best;
        }
    }

    //função que verifica fim
    boolean fim() {
        char g = verificaGanhador();
        return g != 0;
    }

}
