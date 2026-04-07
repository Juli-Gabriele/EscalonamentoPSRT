
import java.util.ArrayList;

class Processo {

    String nome;
    int tempoExecucao;
    int tempoChegada;
    int tempoRestante; // p controlar quanto falta para acabar na cpu
    int prioridadeOriginal; // que o usuário digitou
    int prioridadeAtual;    // que vai mudar com o tempo (aging)
    int tempoEspera;        // contador de ciclos na fila de prontos

    public Processo(String nome, int tempoChegada, int tempoExecucao, int prioridade) {
        this.nome = nome;
        this.tempoChegada = tempoChegada;
        this.tempoExecucao = tempoExecucao;
    }
}
