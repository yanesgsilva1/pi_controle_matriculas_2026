package controle.matriculas.educontrol.bean;

import controle.matriculas.educontrol.model.Fatura;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class FaturamentoBean implements Serializable {

    private List<Fatura> listaFaturas;

    @PostConstruct
    public void init() {
        listaFaturas = new ArrayList<>();
        // Note que usei "PENDENTE" aqui para bater com o cálculo abaixo
        listaFaturas.add(new Fatura(1L, "Cauã Silva", new BigDecimal("450.00"), LocalDate.now().plusDays(5), "PENDENTE"));
        listaFaturas.add(new Fatura(2L, "Yanes Silva", new BigDecimal("500.00"), LocalDate.now().minusDays(2), "PENDENTE"));
    }

    public void darBaixa(Fatura fatura) {
        fatura.setStatus("PAGO");
        System.out.println("Fatura do aluno " + fatura.getAlunoNome() + " foi paga!");
    }

    // O método agora está DENTRO da classe
    public BigDecimal getTotalPendente() {
        if (listaFaturas == null) return BigDecimal.ZERO;
        
        return listaFaturas.stream()
            .filter(f -> f.getStatus().equals("PENDENTE"))
            .map(Fatura::getValor)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Fatura> getListaFaturas() {
        return listaFaturas;
    }
}