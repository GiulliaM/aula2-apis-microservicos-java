package systemFunctions.Actions;

import Courses.CourseCatalog;
import Support.FilaSupportTicket;
import Support.SupportTicket;
import Users.Student;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MenuAluno {
    Scanner scanner;

    //numero associado a cada metodo de acoes de cada tipo de usuario
    Student alunoLogado;
    private Map<Integer, Runnable> opcoesMenuAluno = new HashMap<>();
    CourseCatalog catalogoCursos;
    FilaSupportTicket filaTickets;

    public MenuAluno(Scanner scaner, Student alunoLogado, CourseCatalog catalogoCursos, FilaSupportTicket filaTickets) {
        this.scanner = scaner;
        this.alunoLogado = alunoLogado;
        this.catalogoCursos = catalogoCursos;
        this.filaTickets = filaTickets;

        //acoes do menu do aluno
        opcoesMenuAluno.put(1, this::cursosMatriculados);//meus cursos
        opcoesMenuAluno.put(2, this::cursosDisponiveis);
        opcoesMenuAluno.put(3, this::abrirTicketSuporte);

    }


    public void cursosMatriculados() {
        System.out.println("Cursos que estou matriculado");
        System.out.println(this.alunoLogado.getListaDeMatriculasAtuais());
    }

    public void cursosDisponiveis() {
        //mostrat cursos disponiveis para se inscrever
        System.out.println("Cursos disponíveis:");
        this.catalogoCursos.listarCursos();

        

    }

    public void abrirTicketSuporte() {
        String titulo, mensagem;
        SupportTicket ticket;

        do{
            System.out.println("digite o titulo do ticket:");
            titulo = this.scanner.nextLine();
            if(titulo.isEmpty()){
                System.out.println("titulo não pode ser vazio");
            }
        }while(titulo.isEmpty());

        do{
            System.out.println("digite o mensagem do ticket:");
            mensagem = this.scanner.nextLine();
            if(mensagem.isEmpty()){
                System.out.println("mensagem não pode ser vazia");
            }
        }while(mensagem.isEmpty());

        //criar um novo ticket de suporte com os dados do aluno logado, titulo e mensagem
        ticket = new SupportTicket(this.alunoLogado, titulo, mensagem);

        //adicinando o ticket criado na fila
        this.filaTickets.adicionarTicket(ticket);


    }

    public void executarAcaoALuno(Integer escolha) {
        Runnable acaoEscolhida = this.opcoesMenuAluno.get(escolha);

        if(acaoEscolhida != null){
            acaoEscolhida.run();
        }else{
            System.out.println("escolha invalida, por favor escolha uma opção válida.");
        }
    }
}
