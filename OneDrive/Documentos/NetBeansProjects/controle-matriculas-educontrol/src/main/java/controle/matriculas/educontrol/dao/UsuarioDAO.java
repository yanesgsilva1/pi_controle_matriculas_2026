package controle.matriculas.educontrol.dao;

import controle.matriculas.educontrol.model.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class UsuarioDAO {

    public void salvar(Usuario usuario) {
        // Abre uma sessão com o Hibernate usando o hibernate.cfg.xml que configuramos
        Session session = new Configuration().configure().buildSessionFactory().openSession();
        Transaction t = null;

        try {
            t = session.beginTransaction();
            session.save(usuario); // Aqui o Hibernate grava o usuário e a senha criptografada
            t.commit();
        } catch (Exception e) {
            if (t != null) t.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}