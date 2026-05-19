package controle.matriculas.educontrol.dao;

import controle.matriculas.educontrol.model.Fatura;
import controle.matriculas.educontrol.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.io.Serializable;
import java.util.List;

public class FaturaDAO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Busca todas as faturas cadastradas no MySQL para o relatório.
     */
    @SuppressWarnings("unchecked")
    public List<Fatura> listarTodas() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // HQL utilizando o nome exato da classe "Fatura"
            return session.createQuery("from Fatura").list();
        } catch (Exception e) {
            System.err.println("Erro ao listar faturas no FaturaDAO:");
            e.printStackTrace();
            return null;
        } finally {
            session.close(); // Fecha a sessão obrigatoriamente
        }
    }

    /**
     * Método opcional extra: Caso precise salvar novas faturas para testar
     */
    public void salvar(Fatura fatura) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(fatura);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}