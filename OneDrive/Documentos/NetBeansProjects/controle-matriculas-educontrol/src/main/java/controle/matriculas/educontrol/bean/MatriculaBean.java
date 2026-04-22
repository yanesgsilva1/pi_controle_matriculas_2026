package controle.matriculas.educontrol.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.hibernate.Session;
import org.hibernate.Transaction;

import controle.matriculas.educontrol.model.*;
import controle.matriculas.educontrol.util.HibernateUtil;

@Named
@ViewScoped
public class MatriculaBean implements Serializable {

    private Matricula matricula;
    private List<Matricula> lista;

    private List<Disciplina> disciplinas;
    private List<Pessoa> alunos;

    @PostConstruct
    public void init() {
        matricula = new Matricula();
        lista = new ArrayList<>();
        listar();
        carregarCombos();
    }

    public void listar() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {

            lista = s.createQuery(
                "select m from Matricula m " +
                "join fetch m.disciplina " +
                "join fetch m.aluno " +
                "order by m.idmat",
                Matricula.class
            ).list();

        } catch (Exception e) {
            addMensagem("Erro ao listar");
        }
    }

    public void carregarCombos() {
    try (Session s = HibernateUtil.getSessionFactory().openSession()) {

        disciplinas = s.createQuery("from Disciplina", Disciplina.class).list();
        alunos = s.createQuery("from Pessoa where tipo = 'ALUNO'", Pessoa.class).list();

        System.out.println(">>> Disciplinas carregadas: " + disciplinas.size());
        System.out.println(">>> Alunos carregados: " + alunos.size());

    } catch (Exception e) {
        e.printStackTrace();
        addMensagem("Erro ao carregar combos: " + e.getMessage());
    }
}

    public void salvar() {

        Transaction tx = null;

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {

            tx = s.beginTransaction();

            if (matricula.getDisciplina() == null || matricula.getAluno() == null) {
                addMensagem("Aluno e Disciplina são obrigatórios!");
                return;
            }

            Long existe = s.createQuery(
                "select count(m) from Matricula m " +
                "where m.disciplina.codigo = :disc and m.aluno.idPessoa = :aluno",
                Long.class
            )
            .setParameter("disc", matricula.getDisciplina().getCodigo())
            .setParameter("aluno", matricula.getAluno().getIdPessoa())
            .uniqueResult();

            if (existe > 0 && matricula.getIdmat() == null) {
                addMensagem("Aluno já matriculado nessa disciplina!");
                tx.rollback();
                return;
            }

            Long total = s.createQuery(
                "select count(m) from Matricula m where m.disciplina.codigo = :id",
                Long.class
            )
            .setParameter("id", matricula.getDisciplina().getCodigo())
            .uniqueResult();

            if (total >= matricula.getDisciplina().getLimiteAlunos()
                && matricula.getIdmat() == null) {
                addMensagem("Limite da turma excedido!");
                tx.rollback();
                return;
            }

            if (matricula.getDataMatricula() == null) {
                matricula.setDataMatricula(new java.util.Date());
            }

            s.saveOrUpdate(matricula);
            tx.commit();

            addMensagem("Salvo com sucesso!");

            matricula = new Matricula();
            listar();

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            addMensagem("Erro ao salvar");
        }
    }

    public void editar(Matricula m) {
        this.matricula = m;
    }

    public void excluir(Matricula m) {

        Transaction tx = null;

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {

            tx = s.beginTransaction();

            Matricula mat = s.get(Matricula.class, m.getIdmat());
            s.delete(mat);

            tx.commit();

            listar();
            addMensagem("Excluído com sucesso!");

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            addMensagem("Erro ao excluir");
        }
    }

    public void limpar() {
        matricula = new Matricula();
    }

    private void addMensagem(String msg) {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(msg));
    }

    // getters
    public Matricula getMatricula() { return matricula; }
    public List<Matricula> getLista() { return lista; }
    public List<Disciplina> getDisciplinas() { return disciplinas; }
    public List<Pessoa> getAlunos() { return alunos; }

    // converters helpers
    public Disciplina getDisciplinaPorCodigo(String codigo) {
        if (codigo == null || codigo.isEmpty()) return null;
        for (Disciplina d : disciplinas) {
            if (String.valueOf(d.getCodigo()).equals(codigo)) return d;
        }
        return null;
    }

    public Pessoa getAlunoPorId(String id) {
        if (id == null || id.isEmpty()) return null;
        for (Pessoa a : alunos) {
            if (String.valueOf(a.getIdPessoa()).equals(id)) return a;
        }
        return null;
    }

}