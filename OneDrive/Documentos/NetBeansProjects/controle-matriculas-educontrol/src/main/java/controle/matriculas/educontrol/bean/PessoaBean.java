package controle.matriculas.educontrol.bean;

import controle.matriculas.educontrol.model.Pessoa;
import controle.matriculas.educontrol.util.HibernateUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "pessoaBean")
@ViewScoped
public class PessoaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Pessoa pessoa = new Pessoa();
    private List<Pessoa> listaPessoas;
    private Pessoa pessoaSelecionada;
    private Integer idPessoaSelecionada;

    @PostConstruct
    public void init() {
        pessoa = new Pessoa();
        pessoaSelecionada = null;
        idPessoaSelecionada = null;
        consultar();
    }

    // Getters e Setters
    public Pessoa getPessoa() { return pessoa; }
    public void setPessoa(Pessoa pessoa) { this.pessoa = pessoa; }

    public List<Pessoa> getListaPessoas() { return listaPessoas; }

    public Pessoa getPessoaSelecionada() { return pessoaSelecionada; }
    public void setPessoaSelecionada(Pessoa pessoaSelecionada) {
        this.pessoaSelecionada = pessoaSelecionada;
    }

    public Integer getIdPessoaSelecionada() { return idPessoaSelecionada; }
    public void setIdPessoaSelecionada(Integer idPessoaSelecionada) {
        this.idPessoaSelecionada = idPessoaSelecionada;
    }

    // Captura seleção de linha na tabela
    public void aoClicarNaLinha(SelectEvent event) {
        this.pessoaSelecionada = (Pessoa) event.getObject();
        this.idPessoaSelecionada = pessoaSelecionada.getIdPessoa();
    }

    // Salvar — distingue novo cadastro (idPessoa null) de edição
    public void salvar() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try {
            if (pessoa.getIdPessoa() == null) {
                s.save(pessoa);
                addMsg(FacesMessage.SEVERITY_INFO, "Pessoa cadastrada com sucesso!");
            } else {
                s.merge(pessoa);
                addMsg(FacesMessage.SEVERITY_INFO, "Pessoa alterada com sucesso!");
            }
            t.commit();
            resetar();
        } catch (ConstraintViolationException e) {
            t.rollback();
            addMsg(FacesMessage.SEVERITY_ERROR, "CPF já cadastrado no sistema.");
        } finally {
            s.close();
        }
    }

    // Consultar todos os registros
public void consultar() {
    try (Session s = HibernateUtil.getSessionFactory().openSession()) {
        Query<Pessoa> q = s.createQuery("from Pessoa order by nomePessoa", Pessoa.class);
        listaPessoas = q.list();
    } catch (Exception e) {
        addMsg(FacesMessage.SEVERITY_ERROR, "Erro ao consultar pessoas.");
        e.printStackTrace();
    }
}

    // Editar — cria cópia do objeto selecionado para não afetar a lista
    public void editar() {
        if (pessoaSelecionada == null) {
            addMsg(FacesMessage.SEVERITY_WARN, "Selecione uma pessoa na lista para editar.");
            return;
        }
        Pessoa copia = new Pessoa();
        copia.setIdPessoa(pessoaSelecionada.getIdPessoa());
        copia.setNomePessoa(pessoaSelecionada.getNomePessoa());
        copia.setEndereco(pessoaSelecionada.getEndereco());
        copia.setUf(pessoaSelecionada.getUf());
        copia.setTelefone(pessoaSelecionada.getTelefone());
        copia.setCpf(pessoaSelecionada.getCpf());
        copia.setEmail(pessoaSelecionada.getEmail());
        copia.setTipo(pessoaSelecionada.getTipo());
        this.pessoa = copia;
    }

    // Excluir registro selecionado
    public void excluir() {
        if (pessoaSelecionada == null) {
            addMsg(FacesMessage.SEVERITY_WARN, "Selecione uma pessoa na lista para excluir.");
            return;
        }
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try {
            Pessoa p = s.get(Pessoa.class, pessoaSelecionada.getIdPessoa());
            if (p != null) {
                s.delete(p);
            }
            t.commit();
            resetar();
            addMsg(FacesMessage.SEVERITY_INFO, "Pessoa excluída com sucesso!");
        } catch (Exception e) {
            t.rollback();
            addMsg(FacesMessage.SEVERITY_ERROR, "Erro ao excluir. Tente novamente.");
        } finally {
            s.close();
        }
    }

    // Limpa formulário e seleção
    public void limpar() {
        resetar();
    }

    public int getTotalRegistros() {
        return listaPessoas != null ? listaPessoas.size() : 0;
    }

    // Reseta estado do formulário e recarrega lista
    private void resetar() {
        this.pessoa = new Pessoa();
        this.pessoaSelecionada = null;
        this.idPessoaSelecionada = null;
        consultar();
    }

    // Método auxiliar para mensagens — evita repetição de código
    private void addMsg(FacesMessage.Severity severity, String mensagem) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(severity, mensagem, null));
    }
}