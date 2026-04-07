import java.util.Scanner;

public class Menu {

    public void iniciar() {
        Scanner sc = new Scanner(System.in);
        EscalonadorPrioridade ec = new EscalonadorPrioridade();
        String opcao;

        System.out.println("******** GERENCIADOR DE PROCESSOS ********\n");

        do {
            System.out.print("Digite o nome do Processo: ");
            String nome = sc.nextLine();

            System.out.print("Digite o Tempo de Chegada do Processo: ");
            int tempoC = sc.nextInt();

            System.out.print("Digite o Tempo de Execução do Processo: ");
            int tempoEx = sc.nextInt();

            System.out.print("Digite a Prioridade: ");
            int prio = sc.nextInt();

            sc.nextLine();

            ec.adicionarProcesso(nome, tempoC, tempoEx, prio);
            System.out.println("Processo '" + nome + "' adicionado com sucesso!\n");
            System.out.print("Deseja adicionar outro Processo? [S/N]: ");
            opcao = sc.nextLine();
            System.out.println();

        } while (opcao.equalsIgnoreCase("s"));

        System.out.println("Todos os processos foram adicionados!");
        System.out.println("Iniciando escalonamento SRT");
        ec.imprimirResultado();
        sc.close();
    }
}