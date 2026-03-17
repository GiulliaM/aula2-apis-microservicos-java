package systemFunctions.Actions;

import Courses.BasicPlan;
import Courses.PremiumPlan;
import Courses.SubscriptionPlan;
import Users.Admin;
import Users.Student;
import Users.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class MenuAutenticacao {
    //leitor dos inputs do usuario
    private Scanner scanner;
    private int escolha = -1;


    //atributos
    private Map<String, User> mapUsuarios = new HashMap<>(); //map para armazenar os usuarios cadastrados, a chave é o email do usuario
    private User usuarioLogado; //variavel para armezenar os dados do usuario logado

    //dados para guardar os dados do user durante o cadastro
    private String nomeUser;
    private String emailUser;
    private boolean isStudent = false;

    public MenuAutenticacao( Scanner scanner, Map<String, User> mapUsuarios) {
        this.mapUsuarios = mapUsuarios;
        this.scanner = scanner;
    }


    public Optional<User> cadastro() {

        do{
            System.out.println("gostaria de criar uma conta de: \n[1]. Aluno \n[2]. Professor");
            try{
                this.escolha = Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Entrada inválida. Por favor, insira um número válido.");
                this.escolha = -1; //reinicia a escolha para continuar o loop
            }

            if(escolha ==1){
                isStudent = true;
            }else{
                isStudent = false;
            }

            if(escolha != 1 && escolha != 2){
                System.out.println("Escolha inválida.");
            }
        }while(escolha != 1 && escolha != 2);

        System.out.println("Digite o nome do usuário:");
        do{
            this.nomeUser = scanner.nextLine();
            if(this.nomeUser.isEmpty()){
                System.out.println("Nome inválido. Por favor, insira um nome válido.");
            }
        }while(this.nomeUser.isEmpty());

        //preenchimento de email com verificacao de formato
        do{
            System.out.println("Digite o email do usuário:");
            this.emailUser = scanner.nextLine().toLowerCase();

            //se o email NAO tiver algo @ ou .com, é invalido (entra no if)
            if(! this.emailUser.contains("@") || ! this.emailUser.contains(".com")){
                System.out.println("Email inválido. Por favor, insira um email válido.");
            }
        }while(! this.emailUser.contains("@") || ! this.emailUser.contains(".com"));

        //de for estudante tem o preenchimento do plano, se for adm pula
        if(isStudent){
            //preenchimento de plano com as palavras reserevadas
            do{
                System.out.println("Digite o plano do Estudante: \n[1]. Basic \n[2]. Premium");
                this.escolha = Integer.parseInt(scanner.nextLine());
                if(escolha != 1 && escolha != 2){
                    System.out.println("Plano inválido. Por favor, insira um plano válido.");
                }
            }while(escolha != 1 && escolha != 2);

            usuarioLogado = new Student(this.nomeUser, this.emailUser, this.escolha ==1  ? new BasicPlan() : new PremiumPlan());

        }else{
            //adicionar o adm se n for estudante na lista map de ususarios
            usuarioLogado = new Admin(this.nomeUser, this.emailUser);
        }

        //adiciona o usuário criado ao mapa de usuários (permitir o login)
        this.mapUsuarios.put(this.emailUser, this.usuarioLogado);

        return Optional.ofNullable(this.usuarioLogado);

    }

    public Optional<User> login(){
        System.out.println("Digite o email para login:");
        this.emailUser = scanner.nextLine().toLowerCase();
        
        if(this.mapUsuarios.containsKey(this.emailUser)){
            this.usuarioLogado = this.mapUsuarios.get(this.emailUser);
            System.out.println("Login bem-sucedido! Bem-vindo, " + usuarioLogado.getName() + "!");
            return Optional.ofNullable(this.usuarioLogado);
        } else {
            System.out.println("Email não encontrado. Por favor, tente novamente ou cadastre-se.");
            return Optional.empty();
        }
    }


}

