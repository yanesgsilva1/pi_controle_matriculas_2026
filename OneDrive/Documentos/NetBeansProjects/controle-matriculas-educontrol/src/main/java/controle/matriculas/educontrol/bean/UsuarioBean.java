package controle.matriculas.educontrol.bean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.application.FacesMessage;
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

    public void salvar() {
        try {
            if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
                
                String senhaCriptografada = SenhaUtil.criptografar(usuario.getSenha());
                usuario.setSenha(senhaCriptografada);

                // PERFIL PADRÃO
                usuario.setPerfil("ADMIN");

                usuarioDAO.salvar(usuario);

                usuario = new Usuario();
                
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Usuário cadastrado com senha protegida!"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção:", "Preencha a senha."));
            }
            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar:", e.getMessage()));
            e.printStackTrace();
        }
    }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}