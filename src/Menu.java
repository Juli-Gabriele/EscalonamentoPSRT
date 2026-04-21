import java.util.Scanner;

public class Menu {

    Scanner sc = new Scanner(System.in);

    EscalonadorPrioridade ep = new EscalonadorPrioridade();
    String opcao;
    int tempoC, tempoEx, prioridade;

    public void iniciar() {

        System.out.println("******** ESCALONADOR POR PRIORIDADE ********\n");

        do {
            System.out.print("Digite o nome do Processo: ");
            String nome = sc.nextLine();

            VerificarTempoDeChegada();
            VerificarTempoDeExecucao();
            VerificarPrioridade();

              ep.adicionarProcesso(nome, tempoC, tempoEx, prioridade);

            System.out.println("Processo '" + nome + "' adicionado com sucesso!\n");

            System.out.print("Deseja adicionar outro Processo? [S/N]: ");
            opcao = sc.nextLine();
            System.out.println();

        } while (opcao.equalsIgnoreCase("s"));

        System.out.println("Todos os processos foram adicionados!");
        System.out.println("Iniciando escalonamento por PRIORIDADE...");
        System.out.println("**Imprimindo Tabela de Execução**");

         ep.executar();

        sc.close();
    }

    public void VerificarTempoDeChegada() {

        while (true) {
            System.out.print("Digite o Tempo de Chegada do Processo: ");

            while (!sc.hasNextInt()) {
                System.out.println("Entrada inválida! Digite um número inteiro:");
                sc.next();
            }

            tempoC = sc.nextInt();
            sc.nextLine();

            if (tempoC >= 0) {
                break;
            } else {
                System.out.println("O tempo de chegada não pode ser negativo!");
            }
        }
    }

    public void VerificarTempoDeExecucao() {

        while (true) {
            System.out.print("Digite o Tempo de Execução do Processo: ");

            while (!sc.hasNextInt()) {
                System.out.println("Entrada inválida! Digite um número inteiro:");
                sc.next();
            }

            tempoEx = sc.nextInt();
            sc.nextLine();

            if (tempoEx > 0) {
                break;
            } else {
                System.out.println("O tempo de execução deve ser maior que zero!");
            }
        }
    }


    public void VerificarPrioridade() {

        while (true) {
            System.out.print("Digite a Prioridade do Processo: ");

            while (!sc.hasNextInt()) {
                System.out.println("Entrada inválida! Digite um número inteiro:");
                sc.next();
            }

            prioridade = sc.nextInt();
            sc.nextLine();

            if (prioridade >= 0) {
                break;
            } else {
                System.out.println("A prioridade não pode ser negativa!");
            }
        }
    }
}
