import java.util.ArrayList;
import java.util.Random;

public class EscalonadorPrioridade {
    ArrayList<Processo> listaProcessos = new ArrayList<>();
    Prioridades controlador = new Prioridades();

    public void adicionarProcesso(String nome, int tempoChegada, int tempoExecucao, int prioridade) {
        listaProcessos.add(new Processo(nome, tempoChegada, tempoExecucao, prioridade));
    }

    public void executar() {
        int tempoAtual = 0;
        Processo anterior = null;
        int indexAt = -1;

        while (!listaProcessos.isEmpty()) {

            int indexEscolhido = escolherProcesso(tempoAtual);

            if (indexEscolhido == -1) {
                tempoAtual++;
                continue;
            }

            Processo menor;
            int index;

            int menorExecucao = listaProcessos.get(indexEscolhido).tempoExecucao;

            if (anterior != null && anterior.tempoExecucao == menorExecucao) {
                menor = anterior;
                index = indexAt;
            } else {
                menor = listaProcessos.get(indexEscolhido);
                index = indexEscolhido;
            }

            System.out.print(menor.nome + " " + menor.tempoChegada + " " + menor.tempoExecucao + " // ");

            menor.tempoExecucao--;

            if (menor.tempoExecucao <= 0) {
                listaProcessos.remove(index);
                anterior = null;
            } else {
                anterior = menor;
                indexAt = index;
            }

            tempoAtual++;
        }
    }

    public int escolherProcesso(int tempoAtual) {
        int menorExecucao = Integer.MAX_VALUE;

        for (Processo atual : listaProcessos) {
            if (atual.tempoChegada <= tempoAtual) {
                if (atual.tempoExecucao < menorExecucao) {
                    menorExecucao = atual.tempoExecucao;
                }
            }
        }

        ArrayList<Integer> possiveis = new ArrayList<>();

        for (int i = 0; i < listaProcessos.size(); i++) {
            Processo p = listaProcessos.get(i);

            if (p.tempoChegada <= tempoAtual && p.tempoExecucao == menorExecucao) {
                possiveis.add(i);
            }
        }

        if (possiveis.isEmpty()) {
            return -1;
        }

        Random rand = new Random();
        return possiveis.get(rand.nextInt(possiveis.size()));
    }

}