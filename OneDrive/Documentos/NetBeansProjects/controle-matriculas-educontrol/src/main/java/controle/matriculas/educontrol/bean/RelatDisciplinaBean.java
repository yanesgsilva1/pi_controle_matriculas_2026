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
        calcularTotaisSemMatricula();
    }

    public void consultarTodos() {
        listaRelatorio = disciplinaDAO.listarTodasDisciplinas();
        calcularTotaisSemMatricula();
    }

    /* *****Atenção***** Cálculo provisório!!! Quando Wendel finalizar Matrículas, 
    basta substituir a lógica para buscar os números reais*/
    private void calcularTotaisSemMatricula() {
        totalDisciplinas = listaRelatorio.size();
        totalMatriculas = 0;
        totalVagasRestantes = 0;

        for (Disciplina d : listaRelatorio) {
            d.setTotalMatriculas(0); // provisório
            d.setVagasRestantes(d.getLimiteAlunos()); // provisório
            totalMatriculas += d.getTotalMatriculas();
            totalVagasRestantes += d.getVagasRestantes();
        }
    }

    //Método já preparado para quando Wendel encaminhar o módulo de Matriculas
    
    /*private void calcularTotais() {
        totalDisciplinas = listaRelatorio.size();
        totalMatriculas = 0;
        totalVagasRestantes = 0;

        Session session = HibernateUtil.getSessionFactory().openSession();

        for (Disciplina d : listaRelatorio) {
            // Consulta real de matrículas por disciplina
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(m) FROM Matricula m WHERE m.disciplina.codigo = :codigo", Long.class);
            query.setParameter("codigo", d.getCodigo());
            Long qtdMatriculas = query.uniqueResult();

            // Atualiza os campos auxiliares
            d.setTotalMatriculas(qtdMatriculas.intValue());
            d.setVagasRestantes(d.getLimiteAlunos() - qtdMatriculas.intValue());

            // Atualiza os totais gerais
            totalMatriculas += d.getTotalMatriculas();
            totalVagasRestantes += d.getVagasRestantes();
        }

        session.close();
    }*/

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
