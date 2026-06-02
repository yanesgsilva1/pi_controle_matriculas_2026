package controle.matriculas.educontrol.bean;

import controle.matriculas.educontrol.dao.DisciplinaDAO;
import controle.matriculas.educontrol.dao.MatriculaDAO;
import controle.matriculas.educontrol.dto.FaturamentoPeriodoDTO;
import controle.matriculas.educontrol.model.Disciplina;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "relatFaturamentoBean")
@ViewScoped
public class RelatFaturamentoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dataInicio;
    private String dataFim;
    private Integer idDisciplinaSelecionada;

    private List<Disciplina> listaDisciplinas;
    private List<FaturamentoPeriodoDTO> resultados;
    private BigDecimal totalGeral = BigDecimal.ZERO;
    private Long totalMatriculasGeral = 0L;

    private MatriculaDAO matriculaDAO = new MatriculaDAO();
    private DisciplinaDAO disciplinaDAO = new DisciplinaDAO();

    @PostConstruct
    public void init() {
        listaDisciplinas = disciplinaDAO.listarTodasDisciplinas();
        resultados = new ArrayList<>();
    }

    public void consultar() {
        if (dataInicio == null || dataInicio.isEmpty() ||
            dataFim == null || dataFim.isEmpty()) {
            addMsg(FacesMessage.SEVERITY_WARN, "Informe a data início e a data fim.");
            return;
        }

        try {
            resultados = matriculaDAO.consultarFaturamentoPorPeriodo(
                dataInicio, dataFim, idDisciplinaSelecionada);

            // Calcula totais gerais
            totalGeral = BigDecimal.ZERO;
            totalMatriculasGeral = 0L;
            for (FaturamentoPeriodoDTO dto : resultados) {
                totalGeral = totalGeral.add(dto.getFaturamentoTotal());
                totalMatriculasGeral += dto.getTotalMatriculas();
            }

            if (resultados.isEmpty()) {
                addMsg(FacesMessage.SEVERITY_INFO, "Nenhum resultado encontrado para o período informado.");
            }
        } catch (Exception e) {
            addMsg(FacesMessage.SEVERITY_ERROR, "Erro ao consultar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void limpar() {
        dataInicio = null;
        dataFim = null;
        idDisciplinaSelecionada = null;
        resultados = new ArrayList<>();
        totalGeral = BigDecimal.ZERO;
        totalMatriculasGeral = 0L;
    }

    // Getters e Setters
    public String getDataInicio() { return dataInicio; }
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }

    public String getDataFim() { return dataFim; }
    public void setDataFim(String dataFim) { this.dataFim = dataFim; }

    public Integer getIdDisciplinaSelecionada() { return idDisciplinaSelecionada; }
    public void setIdDisciplinaSelecionada(Integer idDisciplinaSelecionada) {
        this.idDisciplinaSelecionada = idDisciplinaSelecionada;
    }

    public List<Disciplina> getListaDisciplinas() { return listaDisciplinas; }
    public List<FaturamentoPeriodoDTO> getResultados() { return resultados; }
    public BigDecimal getTotalGeral() { return totalGeral; }
    public Long getTotalMatriculasGeral() { return totalMatriculasGeral; }

    private void addMsg(FacesMessage.Severity severity, String mensagem) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(severity, mensagem, null));
    }
}
