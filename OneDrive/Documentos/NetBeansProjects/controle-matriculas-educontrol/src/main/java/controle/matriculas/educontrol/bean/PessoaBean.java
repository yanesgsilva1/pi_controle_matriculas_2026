/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle.matriculas.educontrol.bean;

import controle.matriculas.educontrol.util.HibernateUtil;
import controle.matriculas.educontrol.model.Pessoa;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

/**
 *
 * @author leo
 */
@ManagedBean(name = "pessoaBean")
@SessionScoped

public class PessoaBean implements Serializable {

    private Pessoa pessoa = new Pessoa();   // objeto atual do formulário
    private List<Pessoa> listaPessoas;      // lista para tabela
    private Pessoa pessoaSelecionada;
    
    // Getters e Setters
    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<Pessoa> getListaPessoas() {
        return listaPessoas;
    }
    
    public Pessoa getPessoaSelecionada() {
        return pessoaSelecionada;
    }

    public void setPessoaSelecionada(Pessoa pessoaSelecionada) {
        this.pessoaSelecionada = pessoaSelecionada;
    }
    
    // Salvar
    public void salvar() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        try {
            s.save(pessoa);
            t.commit();
            pessoa = new Pessoa();
            consultar();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Pessoa cadastrada com sucesso!", null));

        } catch (ConstraintViolationException e) {
            t.rollback();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "CPF já cadastrado no sistema.", null));
        } finally {
            s.close();
        }
    }

    // Consultar
    public void consultar() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query<Pessoa> q = s.createQuery("from Pessoa", Pessoa.class);
        listaPessoas = q.list();
        s.close();
    }

    // Alterar
    public void editar() {
        if (pessoaSelecionada != null){
            this.pessoa = pessoaSelecionada;
        }
        else{
            FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Selecione uma pessoa na lista para editar.", null));
        }
    }

    // Excluir
    public void excluir() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        Pessoa p = s.get(Pessoa.class, pessoa.getIdPessoa());
        if (p != null) {
            s.delete(p);
        }
        t.commit();
        s.close();
        pessoa = new Pessoa();
        consultar();
    }

    public void limpar() {
        this.pessoa = new Pessoa();
    }

    public int getTotalRegistros() {
        return listaPessoas != null ? listaPessoas.size() : 0;
    }

    
}
