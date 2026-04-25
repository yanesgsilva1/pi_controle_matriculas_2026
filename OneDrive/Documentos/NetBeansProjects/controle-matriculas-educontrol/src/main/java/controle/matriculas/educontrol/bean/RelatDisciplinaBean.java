/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle.matriculas.educontrol.bean;

import controle.matriculas.educontrol.model.Disciplina;
import controle.matriculas.educontrol.model.Pessoa;
import controle.matriculas.educontrol.dao.DisciplinaDAO;
import controle.matriculas.educontrol.dao.PessoaDAO;
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
@ManagedBean(name = "relatDisciplinaBean")
@SessionScoped

public class RelatDisciplinaBean implements Serializable {

    private Integer idProfessorSelecionado;
    private List<Pessoa> listaProfessores;
    private List<Disciplina> listaRelatorio;

    private int totalDisciplinas;
    private int totalMatriculas;
    private int totalVagasRestantes;

    private DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
    private PessoaDAO pessoaDAO = new PessoaDAO();

    @PostConstruct
    public void init() {
        listaProfessores = pessoaDAO.listarProfessores();
        listaRelatorio = new ArrayList<>();
    }

    public void consultar() {
        if (idProfessorSelecionado == null) {
            addMsg(FacesMessage.SEVERITY_WARN, "Selecione um professor.");
            return;
        }
        listaRelatorio = disciplinaDAO.listarDisciplinasPorProfessor(idProfessorSelecionado);
        calcularTotais();
    }

    public void consultarTodos() {
        listaRelatorio = disciplinaDAO.listarTodasDisciplinas();
        calcularTotais();
    }

    /* *****Atenção***** Cálculo provisório!!! Quando Wendel finalizar Matrículas, 
    basta substituir a lógica para buscar os números reais*/
    private void calcularTotais() {
        totalDisciplinas = listaRelatorio.size();
        totalMatriculas = 0;
        totalVagasRestantes = 0;

        for (Disciplina d : listaRelatorio) {
            totalMatriculas += d.getTotalMatriculas();
            totalVagasRestantes += d.getVagasRestantes();
        }
    }

    private void addMsg(FacesMessage.Severity severity, String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, msg, null));
    }

    public Integer getIdProfessorSelecionado() {
        return idProfessorSelecionado;
    }

    public void setIdProfessorSelecionado(Integer idProfessorSelecionado) {
        this.idProfessorSelecionado = idProfessorSelecionado;
    }

    public List<Pessoa> getListaProfessores() {
        return listaProfessores;
    }

    public void setListaProfessores(List<Pessoa> listaProfessores) {
        this.listaProfessores = listaProfessores;
    }

    public List<Disciplina> getListaRelatorio() {
        return listaRelatorio;
    }

    public void setListaRelatorio(List<Disciplina> listaRelatorio) {
        this.listaRelatorio = listaRelatorio;
    }

    public int getTotalDisciplinas() {
        return totalDisciplinas;
    }

    public int getTotalMatriculas() {
        return totalMatriculas;
    }

    public int getTotalVagasRestantes() {
        return totalVagasRestantes;
    }

}
