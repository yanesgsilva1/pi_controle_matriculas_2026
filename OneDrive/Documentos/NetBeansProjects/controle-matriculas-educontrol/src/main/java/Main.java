import org.mindrot.jbcrypt.BCrypt;

public class Main {

    public static void main(String[] args) {

        String senha = "teste123";

        // Gerar hash
        String hash = BCrypt.hashpw(senha, BCrypt.gensalt());

        System.out.println("Hash gerado:");
        System.out.println(hash);
    }
}
