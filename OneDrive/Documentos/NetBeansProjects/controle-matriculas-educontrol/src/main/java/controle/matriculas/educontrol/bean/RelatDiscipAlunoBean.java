/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle.matriculas.educontrol.bean;

import controle.matriculas.educontrol.dao.MatriculaDAO;
import controle.matriculas.educontrol.dao.PessoaDAO;
import controle.matriculas.educontrol.model.Matricula;
import controle.matriculas.educontrol.model.Pessoa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author leo
 */
@ManagedBean(name = "relatDiscipAlunoBean")
@SessionScoped

public class RelatDiscipAlunoBean implements Serializable {

    private Integer idAlunoSelecionado;
    private List<Pessoa> listaAlunos;
    private List<Matricula> listaRelatorio;

    private int totalMatriculas;

    private PessoaDAO pessoaDAO = new PessoaDAO();
    private MatriculaDAO matriculaDAO = new MatriculaDAO();

    @PostConstruct
    public void init() {
        listaAlunos = pessoaDAO.listarAlunos();
        listaRelatorio = new ArrayList<>();
    }

    public void consultar() {
        if (idAlunoSelecionado == null) {
            addMsg(FacesMessage.SEVERITY_WARN, "Selecione um aluno.");
            return;
        }
        listaRelatorio = matriculaDAO.listarMatriculasPorAluno(idAlunoSelecionado);
        calcularTotais();
    }

    public void consultarTodos() {
        listaRelatorio = matriculaDAO.listarTodas();
        calcularTotais();
    }

    private void calcularTotais() {
        totalMatriculas = listaRelatorio.size();
    }

    private void addMsg(FacesMessage.Severity severity, String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, msg, null));
    }

    public Integer getIdAlunoSelecionado() {
        return idAlunoSelecionado;
    }

    public void setIdAlunoSelecionado(Integer idAlunoSelecionado) {
        this.idAlunoSelecionado = idAlunoSelecionado;
    }

    public List<Pessoa> getListaAlunos() {
        return listaAlunos;
    }

    public void setListaAlunos(List<Pessoa> listaAlunos) {
        this.listaAlunos = listaAlunos;
    }

    public List<Matricula> getListaRelatorio() {
        return listaRelatorio;
    }

    public void setListaRelatorio(List<Matricula> listaRelatorio) {
        this.listaRelatorio = listaRelatorio;
    }

    public int getTotalMatriculas() {
        return totalMatriculas;
    }

    public void setTotalMatriculas(int totalMatriculas) {
        this.totalMatriculas = totalMatriculas;
    }
    
    
}
