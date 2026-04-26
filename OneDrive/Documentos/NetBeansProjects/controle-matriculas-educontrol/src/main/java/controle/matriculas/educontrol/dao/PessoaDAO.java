/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle.matriculas.educontrol.dao;

import controle.matriculas.educontrol.model.Pessoa;
import controle.matriculas.educontrol.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author leo
 */
public class PessoaDAO {
    
    public void salvar(Pessoa pessoa) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try {
            s.save(pessoa);
            t.commit();
        } catch (Exception e) {
            t.rollback();
            throw e;
        } finally {
            s.close();
        }
    }
    
    public void editar(Pessoa pessoa) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try {
            s.merge(pessoa);
            t.commit();
        } catch (Exception e) {
            t.rollback();
            throw e;
        } finally {
            s.close();
        }
    }
    
    public void excluir(Integer idPessoa) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try {
            Pessoa p = s.get(Pessoa.class, idPessoa);
            if (p != null) {
                s.delete(p);
            }
            t.commit();
        } catch (Exception e) {
            t.rollback();
            throw e;
        } finally {
            s.close();
        }
    }
    
    public List<Pessoa> listarProfessores() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Pessoa> q = s.createQuery("from Pessoa where tipo = 'PROFESSOR' order by nomePessoa", Pessoa.class);
            return q.list();
        } finally {
            s.close();
        }
    }
    
        public List<Pessoa> listarAlunos() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Pessoa> q = s.createQuery("from Pessoa where tipo = 'ALUNO' order by nomePessoa", Pessoa.class);
            return q.list();
        } finally {
            s.close();
        }
    }

    public Pessoa buscarPorId(Integer id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            return s.get(Pessoa.class, id);
        } finally {
            s.close();
        }
    }
}
