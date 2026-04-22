package controle.matriculas.educontrol.util;

import org.mindrot.jbcrypt.BCrypt;

public class SenhaUtil {

    // Transforma senha comum em Hash (Para o Cadastro)
    public static String criptografar(String senhaPlana) {
        if (senhaPlana == null || senhaPlana.isEmpty()) return null;
        return BCrypt.hashpw(senhaPlana, BCrypt.gensalt());
    }

    // Compara senha digitada com a do Banco (Para o Login)
    public static boolean verificarSenha(String senhaPlana, String senhaCriptografada) {
        if (senhaPlana == null || senhaCriptografada == null) return false;
        try {
            return BCrypt.checkpw(senhaPlana, senhaCriptografada);
        } catch (Exception e) {
            return false;
        }
    }
}
