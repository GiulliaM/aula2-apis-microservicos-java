import Courses.*;
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

            //usuario como visitante
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

            //usuario logado como administrador
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
                        System.out.println("\nGERANDO RELATÓRIOS DA PLATAFORMA...");
                        ReportService.listarCursosPorDificuldade(catalogo.getTodosCursos(), DifficultyLevel.ADVANCED);
                        ReportService.listarInstrutoresAtivos(catalogo.getTodosCursos());
                        ReportService.agruparAlunosPorPlano(usuarios.values());
                        ReportService.exibirMediaGeralProgresso(usuarios.values());

                        // Imprimindo o Optional<Student> do aluno com mais matrículas
                        Optional<Student> topAluno = ReportService.getAlunoComMaisMatriculas(usuarios.values());
                        if (topAluno.isPresent()) {
                            Student aluno = topAluno.get();
                            System.out.println("\n--- Top Aluno ---\nO aluno com mais matrículas é: " + aluno.getName() + " (" + aluno.getListaDeMatriculasAtuais().size() + " matrículas)");
                        } else {
                            System.out.println("\n--- Top Aluno ---\nAinda não há alunos matriculados na plataforma.");
                        }
                        break;
                    case 3:
                        System.out.println("\nGerando CSV de Cursos...");
                        List<String> colunas = Arrays.asList("title", "instructorName", "difficultyLevel", "status");
                        String csv = GenericCsvExporter.exportarParaCsv(new ArrayList<>(catalogo.getTodosCursos()), colunas);
                        System.out.println(csv);
                        break;
                    case 4:
                        System.out.print("Digite o título exato do curso para alterar o status: ");
                        String tituloCurso = scanner.nextLine();
                        Course curso = catalogo.buscarCurso(tituloCurso);

                        if (curso == null) {
                            System.out.println("Curso não encontrado.");
                        } else {
                            System.out.println("Status atual: " + curso.getStatus());
                            System.out.print("Deseja alterar para qual status? (1 - ACTIVE / 2 - INACTIVE): ");
                            int opStatus = lerOpcao(scanner);
                            if (opStatus == 1) {
                                curso.setStatus(CourseStatus.ACTIVE);
                                System.out.println("Curso ativado com sucesso!");
                            } else if (opStatus == 2) {
                                curso.setStatus(CourseStatus.INACTIVE);
                                System.out.println("Curso inativado com sucesso!");
                            } else {
                                System.out.println("Opção inválida.");
                            }
                        }
                        break;
                    case 5:
                        System.out.print("Digite o email do aluno para alterar o plano: ");
                        String emailAluno = scanner.nextLine();
                        User usuarioEncontrado = usuarios.get(emailAluno);

                        if (usuarioEncontrado instanceof Student) {
                            Student alunoParaAlterar = (Student) usuarioEncontrado;
                            System.out.println("Plano atual do aluno: " + alunoParaAlterar.getPlano().getClass().getSimpleName());
                            System.out.print("Deseja alterar para qual plano? (1 - Basic / 2 - Premium): ");
                            int opPlano = lerOpcao(scanner);

                            if (opPlano == 1) {
                                alunoParaAlterar.setPlano(new BasicPlan());
                                System.out.println("Plano alterado para BasicPlan com sucesso!");
                            } else if (opPlano == 2) {
                                alunoParaAlterar.setPlano(new PremiumPlan());
                                System.out.println("Plano alterado para PremiumPlan com sucesso!");
                            } else {
                                System.out.println("Opção inválida.");
                            }
                        } else {
                            System.out.println("Aluno não encontrado com este email.");
                        }
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            }

            //usuario logado como estudante
            else if (usuarioLogado instanceof Student) {
                Student alunoLogado = (Student) usuarioLogado;
                MenuActionsList.mostrarMenuAluno();
                escolha = lerOpcao(scanner);

                if (escolha == 0) {
                    usuarioLogado = null; // Logout
                    System.out.println("Logout realizado com sucesso.");
                    continue;
                }

                switch (escolha) {
                    case 1: { // MATRICULAR-SE
                        System.out.println("\n--- Catálogo de Cursos Disponíveis ---");
                        MenuActionsList.ListarCursos();
                        System.out.println("--------------------------------------");

                        System.out.print("\nDigite o título exato do curso que deseja se matricular: ");
                        String tituloCurso = scanner.nextLine();

                        try {
                            Course cursoDesejado = catalogo.buscarCurso(tituloCurso);
                            if (cursoDesejado == null) {
                                System.out.println("Curso não encontrado no catálogo.");
                            } else {
                                alunoLogado.matricular(cursoDesejado);
                                System.out.println("Matrícula realizada com sucesso no curso: " + cursoDesejado.getTitle());
                            }
                        } catch (Exception e) {
                            System.out.println("Erro na matrícula: " + e.getMessage());
                        }
                        break;
                    }
                    case 2: {
                        System.out.println("\n--- Minhas Matrículas ---");
                        List<Enrollment> minhasMatriculas = alunoLogado.getListaDeMatriculasAtuais();

                        if (minhasMatriculas.isEmpty()) {
                            System.out.println("Você ainda não está matriculado em nenhum curso.");
                        } else {
                            minhasMatriculas.forEach(m ->
                                    System.out.println("- " + m.getCursoMatriculado().getTitle() + " | Progresso: " + m.getProgress() + "%")
                            );
                        }
                        break;
                    }
                    case 3: {
                        System.out.print("Digite o título do curso que deseja atualizar o progresso: ");
                        String tituloAtualizar = scanner.nextLine();

                        Optional<Enrollment> matriculaEncontrada = alunoLogado.getListaDeMatriculasAtuais().stream()
                                .filter(m -> m.getCursoMatriculado().getTitle().equalsIgnoreCase(tituloAtualizar))
                                .findFirst();

                        if (matriculaEncontrada.isPresent()) {
                            System.out.print("Digite o novo percentual de conclusão (0 a 100): ");
                            if (scanner.hasNextInt()) {
                                int novoProgresso = scanner.nextInt();
                                scanner.nextLine(); // limpar buffer

                                try {
                                    matriculaEncontrada.get().setProgress(novoProgresso);
                                    System.out.println("Progresso atualizado com sucesso para " + novoProgresso + "%!");
                                } catch (Exception e) {
                                    System.out.println("Erro: " + e.getMessage());
                                }
                            } else {
                                System.out.println("Valor inválido. Digite um número inteiro.");
                                scanner.nextLine();
                            }
                        } else {
                            System.out.println("Você não está matriculado no curso: " + tituloAtualizar);
                        }
                        break;
                    }
                    case 4: {
                        System.out.print("Digite o título do curso que deseja cancelar a matrícula: ");
                        String tituloCancelar = scanner.nextLine();

                        boolean removido = alunoLogado.getListaDeMatriculasAtuais().removeIf(
                                m -> m.getCursoMatriculado().getTitle().equalsIgnoreCase(tituloCancelar)
                        );

                        if (removido) {
                            System.out.println("Matrícula cancelada com sucesso. Vaga liberada no seu plano!");
                        } else {
                            System.out.println("Você não está matriculado no curso: " + tituloCancelar);
                        }
                        break;
                    }
                    case 5: {
                        System.out.print("Título do Ticket: ");
                        String titulo = scanner.nextLine();
                        System.out.print("Mensagem: ");
                        MenuActionsList.AbrirTicket(alunoLogado.getEmail(), titulo, scanner.nextLine());
                        break;
                    }
                    case 6: {
                        System.out.println("\n--- Catálogo de Cursos ---");
                        MenuActionsList.ListarCursos();
                        break;
                    }
                    default:
                        System.out.println("Opção inválida!");
                }
            }

        } while (true);

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