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
        ArrayList<String> linhaTempo = new ArrayList<>();
        System.out.println("\n--- INICIANDO ESCALONAMENTO POR PRIORIDADE ---");

        while (!listaProcessos.isEmpty()) {

            // verifica quem já chegou
            ArrayList<Processo> prontos = new ArrayList<>();

            for (Processo p : listaProcessos) {
                if (p.tempoChegada <= tempoAtual) prontos.add(p); //só entra na fila de prontos quem já chegou
            }

            if (prontos.isEmpty()) {
                linhaTempo.add("*");
                tempoAtual++;
                continue;
            }

            String estado = controlador.avaliarSistema(prontos); //retorna o estado dos prontos

            controlador.aplicarAging(prontos, estado); //implementa o aging e atualiza prioridades baseado no tempo de espera e estado

            // quem tem a MAIOR prioridade atual?

            prontos.sort((p1, p2) -> Integer.compare(p2.prioridadeAtual, p1.prioridadeAtual));

            //esse mét sort pega dois processos por vez ai: dados dois processos p1 e p2, então compare as suas prioridades, ordem decrescente pq queremos o maior entre eles,

            Processo escolhido = prontos.get(0); //após o aterior, pegamos o pronto do ind 0 e colocamos em "processo escolhido"

            // executando por tempo: 1 unidade
            linhaTempo.add(escolhido.nome);
            escolhido.tempoRestante--;  //executa apenas 1 unidade de tempo (escalom preemptivo)
            tempoAtual++; //relógio avança

            //logo depois, venhamos c um for para atualizar tempos de espera de quem não rodou ainda p não acontecer a inanição

            for (Processo p : prontos) {
                if (p != escolhido) p.tempoEspera++;

                //ou seja, soma 1 no seu tempo de espera p que no próximo ciclo, a prioridadeAtual dele seja maior por causa desse +1

                else p.tempoEspera = 0; // zera a espera de quem acabou de usar a cpu p que não continue acumulando priori infinita e deixe os outros "morrerem de fome"
            }

            if (escolhido.tempoRestante <= 0) {
                listaProcessos.remove(escolhido);
                System.out.println(">>> Processo " + escolhido.nome + " finalizado.");
            }


        }
         imprimirTabela(linhaTempo);
    }
    public void imprimirTabela(ArrayList<String> linhaTempo) {

        System.out.println("\nTEMPO");

        for (int i = 0; i < linhaTempo.size(); i++) {
            System.out.print(i + " | ");
        }

        System.out.println("\n--------------------------------------------------");

        for (String p : linhaTempo) {
            System.out.print(p + " | ");
        }

        System.out.println("\nPROCESSOS");
    }
}