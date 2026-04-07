public class Prioridades {

    // calculando o "estado de estresse" baseado na espera dos processos
    public String avaliarSistema(ArrayList<Processo> processos) {
        if (processos.isEmpty()) return "CALMA";

        double soma = 0;

        for (Processo p : processos) {
            soma += p.tempoEspera;
        }

        double mediaEspera = soma / processos.size();

        if (mediaEspera > 5) return "FRUSTRAÇÃO"; // se, em média, os processos estão esperando há mais de 5 ciclos de cpu, o sistema deve entender que a fila está andando devagar demais, o 5 é só p regra de negócio

        // no caso de frustação, o aging precisa ser agressivo

        return "NORMAL";
    }

    // aplicando o aging (dinâmico), ou seja, ele vai mudar conforme o estado do sistema

    public void aplicarAging(ArrayList<Processo> processos, String estado) {
        for (Processo p : processos) {

            int bonusEstado;

            //esse bônus existe para fazer o sistema reagir quando a fila começa a ficar lenta, ai esse 2 é p "acelerar" a prioridade quando detecta lentidão e o 1 é p funcionamento normal

            if (estado.equals("FRUSTRAÇÃO")) {
                bonusEstado = 2;
            } else {
                bonusEstado = 1; }

            p.prioridadeAtual = p.prioridadeOriginal + (p.tempoEspera * bonusEstado);

            //nisso, a cada ciclo, a prioridade atual sobe e quanto mais tempo espera, maior a prioridadeAtual fica
        }
    }

}
