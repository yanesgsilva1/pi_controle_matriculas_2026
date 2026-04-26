package controle.matriculas.educontrol.bean;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import controle.matriculas.educontrol.model.Usuario;
import controle.matriculas.educontrol.dao.UsuarioDAO;
import controle.matriculas.educontrol.util.SenhaUtil;

@ManagedBean(name = "usuarioBean")
@ViewScoped
public class UsuarioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Usuario usuario = new Usuario();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private String confirmarSenha;

    public void salvar() {
        try {
            // Valida se as senhas conferem
            if (confirmarSenha == null || !confirmarSenha.equals(usuario.getSenha())) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "As senhas não conferem.", null));
                return;
            }

            if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
                String senhaCriptografada = SenhaUtil.criptografar(usuario.getSenha());
                usuario.setSenha(senhaCriptografada);
                usuario.setPerfil("ADMIN");
                usuarioDAO.salvar(usuario);
                usuario = new Usuario();
                confirmarSenha = null;
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Usuário cadastrado com sucesso!", null));
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Preencha a senha.", null));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro ao salvar: " + e.getMessage(), null));
            e.printStackTrace();
        }
    }

    public String cancelar() {
        return "/menuPrincipal?faces-redirect=true";
    }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getConfirmarSenha() { return confirmarSenha; }
    public void setConfirmarSenha(String confirmarSenha) { this.confirmarSenha = confirmarSenha; }
}