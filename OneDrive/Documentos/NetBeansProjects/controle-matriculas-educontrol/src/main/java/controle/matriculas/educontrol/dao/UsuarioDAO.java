package controle.matriculas.educontrol.dao;

import controle.matriculas.educontrol.model.Usuario;
import controle.matriculas.educontrol.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UsuarioDAO {

    public void salvar(Usuario usuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        try {
            t = session.beginTransaction();
            session.save(usuario);
            t.commit();
        } catch (Exception e) {
            if (t != null) t.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
    
    public Usuario buscarPorLogin(String login) {
    try (Session s = HibernateUtil.getSessionFactory().openSession()) {
        Query<Usuario> q = s.createQuery(
            "from Usuario where login = :login", Usuario.class);
        q.setParameter("login", login);
        return q.uniqueResult();
    }
}
}
