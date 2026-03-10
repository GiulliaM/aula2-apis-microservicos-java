package Users;

public class Admin extends User {

    public Admin(String name, String email) {
        super(name, email);
    }

    // As permissões serão validadas na classe de Menu usando "instanceof Admin"
}