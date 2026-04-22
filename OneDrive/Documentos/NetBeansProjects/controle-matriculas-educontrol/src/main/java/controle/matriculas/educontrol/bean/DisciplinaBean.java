/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle.matriculas.educontrol.bean;

import controle.matriculas.educontrol.model.Disciplina;
import controle.matriculas.educontrol.model.Pessoa;
import controle.matriculas.educontrol.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author leo
 */
@ManagedBean(name = "disciplinaBean")
@ViewScoped
public class DisciplinaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Disciplina disciplina = new Disciplina();
    private List<Disciplina> listaDisciplinas;
    private Disciplina disciplinaSelecionada;
    private List<Pessoa> listaProfessores;
    private Integer idProfessorSelecionado;

    @PostConstruct
    public void init() {
        disciplina = new Disciplina();
        disciplinaSelecionada = null;
        consultar();

        // carrega os professores do banco
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<Pessoa> q = s.createQuery(
                    "from Pessoa where tipo = 'PROFESSOR' order by nomePessoa", Pessoa.class);
            listaProfessores = q.list();
        } catch (Exception e) {
            listaProfessores = new ArrayList<>();
            addMsg(FacesMessage.SEVERITY_ERROR, "Erro ao carregar professores.");
        }
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public List<Disciplina> getListaDisciplinas() {
        return listaDisciplinas;
    }

    public void setListaDisciplinas(List<Disciplina> listaDisciplinas) {
        this.listaDisciplinas = listaDisciplinas;
    }

    public Disciplina getDisciplinaSelecionada() {
        return disciplinaSelecionada;
    }

    public void setDisciplinaSelecionada(Disciplina disciplinaSelecionada) {
        this.disciplinaSelecionada = disciplinaSelecionada;
    }

    public List<Pessoa> getListaProfessores() {
        return listaProfessores;
    }

    public void setListaProfessores(List<Pessoa> listaProfessores) {
        this.listaProfessores = listaProfessores;
    }

    public Integer getIdProfessorSelecionado() {
        return idProfessorSelecionado;
    }

    public void setIdProfessorSelecionado(Integer idProfessorSelecionado) {
        this.idProfessorSelecionado = idProfessorSelecionado;
    }

    public void aoClicarNaLinha(SelectEvent event) {
        this.disciplinaSelecionada = (Disciplina) event.getObject();
    }

    public void salvar() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try {
            if (idProfessorSelecionado == null) {
                addMsg(FacesMessage.SEVERITY_ERROR, "Professor deve ser informado.");
                return;
            }
            // Buscar o professor pelo ID selecionado
            Pessoa p = s.get(Pessoa.class, idProfessorSelecionado);
            disciplina.setProfessor(p);

            if (disciplina.getCodigo() == null) {
                s.save(disciplina);
                addMsg(FacesMessage.SEVERITY_INFO, "Disciplina cadastrada com sucesso!");
            } else {
                s.merge(disciplina);
                addMsg(FacesMessage.SEVERITY_INFO, "Disciplina alterada com sucesso!");
            }
            t.commit();
            resetar();

        } catch (ConstraintViolationException e) {
            t.rollback();
            addMsg(FacesMessage.SEVERITY_ERROR, "Erro ao salvar disciplina.");
            e.printStackTrace();
        } finally {
            s.close();
        }
    }

    public void consultar() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<Disciplina> q = s.createQuery("select d from Disciplina d join fetch d.professor order by d.nomeDisciplina", Disciplina.class);
            listaDisciplinas = q.list();
        } catch (Exception e) {
            addMsg(FacesMessage.SEVERITY_ERROR, "Erro ao consultar disciplinas.");
            e.printStackTrace();
        }
    }

    public void editar() {
        if (disciplinaSelecionada == null) {
            addMsg(FacesMessage.SEVERITY_WARN, "Selecione uma disciplina na lista para editar.");
            return;
        }
        Disciplina copia = new Disciplina();
        copia.setCodigo(disciplinaSelecionada.getCodigo());
        copia.setNomeDisciplina(disciplinaSelecionada.getNomeDisciplina());
        copia.setCargaHoraria(disciplinaSelecionada.getCargaHoraria());
        copia.setProfessor(disciplinaSelecionada.getProfessor());
        copia.setLimiteAlunos(disciplinaSelecionada.getLimiteAlunos());
        this.disciplina = copia;

        // Atualiza o combo de professor
        if (disciplinaSelecionada.getProfessor() != null) {
            this.idProfessorSelecionado = disciplinaSelecionada.getProfessor().getIdPessoa();
        }
    }

    public void excluir() {
        if (disciplinaSelecionada == null) {
            addMsg(FacesMessage.SEVERITY_WARN, "Selecione uma disciplina na lista para excluir.");
            return;
        }
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try {
            Disciplina d = s.get(Disciplina.class, disciplinaSelecionada.getCodigo());
            if (d != null) {
                // regra: só excluir se não houver matrícula vinculada
                // aqui você deve verificar antes de excluir
                s.delete(d);
            }
            t.commit();
            resetar();
            addMsg(FacesMessage.SEVERITY_INFO, "Disciplina excluída com sucesso!");
        } catch (Exception e) {
            t.rollback();
            addMsg(FacesMessage.SEVERITY_ERROR, "Erro ao excluir disciplina.");
            e.printStackTrace();
        } finally {
            s.close();
        }
    }

    public void limpar() {
        resetar();
    }

    public int getTotalRegistros() {
        return listaDisciplinas != null ? listaDisciplinas.size() : 0;
    }

    private void resetar() {
        this.disciplina = new Disciplina();
        this.disciplinaSelecionada = null;
        this.idProfessorSelecionado = null;
        consultar();
    }

    private void addMsg(FacesMessage.Severity severity, String mensagem) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(severity, mensagem, null));
    }

}
