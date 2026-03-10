import Courses.Course;
import Courses.CourseCatalog;
import Courses.DifficultyLevel;
import Courses.EnrollmentException;
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


            //1: USUÁRIO NÃO LOGADO (Visitante)

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

            //2: LOGADO COMO ADMINISTRADOR

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

            //3: LOGADO COMO ALUNO (Student)

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
                        // 1. mostra catalogo de cursos antes da matricula
                        System.out.println("\n--- Catálogo de Cursos Disponíveis ---");
                        MenuActionsList.ListarCursos();
                        System.out.println("--------------------------------------");

                        // 2. pede nome do curso para matricula
                        System.out.print("\nDigite o título exato do curso que deseja se matricular: ");
                        String tituloCurso = scanner.nextLine();

                        try {
                            // 3. busca cursos na catalogo
                            Course cursoDesejado = catalogo.buscarCurso(tituloCurso);

                            if (cursoDesejado == null) {
                                System.out.println("Curso não encontrado no catálogo. Verifique o nome e tente novamente.");
                            } else {
                                // 4. Efetiva a matrícula usando a lógica blindada da sua classe Student
                                Student alunoLogado = (Student) usuarioLogado;
                                alunoLogado.matricular(cursoDesejado);
                                System.out.println("Matrícula realizada com sucesso no curso: " + cursoDesejado.getTitle());
                            }
                        } catch (EnrollmentException e) {
                            System.out.println("Erro na matrícula: " + e.getMessage());
                        } catch (Exception e) {
                            System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
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