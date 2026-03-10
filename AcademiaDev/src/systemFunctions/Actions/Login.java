package systemFunctions.Actions;

import Users.User;
import systemFunctions.MenuAction;
import java.util.Map;

public class Login implements MenuAction {

    private String email;
    private Map<String, User> usuarios;
    private User usuarioLogado; // Variável para guardar o resultado

    // 1. O Construtor recebe os dados que a ação precisa para funcionar
    public Login(String email, Map<String, User> usuarios) {
        this.email = email;
        this.usuarios = usuarios;
        this.usuarioLogado = null; // Inicia nulo
    }

    // 2. O execute() da interface agora é usado de verdade!
    @Override
    public void execute() {
        this.usuarioLogado = usuarios.get(email);

        if (this.usuarioLogado == null) {
            System.out.println("Email não encontrado, digite novamente.");
        } else {
            System.out.println("Você está logado como: " + usuarioLogado.getName());
        }
    }

    // 3. Método extra para devolver quem logou para o Main
    public User getUsuarioLogado() {
        return this.usuarioLogado;
    }
}