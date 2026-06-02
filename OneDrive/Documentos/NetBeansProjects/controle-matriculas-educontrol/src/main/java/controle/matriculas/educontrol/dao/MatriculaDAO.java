package controle.matriculas.educontrol.dao;

import controle.matriculas.educontrol.model.Matricula;
import controle.matriculas.educontrol.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import controle.matriculas.educontrol.dto.FaturamentoPeriodoDTO;
import java.util.ArrayList;

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

    // Listar matrículas de um aluno específico
    public List<Matricula> listarMatriculasPorAluno(Integer idAluno) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Matricula> q = s.createQuery(
                    "from Matricula m where m.aluno.idPessoa = :idAluno order by m.dataMatricula",
                    Matricula.class
            );
            q.setParameter("idAluno", idAluno);
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
    
    public List<FaturamentoPeriodoDTO> consultarFaturamentoPorPeriodo(
        String dataInicio, String dataFim, Integer idDisciplina) {
 
    Session s = HibernateUtil.getSessionFactory().openSession();
    try {
        StringBuilder hql = new StringBuilder(
            "select m.disciplina.nomeDisciplina, m.periodo, " +
            "count(m), sum(m.valorPago) " +
            "from Matricula m " +
            "where m.dataMatricula >= :dataInicio " +
            "and m.dataMatricula <= :dataFim "
        );
 
        if (idDisciplina != null) {
            hql.append("and m.disciplina.codigo = :idDisciplina ");
        }
 
        hql.append("group by m.disciplina.nomeDisciplina, m.periodo ");
        hql.append("order by m.disciplina.nomeDisciplina, m.periodo");
 
        Query<?> q = s.createQuery(hql.toString());
        q.setParameter("dataInicio",
            java.sql.Date.valueOf(dataInicio));
        q.setParameter("dataFim",
            java.sql.Date.valueOf(dataFim));
 
        if (idDisciplina != null) {
            q.setParameter("idDisciplina", idDisciplina);
        }
 
        List<Object[]> rows = (List<Object[]>) q.list();
        List<FaturamentoPeriodoDTO> resultado = new ArrayList<>();
 
        for (Object[] row : rows) {
            resultado.add(new FaturamentoPeriodoDTO(
                (String) row[0],
                (String) row[1],
                (Long) row[2],
                (java.math.BigDecimal) row[3]
            ));
        }
        return resultado;
    } finally {
        s.close();
    }
}
}
