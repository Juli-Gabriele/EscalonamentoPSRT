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
        System.out.println("\n--- INICIANDO ESCALONAMENTO POR PRIORIDADE ---");

        while (!listaProcessos.isEmpty()) {

            // verifica quem já chegou
            ArrayList<Processo> prontos = new ArrayList<>();

            for (Processo p : listaProcessos) {
                if (p.tempoChegada <= tempoAtual) prontos.add(p); //só entra na fila de prontos quem já chegou
            }

            if (prontos.isEmpty()) {
                tempoAtual++;
                continue;
            }

            String estado = controlador.avaliarSistema(prontos); //retorna o estado dos prontos

            controlador.aplicarAging(prontos, estado); //implementa o aging e atualiza prioridades baseado no tempo de espera e estado

            // quem tem a MAIOR prioridade atual?

            prontos.sort((p1, p2) -> Integer.compare(p2.prioridadeAtual, p1.prioridadeAtual));

            //esse mét sort pega dois processos por vez ai: dados dois processos p1 e p2, então compare as suas prioridades, ordem decrescente pq queremos o maior entre eles,

            Processo escolhido = prontos.get(0); //após o aterior, pegamos o pronto do ind 0 e colocamos em "processo escolhido"

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