package systemFunctions.Actions;

import Support.FilaSupportTicket;
import Support.SupportTicket;
import Users.Admin;
import Users.User;
import systemFunctions.GenericCsvExporter;

import java.util.*;

public class MenuAdmin {
    //acaner do inupr
    private Scanner scanner;

    private Admin adminLogado;
    private FilaSupportTicket filaSupportTicket;
    private Map<Integer, Runnable> opcoesMenuAdmin = new HashMap<>();
    private Map<String, User> mapUsuarios;




    public MenuAdmin(Scanner scanner, Admin adminLogado, FilaSupportTicket filaSupportTicket, Map<String, User> mapUsuarios) {
        this.scanner = scanner;
        this.adminLogado = adminLogado;
        this.filaSupportTicket = filaSupportTicket;
        this.mapUsuarios = mapUsuarios;

        //funcoes do menu admin
        this.opcoesMenuAdmin.put(1, this::resolverTicketsSuporte);
        this.opcoesMenuAdmin.put(2, this::abrirTicketSuporte);
        this.opcoesMenuAdmin.put(3, this::exportarRelatorioUsuarios);

    }

    public void resolverTicketsSuporte() {
        //listar os tickets na fila e resolver/remover o primeir da fila
        System.out.println("Tickets Pendentes:");
        if(this.filaSupportTicket.estaVazia()){
            System.out.println("Não há tickets para resolver.");
        }else {
            this.filaSupportTicket.listarTickets();
            System.out.println("Deseja resolver o próximo ticket? (s/n)");
            String resposta = scanner.nextLine().toLowerCase();

            if (resposta.equals("s")) {
                this.filaSupportTicket.atenderERemoverTicket();
                System.out.println("Ticket resolvido e removido da fila.");
            } else {
                System.out.println("Operação cancelada. O ticket permanece na fila.");
            }
        }
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
        ticket = new SupportTicket(this.adminLogado, titulo, mensagem);

        //adicinando o ticket criado na fila
        this.filaSupportTicket.adicionarTicket(ticket);


    }

    public void exportarRelatorioUsuarios() {
        System.out.println("Iniciando exportação de usuários...");

        // INGREDIENTE 1: Transformar o seu Map<String, User> em uma List<User>
        // (A classe da sua amiga pede uma List, então fazemos essa conversão rápida)
        List<User> listaDeUsuarios = new ArrayList<>(this.mapUsuarios.values());

        // INGREDIENTE 2: Criar a lista de colunas que queremos exportar
        List<String> colunasDesejadas = new ArrayList<>();

        // ATENÇÃO: Troque "name" e "email" pelo nome exato das variáveis na sua classe User!
        colunasDesejadas.add("name");
        colunasDesejadas.add("email");

        // A MÁGICA: Chamamos o "liquidificador" da sua amiga passando os ingredientes!
        String textoDoCsv = GenericCsvExporter.exportarParaCsv(listaDeUsuarios, colunasDesejadas);

        // IMPRIMIR: Mostrar o resultado na tela conforme o projeto pede
        System.out.println("\n=== RESULTADO DA EXPORTAÇÃO CSV ===");
        System.out.println(textoDoCsv);
        System.out.println("===================================\n");
    }

    public void executarAcaoAdmin(Integer escolha) {
        Runnable acaoEscolhida = this.opcoesMenuAdmin.get(escolha);

        if(acaoEscolhida != null){
            acaoEscolhida.run();
        }else{
            System.out.println("escolha invalida, por favor escolha uma opção válida.");
        }
    }

}
