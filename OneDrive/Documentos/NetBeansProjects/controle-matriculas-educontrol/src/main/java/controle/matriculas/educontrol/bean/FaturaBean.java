package controle.matriculas.educontrol.bean;

import controle.matriculas.educontrol.dao.FaturaDAO;
import controle.matriculas.educontrol.model.Fatura;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class FaturaBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Fatura> faturas;
    private final FaturaDAO faturaDAO = new FaturaDAO();

    @PostConstruct
    public void init() {
        // Alimenta a lista de faturas assim que a tela abre
        faturas = faturaDAO.listarTodas();
    }

    // Getter necessário para o PrimeFaces ler os dados
    public List<Fatura> getFaturas() {
        return faturas;
    }

    public void setFaturas(List<Fatura> faturas) {
        this.faturas = faturas;
    }
}
