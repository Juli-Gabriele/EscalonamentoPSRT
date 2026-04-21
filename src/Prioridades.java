import java.util.ArrayList;

public class Prioridades {

    // Calcula o estado do sistema com base no tempo médio de espera dos processos
    public String avaliarSistema(ArrayList<Processo> processos) {

        if (processos.isEmpty()) return "CALMA";

        double soma = 0;

        for (Processo p : processos) {
            soma += p.tempoEspera;
        }

        double mediaEspera = soma / processos.size();

        // Se a média de espera for alta, o sistema entra em FRUSTRAÇÃO
        if (mediaEspera > 2) return "FRUSTRAÇÃO";

        return "NORMAL";
    }

    // Aplica o aging ajustando a prioridade com base no tempo de espera
    public void aplicarAging(ArrayList<Processo> processos, String estado,
                             int bonusNormal, int bonusFrustracao) {

        for (Processo p : processos) {

            int bonusEstado;

            // Define o impacto do aging dependendo do estado do sistema
            if (estado.equals("FRUSTRAÇÃO")) {
                bonusEstado = bonusFrustracao;
            } else {
                bonusEstado = bonusNormal;
            }

            // Atualiza a prioridade dinâmica
            p.prioridadeAtual = p.prioridadeOriginal + (p.tempoEspera * bonusEstado);
        }
    }
}