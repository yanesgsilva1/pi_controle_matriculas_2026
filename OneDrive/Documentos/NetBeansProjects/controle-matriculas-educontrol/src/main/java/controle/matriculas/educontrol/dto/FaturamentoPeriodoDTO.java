package controle.matriculas.educontrol.dto;

import java.math.BigDecimal;

/**
 * DTO para agrupar os dados do relatório de faturamento por período
 */
public class FaturamentoPeriodoDTO {

    private String nomeDisciplina;
    private String periodo;
    private Long totalMatriculas;
    private BigDecimal faturamentoTotal;

    public FaturamentoPeriodoDTO(String nomeDisciplina, String periodo,
                                  Long totalMatriculas, BigDecimal faturamentoTotal) {
        this.nomeDisciplina = nomeDisciplina;
        this.periodo = periodo;
        this.totalMatriculas = totalMatriculas;
        this.faturamentoTotal = faturamentoTotal;
    }

    public String getNomeDisciplina() { return nomeDisciplina; }
    public String getPeriodo() { return periodo; }
    public Long getTotalMatriculas() { return totalMatriculas; }
    public BigDecimal getFaturamentoTotal() { return faturamentoTotal; }
}
