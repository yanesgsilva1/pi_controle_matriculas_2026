/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle.matriculas.educontrol.dao;

import controle.matriculas.educontrol.model.Pessoa;
import controle.matriculas.educontrol.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author leo
 */
public class PessoaDAO {
    
    public List<Pessoa> listarProfessores() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Pessoa> q = s.createQuery("from Pessoa where tipo = 'PROFESSOR' order by nomePessoa", Pessoa.class);
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
