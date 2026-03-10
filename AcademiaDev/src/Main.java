import Courses.CourseCatalog;
import Courses.DifficultyLevel;
import Support.SupportTicket;
import Users.Admin;
import Users.Student;
import Users.User;
import systemFunctions.GenericCsvExporter;
import systemFunctions.MenuActionsList;
import systemFunctions.ReportService;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        CourseCatalog catalogo = new CourseCatalog();
        Map<String, User> usuarios = new HashMap<>();
        Queue<SupportTicket> filaDeTicketSuporte = new LinkedList<>();
        Scanner scanner = new Scanner(System.in);

        // 1. Carrega os dados simulados
        InitialData.carregarDados(catalogo, usuarios);

        // 2. Inicializa o controlador do menu com as coleções
        MenuActionsList.inicializar(usuarios, catalogo, filaDeTicketSuporte, scanner);

        // 3. Variável de Controle de Sessão
        User usuarioLogado = null;

        System.out.println("====== Bem-vindo(a) à Academia Dev ======");

        do {
            int escolha;

            // ==========================================
            // ESTADO 1: USUÁRIO NÃO LOGADO (Visitante)
            // ==========================================
            if (usuarioLogado == null) {
                MenuActionsList.mostrarMenuPrincipal();
                escolha = lerOpcao(scanner);

                if (escolha == 0) {
                    System.out.println("Saindo do sistema. Até logo!");
                    break;
                }

                switch (escolha) {
                    case 1:
                        System.out.print("Digite seu email: ");
                        // Atualiza a variável de sessão com o retorno do Login
                        usuarioLogado = MenuActionsList.Login(scanner.nextLine());
                        break;
                    case 2:
                        System.out.print("Nome: ");
                        String nome = scanner.nextLine();
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Plano (basic/premium): ");
                        MenuActionsList.CadastrarAluno(nome, email, scanner.nextLine());
                        break;
                    case 3:
                        MenuActionsList.ListarCursos();
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            }
            // ==========================================
            // ESTADO 2: LOGADO COMO ADMINISTRADOR
            // ==========================================
            else if (usuarioLogado instanceof Admin) {
                MenuActionsList.mostrarMenuAdmin();
                escolha = lerOpcao(scanner);

                if (escolha == 0) {
                    usuarioLogado = null; // Logout
                    System.out.println("Logout realizado com sucesso.");
                    continue; // Volta para o início do loop
                }

                switch (escolha) {
                    case 1:
                        MenuActionsList.AtenderTicketSuporte();
                        break;
                    case 2:
                        // Chamando os relatórios da Stream API
                        System.out.println("\nGERANDO RELATÓRIOS DA PLATAFORMA...");
                        ReportService.listarCursosPorDificuldade(catalogo.getTodosCursos(), DifficultyLevel.ADVANCED);
                        ReportService.listarInstrutoresAtivos(catalogo.getTodosCursos());
                        ReportService.agruparAlunosPorPlano(usuarios.values());
                        ReportService.exibirMediaGeralProgresso(usuarios.values());
                        break;
                    case 3:
                        // Exportando dados com Reflection
                        System.out.println("\nGerando CSV de Cursos...");
                        List<String> colunas = Arrays.asList("title", "instructorName", "difficultyLevel");
                        String csv = GenericCsvExporter.exportarParaCsv(new ArrayList<>(catalogo.getTodosCursos()), colunas);
                        System.out.println(csv);
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            }
            // ==========================================
            // ESTADO 3: LOGADO COMO ALUNO (Student)
            // ==========================================
            else if (usuarioLogado instanceof Student) {
                MenuActionsList.mostrarMenuAluno();
                escolha = lerOpcao(scanner);

                if (escolha == 0) {
                    usuarioLogado = null; // Logout
                    System.out.println("Logout realizado com sucesso.");
                    continue;
                }

                switch (escolha) {
                    case 1:
                        // Lógica de Matrícula (Puxando os métodos que você já fez)
                        System.out.print("Digite o título do curso exato que deseja matricular: ");
                        String tituloCurso = scanner.nextLine();
                        try {
                            // Student alunoLogado = (Student) usuarioLogado;
                            // alunoLogado.matricular(catalogo.buscarCurso(tituloCurso));
                            System.out.println("Funcionalidade de matrícula em construção no menu!");
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage());
                        }
                        break;
                    case 2:
                        System.out.print("Título do Ticket: ");
                        String titulo = scanner.nextLine();
                        System.out.print("Mensagem: ");
                        MenuActionsList.AbrirTicket(usuarioLogado.getEmail(), titulo, scanner.nextLine());
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            }

        } while (true);

        scanner.close();
    }

    // Método auxiliar para evitar repetição de código na leitura do scanner
    private static int lerOpcao(Scanner scanner) {
        System.out.print("Escolha uma opção: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Digite um número válido: ");
            scanner.next();
        }
        int escolha = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer do teclado
        return escolha;
    }
}