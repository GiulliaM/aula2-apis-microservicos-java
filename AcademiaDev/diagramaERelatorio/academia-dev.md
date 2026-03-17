
# Resumo da Arquitetura do Sistema AcademiaDev

  

O sistema foi construído focando em boas práticas de Programação Orientada a Objetos (POO), Manutenibilidade e separação de responsabilidades (Clean Code). As principais escolhas arquiteturais foram:

  

* **Injeção de Dependência e Fonte Única da Verdade:** Em vez de cada classe instanciar seus próprios bancos de dados ou leitores de teclado (`new Scanner`), o `Main` atua como um maestro. Ele cria os recursos globais (o Catálogo de Cursos, a Fila de Tickets, o Mapa de Usuários e o Scanner) e os "injeta" (passa pelos construtores) para as classes que precisam. Isso evita vazamento de memória e garante que todo o sistema olhe para os mesmos dados.

  

* **Princípio da Responsabilidade Única (SRP):** A lógica de interface com o usuário foi fatiada. O `MenuAutenticacao` cuida apenas de entrada. O `MenuAcoesUsuario` funciona apenas como um "Roteador" (avaliando via `instanceof` quem é o usuário). E as lógicas específicas vivem isoladas dentro de `MenuAluno` e `MenuAdmin`. Isso evitou a criação de uma "Classe Deus" (God Class) gigantesca e difícil de dar manutenção.

  

* **Programação Funcional (Fim do `switch-case`):** Em vez de usar blocos `switch-case` enormes e repetitivos para os menus, implementamos a interface funcional `Runnable` do Java 8+ em conjunto com um `Map<Integer, Runnable>`. Usando Referência de Métodos (`this::acao`), os menus funcionam como uma "máquina de botões" com complexidade O(1). Para adicionar uma nova opção no menu, basta adicionar uma linha no construtor, sem alterar o fluxo principal.

  

* **Herança e Polimorfismo:** A utilização de uma classe base `User` permitiu que o sistema armazenasse tanto `Student` quanto `Admin` em um mesmo Dicionário (`Map<String, User>`). O uso de Interfaces (como o plano de assinatura) permite que o sistema cresça facilmente (ex: criar um plano *Enterprise*) sem quebrar o código existente.
