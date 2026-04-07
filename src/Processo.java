
import java.util.ArrayList;

class Processo {

    String nome;
    int tempoExecucao;
    int tempoChegada;
    int tempoRestante; // p controlar quanto falta para acabar na cpu
    int prioridade;    // p usar no algorit de priorid.

    public Processo(String nome, int tempoChegada, int tempoExecucao) {
        this.nome = nome;
        this.tempoChegada = tempoChegada;
        this.tempoExecucao = tempoExecucao;
    }
}
