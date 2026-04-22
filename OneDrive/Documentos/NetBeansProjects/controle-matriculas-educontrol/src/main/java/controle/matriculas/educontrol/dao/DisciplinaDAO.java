/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle.matriculas.educontrol.dao;

import controle.matriculas.educontrol.model.Disciplina;
import controle.matriculas.educontrol.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author leo
 */
public class DisciplinaDAO {

    public List<Disciplina> listarTodasDisciplinas() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Disciplina> query = session.createQuery("FROM Disciplina d", Disciplina.class);
        List<Disciplina> resultado = query.getResultList();
        session.close();
        return resultado;
    }

    public List<Disciplina> listarDisciplinasPorProfessor(Integer idProfessor) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Disciplina> query = session.createQuery(
                "FROM Disciplina d WHERE d.professor.idPessoa = :id", Disciplina.class);
        query.setParameter("id", idProfessor);
        List<Disciplina> resultado = query.getResultList();
        session.close();
        return resultado;
    }

    public void salvar(Disciplina disciplina) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try {
            s.save(disciplina);
            t.commit();
        } catch (Exception e) {
            t.rollback();
            throw e;
        } finally {
            s.close();
        }
    }

    public void atualizar(Disciplina disciplina) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try {
            s.merge(disciplina);
            t.commit();
        } catch (Exception e) {
            t.rollback();
            throw e;
        } finally {
            s.close();
        }
    }

    public void excluir(Integer codigo) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try {
            Disciplina d = s.get(Disciplina.class, codigo);
            if (d != null) {
                s.delete(d);
            }
            t.commit();
        } catch (Exception e) {
            t.rollback();
            throw e;
        } finally {
            s.close();
        }
    }

    public List<Disciplina> listarTodas() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Disciplina> q = s.createQuery("from Disciplina order by nomeDisciplina", Disciplina.class);
            return q.list();
        } finally {
            s.close();
        }
    }

}
