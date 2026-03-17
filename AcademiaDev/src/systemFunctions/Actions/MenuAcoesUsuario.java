package systemFunctions.Actions;

import Courses.CourseCatalog;
import Support.FilaSupportTicket;
import Users.Admin;
import Users.Student;
import Users.User;

import java.util.Map;
import java.util.Scanner;

public class MenuAcoesUsuario {
    //exibe o menu de funcoes respectivas para cada ususario

    //map de funcoes do aluno
    MenuAluno menuAluno;

    //map de funcoes do adm
    MenuAdmin menuAdmin;


    private Scanner scanner;
    private int escolha =-1;
    private User usuarioLogado;
    private CourseCatalog catalogoCursos;
    private FilaSupportTicket filaTickets;
    private Map<String, User> mapUsuarios;

    public MenuAcoesUsuario(Scanner scaner, User usuarioLogado, CourseCatalog catalogoCursos, FilaSupportTicket filaTickets, Map<String, User> mapUsuarios) {
        this.scanner = new Scanner(System.in);
        this.usuarioLogado = usuarioLogado;
        this.catalogoCursos = catalogoCursos;
        this.filaTickets = filaTickets;
        this.mapUsuarios = mapUsuarios;
    }

    public void exibirMenu() {
        if (usuarioLogado instanceof Users.Student) {
            //converter o susario logado para a classe Studant
            Student alunoLogado = (Student) usuarioLogado;

            this.menuAluno = new MenuAluno(scanner, alunoLogado, this.catalogoCursos, this.filaTickets);

            do{
                System.out.println("Menu do Aluno:");
                System.out.println("[1] Ver meus cursos matriculados");
                System.out.println("[2] Ver cursos disponíveis");
                System.out.println("[3] Abrir ticket de suporte");
                System.out.println("[4] Sair");

                escolha = Integer.parseInt(scanner.nextLine());

                menuAluno.executarAcaoALuno(escolha);

            }while(escolha != 4);



        } else if (usuarioLogado instanceof Users.Admin) {
            //convertendo o user para a classe adm
            Admin adminLogado = (Admin) usuarioLogado;

            this.menuAdmin = new MenuAdmin(scanner, adminLogado, this.filaTickets,mapUsuarios);

            do{
                System.out.println("Menu do Admin:");
                System.out.println("[1] Resolver Tickets de Suporte");
                System.out.println("[2] Abrir Ticket de Suporte");
                System.out.println("[3] Gerar Relatório de csv");
                System.out.println("[4] Sair");

                menuAdmin.executarAcaoAdmin(escolha);

            }while(escolha != 4);



        }
    }
}
