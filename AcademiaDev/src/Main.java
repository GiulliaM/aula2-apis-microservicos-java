import Courses.CourseCatalog;
import Support.FilaSupportTicket;
import Users.User;
import systemFunctions.Actions.MenuAcoesUsuario;
import systemFunctions.Actions.MenuAutenticacao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1. CRIANDO AS FERRAMENTAS GLOBAIS (O Palco)
        Scanner scannerGlobal = new Scanner(System.in);
        Map<String, User> mapaUsuariosGlobal = new HashMap<>();
        CourseCatalog catalogoCursosGlobal = new CourseCatalog();
        FilaSupportTicket filaTicketsGlobal = new FilaSupportTicket();

        // (Opcional) Se a sua classe InitialData tiver um método para carregar dados de teste,
        // você poderia chamá-lo aqui, passando esses mapas/catálogos!

        // 2. INSTANCIANDO O MENU DE ENTRADA
        MenuAutenticacao menuAuth = new MenuAutenticacao(scannerGlobal, mapaUsuariosGlobal);

        boolean sistemaRodando = true;

        System.out.println("Bem-vindo à plataforma AcademiaDev! 🚀");

        // 3. O LOOP PRINCIPAL DO SISTEMA
        while (sistemaRodando) {
            System.out.println("\n--- TELA INICIAL ---");
            System.out.println("[1] Cadastrar novo usuário");
            System.out.println("[2] Fazer Login");
            System.out.println("[3] Desligar o sistema");
            System.out.print("Escolha uma opção: ");

            int opcaoInicial = -1;
            try {
                opcaoInicial = Integer.parseInt(scannerGlobal.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
                continue; // Volta para o início do while
            }

            Optional<User> tentativaDeAcesso = Optional.empty();

            switch (opcaoInicial) {
                case 1:
                    tentativaDeAcesso = menuAuth.cadastro();
                    break;
                case 2:
                    tentativaDeAcesso = menuAuth.login();
                    break;
                case 3:
                    sistemaRodando = false;
                    System.out.println("Encerrando o sistema. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

            // 4. A MÁGICA DA AULA 02 (O Optional)
            // Se o login/cadastro deu certo, a "caixa" terá um usuário dentro!
            if (tentativaDeAcesso.isPresent()) {
                User usuarioLogado = tentativaDeAcesso.get();

                // Criamos o Roteador passando TODAS as ferramentas globais para ele
                MenuAcoesUsuario menuAcoesUsuario = new MenuAcoesUsuario(
                        scannerGlobal,
                        usuarioLogado,
                        catalogoCursosGlobal,
                        filaTicketsGlobal,
                        mapaUsuariosGlobal
                );

                // Mandamos o menuAcoesUsuario exibir o menu (Aluno ou Admin)
                menuAcoesUsuario.exibirMenu();
                // Quando o usuário apertar [4] para Sair no menu dele, o código volta para cá!
                System.out.println("Você saiu da sua conta.");
            }
        }

        // Fechando o microfone apenas no final de tudo
        scannerGlobal.close();
    }
}