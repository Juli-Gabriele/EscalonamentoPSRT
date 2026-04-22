
 # 📊 Escalonador de Processos — Algoritmo de Prioridade Dinâmica (Aging)
 ### 1. Estrutura de Dados e Configuração de Bônus

Diferente do escalonador anterior, este sistema utiliza um esquema de **Prioridades Dinâmicas**. Ele permite configurar bônus que podem ser aplicados aos processos para evitar que tarefas com baixa prioridade fiquem "esquecidas" na fila (problema conhecido como *starvation*).

<img width="1089" height="521" alt="image" src="https://github.com/user-attachments/assets/75c02879-a748-449b-89b7-18d6d37c75d2" />



#### Explicação Técnica:
* **Bônus (`bonusNormal` e `bonusFrustracao`)**: Variáveis que permitem ajustar a prioridade dos processos ao longo do tempo. O bônus de "frustração" é uma técnica clássica para aumentar a prioridade de um processo que está esperando há muito tempo.
* **Controlador de Prioridades**: O código utiliza uma classe auxiliar chamada `Prioridades` para gerenciar as regras de precedência.
* **Sobrecarga de Construtores**: A classe permite ser iniciada com valores de bônus customizados ou com valores padrão, conferindo flexibilidade ao simulador.
* **`adicionarProcesso()`**: Além do tempo de chegada e execução, este método agora recebe o parâmetro **Prioridade**, essencial para a lógica deste algoritmo.
### 2. Gerenciamento da Fila de Prontos e Ociosidade

O método `executar` é o motor do escalonador. Antes de aplicar qualquer regra de prioridade, o sistema precisa identificar quais processos estão aptos a usar a CPU no momento atual.

<img width="884" height="559" alt="image" src="https://github.com/user-attachments/assets/b99e4ba7-07a6-41fb-8d64-c63183e64229" />


#### Explicação Técnica:
* **Fila de Prontos (`ArrayList<Processo> prontos`)**: A cada unidade de tempo, o sistema cria uma sublista contendo apenas os processos cujo `tempoChegada` é menor ou igual ao `tempoAtual`. Isso garante que a CPU não tente executar um processo que ainda não "nasceu" no sistema.
* **Tratamento de CPU Ociosa**: Caso a lista de prontos esteja vazia (ou seja, existem processos no sistema, mas nenhum chegou ainda), o algoritmo adiciona um símbolo de ociosidade (`*`) à linha do tempo e avança o relógio. Isso evita loops infinitos e garante a precisão do gráfico final.
* **Registro Temporal (`linhaTempo`)**: Assim como no SRT, utilizamos uma lista de strings para mapear cada segundo da execução, preparando o terreno para a visualização do gráfico de Gantt.

### 3. Dinâmica de Aging e Seleção de Prioridade

Nesta etapa, o escalonador aplica as regras de negócio para decidir qual processo é o mais importante no momento, utilizando técnicas para garantir a justiça no uso da CPU.

<img width="948" height="634" alt="image" src="https://github.com/user-attachments/assets/0d60bf17-a741-415b-932c-5e5ab769184f" />

#### Explicação Técnica:
* **Mecanismo de Aging (Envelhecimento)**: O método `controlador.aplicarAging` utiliza os bônus configurados no início para aumentar a `prioridadeAtual` dos processos que estão na fila de prontos. Isso garante que processos com prioridade baixa não fiquem "presos" para sempre na fila.
* **Ordenação (`sort`)**: O sistema utiliza uma expressão Lambda para ordenar a lista de processos prontos em ordem decrescente de prioridade (`p2` vs `p1`). O processo no topo da lista (`get(0)`) é o escolhido para execução.
* **Controle de Espera**: 
    * Se o processo foi escolhido, o seu `tempoEspera` volta para zero.
    * Se o processo continuou na fila, o seu `tempoEspera` aumenta, o que alimentará o próximo ciclo de bônus por "frustração".
* **Execução Preemptiva**: O processo executa apenas 1 unidade de tempo por vez, permitindo que o sistema reavalie as prioridades a cada ciclo e possa interromper a tarefa caso surja algo mais urgente.


### 4. Visualização do Escalonamento (Gráfico de Gantt)

Após o processamento de todas as prioridades e bônus, o sistema consolida o histórico de execução em uma tabela formatada para análise.

<img width="1060" height="626" alt="image" src="https://github.com/user-attachments/assets/09867a5b-271e-4abd-82dd-2fc0f43aa6ac" />


#### Explicação Técnica:
* **Linha Temporal Dinâmica**: O primeiro laço `for` gera os marcos de tempo da CPU (0, 1, 2...). Isso é essencial para identificar em qual exato momento um processo foi interrompido por outro de maior prioridade.
* **Mapeamento de Processos**: O segundo laço percorre a `linhaTempo`, exibindo o nome do processo que obteve a maior prioridade naquele instante.
* **Rastro de Ociosidade**: O gráfico também revela os momentos em que a CPU ficou ociosa (marcados com `*`), permitindo validar se o tempo de chegada dos processos foi respeitado.

### 5. Lógica de Avaliação de Estado (Sentimento do Sistema)

Este método funciona como um termômetro para o processador, identificando quando os processos estão esperando por tempo demais e mudando o comportamento do escalonador para garantir a fluidez.

<img width="1001" height="477" alt="image" src="https://github.com/user-attachments/assets/15bfd0cc-83b2-4747-a199-b0d776c80d52" />


#### Explicação Técnica:
* **Média de Espera**: O sistema soma o `tempoEspera` de todos os processos que já chegaram e divide pela quantidade de processos na fila. Isso gera uma métrica real de quão "congestionado" o sistema está.
* **Estados de Operação**:
    * **CALMA**: Retornado quando não há processos para executar.
    * **NORMAL**: O sistema opera com bônus padrão, pois a espera média está dentro do limite aceitável.
    * **FRUSTRAÇÃO**: Ativado quando a `mediaEspera > 2`. É um estado de alerta onde o sistema reconhece que os processos estão parados há muito tempo, disparando bônus de *Aging* mais altos para limpar a fila rapidamente.

    ### 6. Mecanismo de Aging (Envelhecimento de Processos)

O método `aplicarAging` é responsável por recalcular a prioridade de cada processo em tempo real. Ele garante que a prioridade de um processo aumente proporcionalmente ao tempo que ele passa esperando na fila.

<img width="1133" height="552" alt="image" src="https://github.com/user-attachments/assets/51048194-fff9-41f0-9df8-1310e2b24453" />



#### Explicação Técnica:
* **Bônus Adaptativo**: O código escolhe entre o `bonusNormal` ou o `bonusFrustracao` baseando-se no estado atual do sistema. Se o sistema estiver em "FRUSTRAÇÃO", o bônus aplicado é maior, acelerando a subida do processo na fila.
* **Cálculo da Prioridade Dinâmica**: A nova prioridade é calculada pela fórmula:  
  **Prioridade Atual = Prioridade Original + (Tempo de Espera × Bônus)** Isso significa que, quanto mais tempo um processo espera, maior fica sua prioridade, permitindo que ele eventualmente supere processos que chegaram depois com prioridades originais mais altas.
* **Prevenção de Starvation**: Essa lógica resolve o problema de *Starvation* (fome), assegurando que processos de baixa prioridade não sejam ignorados infinitamente por processos de alta prioridade que chegam constantemente.

### 7. Modelo de Dados: Classe Processo

A classe `Processo` é a base (POJO - Plain Old Java Object) do simulador. Ela armazena tanto os dados estáticos fornecidos pelo usuário quanto os dados dinâmicos calculados pelo escalonador durante a execução.


<img width="1130" height="469" alt="image" src="https://github.com/user-attachments/assets/9851526f-e65e-4db0-b189-fc4f677c4a0f" />


#### Explicação Técnica dos Atributos:
* **Dados de Entrada**:
    * `nome`: Identificador do processo.
    * `tempoChegada`: Momento em que o processo entra no sistema.
    * `tempoExecucao`: Tempo total de CPU requerido pelo processo, reduzido progressivamente a cada execução.
    * `prioridade`: Nível de prioridade do processo no sistema, ajustado a cada execução com base no bônus do usuário.
* **Dados de Controle (Dinâmicos)**:
    * `tempoEspera`: Contador de quanto tempo o processo ficou na fila de prontos sem executar, essencial para o cálculo de frustração do sistema.

    ### 8. Configuração de Parâmetros de Escalonamento

Este trecho do método `iniciar` é onde o usuário calibra o comportamento do algoritmo. Ao definir os bônus, o usuário decide quão rápido a prioridade dos processos deve subir em situações normais e críticas.

<img width="1083" height="534" alt="image" src="https://github.com/user-attachments/assets/68fd5720-1d20-4018-9c05-c34ba66f586d" />




#### Explicação Técnica:
* **Customização de Bônus**: O sistema solicita dois valores distintos:
    * `bonusNormal`: Aplicado em condições estáveis de operação.
    * `bonusFrustracao`: Geralmente um valor maior, aplicado para resolver gargalos quando a espera média é alta.
* **Instanciação Dinâmica**: O objeto `EscalonadorPrioridade` é criado passando esses parâmetros para o construtor, o que permite que a mesma lógica de código se comporte de maneira diferente dependendo da configuração inicial (simulando diferentes políticas de Sistema Operacional).
* **Modularização**: O uso do método auxiliar `lerBonus` (que deve conter validações similares às que vimos anteriormente) garante que o sistema não receba valores inválidos antes mesmo de começar a simulação.

  ### 9. Ciclo de Entrada e Disparo do Escalonamento

Este trecho consolida a coleta de dados de cada processo e inicia a simulação. É o ponto de transição entre a interface de entrada e o processamento lógico do algoritmo.

<img width="1031" height="566" alt="image" src="https://github.com/user-attachments/assets/fc4ade07-8be7-40bf-aa55-7009940f52c0" />


#### Explicação Técnica:
* **Coleta Multivariada**: Para cada processo, o sistema invoca três validações distintas (`Chegada`, `Execução` e `Prioridade`), garantindo que o objeto `Processo` seja instanciado com todos os parâmetros necessários para o cálculo do envelhecimento (*aging*).
* **Persistência Temporária**: O método `ep.adicionarProcesso` armazena os dados na lista interna do escalonador, permitindo que o usuário visualize a confirmação de sucesso antes de decidir se deseja inserir novos itens.
* **Início da Simulação**: Ao sair do laço (quando o usuário digita 'N'), o comando `ep.executar()` é acionado. Este é o momento em que a CPU fictícia começa a processar a fila, aplicar as regras de prioridade dinâmica e, por fim, gerar o gráfico de execução.

### 10. Validação de Regras de Negócio (Tempo de Chegada)

Um escalonador de processos precisa de dados precisos para funcionar. Este método implementa uma camada de proteção que impede a entrada de valores inconsistentes ou tipos de dados incorretos.

<img width="1036" height="561" alt="image" src="https://github.com/user-attachments/assets/5b27d591-7b3b-445e-a85a-79daf59833a3" />


#### Explicação Técnica:
* **Tratamento de Exceções de Entrada**: O uso do `while (!sc.hasNextInt())` é uma técnica de programação defensiva. Se o usuário digitar uma letra ou caractere especial, o programa captura o erro, avisa o usuário e limpa o buffer, em vez de encerrar a execução abruptamente (crash).
* **Consistência Temporal**: O algoritmo garante que o `tempoChegada` seja sempre maior ou igual a zero ($$T \geq 0$$). Na teoria de sistemas operacionais, não existe tempo negativo para a criação de um processo, e essa validação mantém a simulação fiel à realidade.
* **Controle de Fluxo**: O laço `while (true)` só é interrompido (`break`) quando todas as condições de validade são atendidas, assegurando que o objeto `Processo` nunca seja criado com dados corrompidos.

### 11. Validação de Nível de Prioridade

Este método assegura que o valor de prioridade inicial do processo seja um número inteiro válido e positivo, estabelecendo o ponto de partida para os cálculos de bônus dinâmico.

<img width="1036" height="561" alt="image" src="https://github.com/user-attachments/assets/1697d854-f0a8-43fd-87f4-748e0f6787f9" />


#### Explicação Técnica:
* **Integridade dos Dados**: Assim como nas validações anteriores, o sistema utiliza o `sc.hasNextInt()` para evitar que o programa pare de funcionar caso o usuário insira textos ou símbolos.
* **Escala de Prioridade**: Ao garantir que a `prioridade >= 0`, o código estabelece uma base sólida para a fórmula de envelhecimento. Valores negativos poderiam causar comportamentos inesperados na ordenação (`sort`) dos processos.
* **Sincronização do Scanner**: O comando `sc.nextLine()` após o `nextInt()` é fundamental para "limpar" a quebra de linha do teclado, evitando que o próximo campo de texto (como o nome do próximo processo) seja pulado acidentalmente.

  ### 12. Reutilização de Código: Método Auxiliar de Leitura

Para manter o código organizado e evitar a repetição de lógica, foi implementado o método `lerBonus`. Ele centraliza a captura e validação de números inteiros, servindo de suporte para a configuração inicial do sistema.

<img width="1008" height="529" alt="image" src="https://github.com/user-attachments/assets/a7479b08-643f-404b-a7cd-b2eccbf1684c" />


#### Explicação Técnica:
* **Abstração e Parâmetros**: O método recebe uma `String mensagem`, o que permite usar a mesma lógica para pedir tanto o bônus normal quanto o de frustração, apenas mudando o texto exibido ao usuário.
* **Robustez na Entrada**: Assim como nas outras verificações, este método utiliza um laço infinito que só é quebrado quando um valor inteiro positivo é inserido. Isso garante que as variáveis `bonusNormal` e `bonusFrustracao` nunca comecem com valores inválidos.
* **Eficiência**: Ao isolar essa lógica, o método `iniciar()` fica muito mais limpo e fácil de ler, focando apenas no fluxo do programa e não nos detalhes técnicos de como o Scanner funciona.

 # 📊 Demonstração de Escalonamento (Prioridade com Aging)

O diferencial deste algoritmo é a capacidade de reagir ao estado do sistema, garantindo que processos com menor prioridade não fiquem parados indefinidamente (Starvation).

### Cenário de teste utilizado para validação da lógica:
* **Bônus Normal:** 1 
* **Bônus Frustração:** 2
* **P1:** Chegada: 0 | Duração: 4 | Prioridade: 10
* **P2:** Chegada: 1 | Duração: 2 | Prioridade: 1

### Diagrama de Gantt

| Tempo | 0 | 1 | 2 | 3 | 4 | 5 |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **CPU** | P1 | P1 | P1 | P1 | P2 | P2 |

**Análise técnica:** No instante **T=1**, o processo **P2** entra no sistema com prioridade baixa (1). Embora o sistema aplique o **Aging** (envelhecimento) a cada ciclo, a prioridade de **P1** (10) ainda é muito superior. O escalonador mantém **P1** em execução até sua finalização no tempo **T=4**. Durante esse período, a prioridade de **P2** foi sendo incrementada dinamicamente, garantindo que ele assumisse a CPU imediatamente após a saída de **P1**.

---

### 📂 Estrutura do Projeto

O software foi desenvolvido em **Java**, estruturado de forma modular seguindo princípios de POO (Programação Orientada a Objetos):

* **Processo.java:** Classe de modelo que encapsula os atributos do processo (ID, tempos de chegada, execução, tempo restante e as prioridades original e atual).
* **EscalonadorPrioridade.java:** Implementa o motor principal de decisão, gerenciando a fila de prontos e a execução preemptiva (ciclo a ciclo).
* **Prioridades.java:** Classe controladora responsável por avaliar o "estado de frustração" do sistema e aplicar os bônus de envelhecimento.
* **Menu.java:** Interface via terminal (CLI) para entrada sanitizada de parâmetros e exibição do log de execução e estatísticas.

---

### 👩‍💻 Equipe de Desenvolvimento

* **Gustavo Antony**
* **Julia Gabriele**
* **Karine Vitória**
* **Lucas Santana**

> **Instituição:** Instituto Federal do Triângulo Mineiro (IFTM)  
> **Curso:** Análise e Desenvolvimento de Sistemas (TADS)  
> **Disciplina:** Sistemas Operacionais
