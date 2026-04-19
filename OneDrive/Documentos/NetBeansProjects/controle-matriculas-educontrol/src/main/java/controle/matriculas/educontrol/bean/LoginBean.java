/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle.matriculas.educontrol.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
/**
 *
 * @author leo
 */

@ManagedBean (name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {
    private String usuario;
    private String senha;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    // Ações da tela de login
    
    public String login() {
        if("admin".equals(usuario) && "admin".equals(senha)) {
            return "menuPrincipal.xhtml?faces-redirect=true"; // redireciona para o menu principal
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Usuário ou Senha Inválidos", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null; // permanece na mesma página
        }
    }

    public String cadastrar() {
        return "cadastro.xhtml?faces-redirect=true";
    }

    public String sair() {
        return "login.xhtml?faces-redirect=true";
    }
}
