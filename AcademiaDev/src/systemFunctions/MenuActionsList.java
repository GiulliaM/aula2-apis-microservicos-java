package systemFunctions;

import Courses.BasicPlan;
import Courses.CourseCatalog;
import Courses.PremiumPlan;
import Courses.SubscriptionPlan;
import Support.SupportTicket;
import Users.Student;
import Users.User;
import systemFunctions.Actions.Login;

import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class    MenuActionsList {
    public static Map<String, User> usuarios;
    public static CourseCatalog catalogo;
    public static Queue<SupportTicket> filaDeTicketSuporte;
    public static Scanner scanner;

    public static void inicializar(Map<String, User> usuariosMap, CourseCatalog cat, Queue<SupportTicket> fila, Scanner sc) {
        usuarios = usuariosMap;
        catalogo = cat;
        filaDeTicketSuporte = fila;
        scanner = sc;
    }

    // Alterado para retornar o User para o Main saber quem logou
    public static User Login(String email) {
        // Cria a ação com os dados
        Login loginAction = new Login(email, usuarios);

        // Executa a regra de negócio
        loginAction.execute();

        // Retorna o usuário logado (ou null se falhou)
        return loginAction.getUsuarioLogado();
    }

    public static void CadastrarAluno(String nome, String email, String tipoPlano) {
        if (usuarios.containsKey(email)) {
            System.out.println("Já existe um usuário com esse email.");
            return;
        }
        SubscriptionPlan plano = tipoPlano.equalsIgnoreCase("premium") ? new PremiumPlan() : new BasicPlan();
        Student novo = new Student(nome, email, plano);
        usuarios.put(email, novo);
        System.out.println("Aluno cadastrado com sucesso!");
    }

    public static void AbrirTicket(String email, String titulo, String mensagem) {
        User user = usuarios.get(email);
        if (user == null) {
            System.out.println("Usuário não encontrado para abrir o ticket!");
            return;
        }
        SupportTicket ticket = new SupportTicket(user, titulo, mensagem);
        filaDeTicketSuporte.add(ticket); // Adiciona no fim da fila
        System.out.println("Ticket aberto e adicionado à fila de atendimento!");
    }

    // NOVO: Processamento da fila (First-In, First-Out)
    public static void AtenderTicketSuporte() {
        if (filaDeTicketSuporte.isEmpty()) {
            System.out.println("A fila de suporte está vazia. Excelente trabalho!");
            return;
        }
        // O método poll() pega e REMOVE o primeiro da fila (FIFO)
        SupportTicket ticketAtendido = filaDeTicketSuporte.poll();
        System.out.println("\n--- Atendendo Ticket ---");
        System.out.println("De: " + ticketAtendido.getUsuarioSolicitante().getName());
        System.out.println("Assunto: " + ticketAtendido.getTitle());
        System.out.println("Mensagem: " + ticketAtendido.getMessage());
        System.out.println("------------------------");
        System.out.println("Ticket resolvido com sucesso!");
    }

    public static void ListarCursos() {
        System.out.println("Cursos disponíveis:");
        catalogo.listarCursos();
    }

    public static void mostrarMenuPrincipal() {
        System.out.println("\n===== ACADEMIA DEV =====");
        System.out.println("1 - Login");
        System.out.println("2 - Cadastrar Novo Aluno");
        System.out.println("3 - Listar Catálogo de Cursos");
        System.out.println("0 - Sair do Sistema");
    }

    public static void mostrarMenuAdmin() {
        System.out.println("\n=== PAINEL DO ADMINISTRADOR ===");
        System.out.println("1 - Atender Ticket de Suporte (Fila FIFO)");
        System.out.println("2 - Gerar Relatórios da Plataforma");
        System.out.println("3 - Exportar Lista de Cursos (CSV)");
        System.out.println("0 - Logout (Voltar)");
    }

    public static void mostrarMenuAluno() {
        System.out.println("\n=== PAINEL DO ALUNO ===");
        System.out.println("1 - Matricular-se em um Curso");
        System.out.println("2 - Abrir Ticket de Suporte");
        System.out.println("0 - Logout (Voltar)");
    }
}