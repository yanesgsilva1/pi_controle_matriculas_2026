/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle.matriculas.educontrol.bean;

import controle.matriculas.educontrol.dao.DisciplinaDAO;
import controle.matriculas.educontrol.dao.PessoaDAO;
import controle.matriculas.educontrol.model.Disciplina;
import controle.matriculas.educontrol.model.Pessoa;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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
    
    private DisciplinaDAO disciplinaDAO = new DisciplinaDAO(); 
     private PessoaDAO pessoaDAO = new PessoaDAO();

    @PostConstruct
    public void init() {
        disciplina = new Disciplina();
        disciplinaSelecionada = null;
        consultar();
        listaProfessores = pessoaDAO.listarProfessores();
    }
    
    public void aoClicarNaLinha(SelectEvent event) {
        this.disciplinaSelecionada = (Disciplina) event.getObject();
    }
    
    public void salvar() {
        if (idProfessorSelecionado == null) {
            addMsg(FacesMessage.SEVERITY_ERROR, "Professor deve ser informado.");
            return;
        }
        Pessoa p = pessoaDAO.buscarPorId(idProfessorSelecionado);
        disciplina.setProfessor(p);

        if (disciplina.getCodigo() == null) {
            disciplinaDAO.salvar(disciplina);
            addMsg(FacesMessage.SEVERITY_INFO, "Disciplina cadastrada com sucesso!");
        } else {
            disciplinaDAO.atualizar(disciplina);
            addMsg(FacesMessage.SEVERITY_INFO, "Disciplina alterada com sucesso!");
        }
        resetar();
    }

    public void consultar() {
        listaDisciplinas = disciplinaDAO.listarTodasDisciplinas();
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

        if (disciplinaSelecionada.getProfessor() != null) {
            this.idProfessorSelecionado = disciplinaSelecionada.getProfessor().getIdPessoa();
        }
    }

    public void excluir() {
        if (disciplinaSelecionada == null) {
            addMsg(FacesMessage.SEVERITY_WARN, "Selecione uma disciplina na lista para excluir.");
            return;
        }
        disciplinaDAO.excluir(disciplinaSelecionada.getCodigo());
        resetar();
        addMsg(FacesMessage.SEVERITY_INFO, "Disciplina excluída com sucesso!");
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
    
}
