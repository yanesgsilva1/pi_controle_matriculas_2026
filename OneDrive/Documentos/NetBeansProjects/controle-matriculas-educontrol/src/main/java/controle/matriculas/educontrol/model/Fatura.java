package controle.matriculas.educontrol.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity 
public class Fatura implements Serializable {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    private String alunoNome; 
    private BigDecimal valor;
    private LocalDate dataVencimento;
    private String status; 

    public Fatura() {
    }

    public Fatura(Long id, String alunoNome, BigDecimal valor, LocalDate dataVencimento, String status) {
        this.id = id;
        this.alunoNome = alunoNome;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAlunoNome() { return alunoNome; }
    public void setAlunoNome(String alunoNome) { this.alunoNome = alunoNome; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}