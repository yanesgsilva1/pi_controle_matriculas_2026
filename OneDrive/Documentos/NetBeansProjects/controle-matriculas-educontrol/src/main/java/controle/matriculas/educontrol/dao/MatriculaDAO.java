/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle.matriculas.educontrol.dao;

import controle.matriculas.educontrol.model.Matricula;
import controle.matriculas.educontrol.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class MatriculaDAO {

    public void salvar(Matricula matricula) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try {
            s.save(matricula);
            t.commit();
        } catch (Exception e) {
            t.rollback();
            throw e;
        } finally {
            s.close();
        }
    }

    public void editar(Matricula matricula) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try {
            s.merge(matricula);
            t.commit();
        } catch (Exception e) {
            t.rollback();
            throw e;
        } finally {
            s.close();
        }
    }

    public void excluir(Integer idMatricula) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try {
            Matricula m = s.get(Matricula.class, idMatricula);
            if (m != null) {
                s.delete(m);
            }
            t.commit();
        } catch (Exception e) {
            t.rollback();
            throw e;
        } finally {
            s.close();
        }
    }

    public List<Matricula> listarTodas() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Matricula> q = s.createQuery("from Matricula order by dataMatricula", Matricula.class);
            return q.list();
        } finally {
            s.close();
        }
    }

    //Conta quantos alunos por disciplina
    public int contarPorDisciplina(Integer idDisciplina) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            Long count = (Long) s.createQuery(
                    "select count(m) from Matricula m where m.disciplina.codigo = :id")
                    .setParameter("id", idDisciplina)
                    .uniqueResult();
            return count.intValue();
        } finally {
            s.close();
        }
    }
}
