import java.util.ArrayList;

public class Prioridades {


    // Aplica o aging ajustando a prioridade com base no tempo de espera
    public void aplicarAging(ArrayList<Processo> processos,
                             int bonusNormal, int bonusFrustracao) {

        for (Processo p : processos) {

            int bonusEstado;
            final int LIMITE_FRUSTRACAO = 3;

            int bonus = bonusNormal + (p.tempoEspera >= LIMITE_FRUSTRACAO ? bonusFrustracao : 0);

            p.prioridadeDinamica = p.prioridade + (p.tempoEspera * bonus);

        }
    }
}