import java.util.ArrayList;
import java.util.Random;

public class EscalonadorPrioridade {

    int bonusNormal;
    int bonusFrustracao;

    ArrayList<Processo> listaProcessos = new ArrayList<>();
    Prioridades controlador = new Prioridades();

    // Construtor que recebe os bônus do aging
    public EscalonadorPrioridade(int bonusNormal, int bonusFrustracao) {
        this.bonusNormal = bonusNormal;
        this.bonusFrustracao = bonusFrustracao;
    }

    // Construtor padrão (caso não passe parâmetros)
    public EscalonadorPrioridade() {
    }

    // Adiciona processos na lista principal
    public void adicionarProcesso(String nome, int tempoChegada, int tempoExecucao, int prioridade) {
        listaProcessos.add(new Processo(nome, tempoChegada, tempoExecucao, prioridade));
    }

    Random rand = new Random();

    public void executar() {

        int tempoAtual = 0;

        // Armazena a linha do tempo da execução para exibição final
        ArrayList<String> linhaTempo = new ArrayList<>();

        System.out.println("\n--- INICIANDO ESCALONAMENTO POR PRIORIDADE ---");

        while (!listaProcessos.isEmpty()) {

            // Filtra processos que já chegaram no sistema
            ArrayList<Processo> prontos = new ArrayList<>();

            for (Processo p : listaProcessos) {
                if (p.tempoChegada <= tempoAtual) {
                    prontos.add(p);
                }
            }

            // Se não há processos prontos, CPU fica ociosa
            if (prontos.isEmpty()) {
                linhaTempo.add("*");
                tempoAtual++;
                continue;
            }

            // Aplica aging ajustando prioridade dinâmica
            controlador.aplicarAging(prontos, bonusNormal, bonusFrustracao);

            // Ordena por maior prioridade atual
            prontos.sort((p1, p2) ->
                    Integer.compare(p2.prioridadeDinamica, p1.prioridadeDinamica));

            // pega a maior prioridade
            int maior = prontos.get(0).prioridadeDinamica;

            // filtra os empatados no topo
            ArrayList<Processo> empatados = new ArrayList<>();
            for (Processo p : prontos) {
                if (p.prioridadeDinamica == maior) {
                    empatados.add(p);
                } else {
                    break; // já que tá ordenado, pode parar
                }
            }

            // sorteia um entre os melhores
            Processo escolhido = empatados.get(rand.nextInt(empatados.size()));


            // Executa 1 unidade de tempo (escalonamento preemptivo)
            linhaTempo.add(escolhido.nome);
            escolhido.tempoExecucao--;
            tempoAtual++;

            // Atualiza tempo de espera dos demais processos
            for (Processo p : prontos) {
                if (p != escolhido) {
                    p.tempoEspera++;
                } else {
                    int penalidade = (p.tempoEspera > 3) ? 2 : 1;
                    p.tempoEspera = Math.max(0, p.tempoEspera - penalidade);
                }
            }

            // Remove processo finalizado
            if (escolhido.tempoExecucao <= 0) {
                listaProcessos.remove(escolhido);
                System.out.println(">>> Processo " + escolhido.nome + " finalizado.");
            }
        }

        // Imprime resultado final (linha do tempo)
        imprimirTabela(linhaTempo);
    }

    // Exibe linha do tempo da execução
    public void imprimirTabela(ArrayList<String> linhaTempo) {

        System.out.println("\nTEMPO");

        for (int i = 0; i < linhaTempo.size(); i++) {
            System.out.print(i + " | ");
        }

        System.out.println("\n----------------------------------------------------------------------");

        for (String p : linhaTempo) {
            System.out.print(p + " | ");
        }

        System.out.println("\nPROCESSOS");
    }
}