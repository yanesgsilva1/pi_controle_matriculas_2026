/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle.matriculas.educontrol.bean;

import controle.matriculas.educontrol.dao.DisciplinaDAO;
import controle.matriculas.educontrol.dao.MatriculaDAO;
import controle.matriculas.educontrol.dao.PessoaDAO;
import controle.matriculas.educontrol.model.Disciplina;
import controle.matriculas.educontrol.model.Matricula;
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
@ManagedBean(name = "matriculaBean")
@ViewScoped
public class MatriculaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Matricula matricula = new Matricula();
    private List<Matricula> listaMatriculas;
    private Integer idAlunoSelecionado;
    private Integer idDisciplinaSelecionada;
    private Matricula matriculaSelecionada;

    private MatriculaDAO matriculaDAO = new MatriculaDAO();
    private PessoaDAO pessoaDAO = new PessoaDAO();
    private DisciplinaDAO disciplinaDAO = new DisciplinaDAO();

    private List<Pessoa> listaAlunos;
    private List<Disciplina> listaDisciplinas;

    @PostConstruct
    public void init() {
        consultar();
        listaAlunos = pessoaDAO.listarAlunos();
        listaDisciplinas = disciplinaDAO.listarTodasDisciplinas();
    }

    
    public void aoClicarNaLinha(SelectEvent event) {
        this.matriculaSelecionada = (Matricula) event.getObject();
    }

    public void salvar() {
        try {
            Pessoa aluno = pessoaDAO.buscarPorId(idAlunoSelecionado);
            Disciplina disciplina = disciplinaDAO.buscarPorId(idDisciplinaSelecionada);

            if (matricula.getIdMatricula() == null) {
                // NOVA MATRÍCULA
                matricula.setAluno(aluno);
                matricula.setDisciplina(disciplina);

                matriculaDAO.salvar(matricula);
                addMsg(FacesMessage.SEVERITY_INFO, "Matrícula cadastrada com sucesso!");
            } else {
                // EDIÇÃO
                matricula.setAluno(aluno);
                matricula.setDisciplina(disciplina);

                matriculaDAO.editar(matricula);
                addMsg(FacesMessage.SEVERITY_INFO, "Matrícula atualizada com sucesso!");
            }

            consultar();
            limpar();

        } catch (Exception e) {
            addMsg(FacesMessage.SEVERITY_ERROR, "Erro ao salvar matrícula: " + e.getMessage());
        }
    }

    public void editar() {
        if (matriculaSelecionada == null) {
            addMsg(FacesMessage.SEVERITY_WARN, "Selecione uma matrícula na lista para editar.");
            return;
        }

        Matricula copia = new Matricula();
        copia.setIdMatricula(matriculaSelecionada.getIdMatricula());
        copia.setAluno(matriculaSelecionada.getAluno());
        copia.setDisciplina(matriculaSelecionada.getDisciplina());
        copia.setDataMatricula(matriculaSelecionada.getDataMatricula());
        copia.setValorPago(matriculaSelecionada.getValorPago());
        copia.setPeriodo(matriculaSelecionada.getPeriodo());

        this.matricula = copia;

        if (matriculaSelecionada.getAluno() != null) {
            this.idAlunoSelecionado = matriculaSelecionada.getAluno().getIdPessoa();
        }
        if (matriculaSelecionada.getDisciplina() != null) {
            this.idDisciplinaSelecionada = matriculaSelecionada.getDisciplina().getCodigo();
        }
    }

    public void consultar() {
        listaMatriculas = matriculaDAO.listarTodas();
    }

    public void excluir() {
        if (matriculaSelecionada == null) {
            addMsg(FacesMessage.SEVERITY_WARN, "Selecione uma matrícula para excluir.");
            return;
        }
        matriculaDAO.excluir(matriculaSelecionada.getIdMatricula());
        consultar();
        limpar();
        addMsg(FacesMessage.SEVERITY_INFO, "Matrícula excluída com sucesso!");
    }

    public void limpar() {
        matricula = new Matricula();
        idAlunoSelecionado = null;
        idDisciplinaSelecionada = null;
        matriculaSelecionada = null;
    }

    private void addMsg(FacesMessage.Severity severity, String mensagem) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(severity, mensagem, null));
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public List<Matricula> getListaMatriculas() {
        return listaMatriculas;
    }

    public void setListaMatriculas(List<Matricula> listaMatriculas) {
        this.listaMatriculas = listaMatriculas;
    }

    public Integer getIdAlunoSelecionado() {
        return idAlunoSelecionado;
    }

    public void setIdAlunoSelecionado(Integer idAlunoSelecionado) {
        this.idAlunoSelecionado = idAlunoSelecionado;
    }

    public Integer getIdDisciplinaSelecionada() {
        return idDisciplinaSelecionada;
    }

    public void setIdDisciplinaSelecionada(Integer idDisciplinaSelecionada) {
        this.idDisciplinaSelecionada = idDisciplinaSelecionada;
    }

    public List<Pessoa> getListaAlunos() {
        return listaAlunos;
    }

    public void setListaAlunos(List<Pessoa> listaAlunos) {
        this.listaAlunos = listaAlunos;
    }

    public List<Disciplina> getListaDisciplinas() {
        return listaDisciplinas;
    }

    public void setListaDisciplinas(List<Disciplina> listaDisciplinas) {
        this.listaDisciplinas = listaDisciplinas;
    }

    public Matricula getMatriculaSelecionada() {
        return matriculaSelecionada;
    }

    public void setMatriculaSelecionada(Matricula matriculaSelecionada) {
        this.matriculaSelecionada = matriculaSelecionada;
    }

}
