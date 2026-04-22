package controle.matriculas.educontrol.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "matriculas")
public class Matricula implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMatricula")
    private Integer idmat;

    @ManyToOne
    @JoinColumn(name = "codigoDisciplina", nullable = false)
    private Disciplina disciplina;

    @ManyToOne
    @JoinColumn(name = "idAluno", nullable = false)
    private Pessoa aluno;

    @Temporal(TemporalType.DATE)
    private Date dataMatricula;

    private BigDecimal valorPago;

    private String periodo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Matricula)) return false;
        Matricula m = (Matricula) o;
        return idmat != null && idmat.equals(m.idmat);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    public Integer getIdmat() { return idmat; }
    public void setIdmat(Integer idmat) { this.idmat = idmat; }

    public Disciplina getDisciplina() { return disciplina; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }

    public Pessoa getAluno() { return aluno; }
    public void setAluno(Pessoa aluno) { this.aluno = aluno; }

    public Date getDataMatricula() { return dataMatricula; }
    public void setDataMatricula(Date dataMatricula) { this.dataMatricula = dataMatricula; }

    public BigDecimal getValorPago() { return valorPago; }
    public void setValorPago(BigDecimal valorPago) { this.valorPago = valorPago; }

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }
}