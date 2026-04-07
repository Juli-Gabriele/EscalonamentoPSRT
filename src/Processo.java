
import java.util.ArrayList;

class Processo {

    String nome;
    int tempoExecucao;
    int tempoChegada;

    public Processo(String nome, int tempoChegada, int tempoExecucao) {
        this.nome = nome;
        this.tempoChegada = tempoChegada;
        this.tempoExecucao = tempoExecucao;
    }
}
