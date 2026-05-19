package controle.matriculas.educontrol.bean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

// Importações das suas outras classes
import controle.matriculas.educontrol.model.Usuario;
import controle.matriculas.educontrol.dao.UsuarioDAO; 
import controle.matriculas.educontrol.util.SenhaUtil;

@ManagedBean(name = "usuarioBean")
@ViewScoped
public class UsuarioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // Objeto que receberá os dados da tela (login e senha)
    private Usuario usuario = new Usuario();
    
    // Objeto que enviará os dados para o banco
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Método chamado pelo botão da tela de cadastro
     */
    public void salvar() {
        try {
            // --- REQUISITO 02: CRIPTOGRAFIA ---
            // Antes de salvar, pegamos a senha digitada e transformamos em BCrypt
            if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
                
                // Gera o Hash seguro
                String senhaCriptografada = SenhaUtil.criptografar(usuario.getSenha());
                
                // Substitui a senha "aberta" pela senha criptografada no objeto
                usuario.setSenha(senhaCriptografada);

                // Agora o DAO salva no banco de dados
                usuarioDAO.salvar(usuario);

                // Limpa o formulário para o próximo cadastro
                usuario = new Usuario();
                
                // Mensagem de sucesso para o usuário na tela
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Usuário cadastrado com senha protegida!"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção:", "Preencha a senha."));
            }
            
        } catch (Exception e) {
            // Em caso de erro (ex: banco fora do ar), mostra na tela
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar:", e.getMessage()));
            e.printStackTrace();
        }
    }

    // --- GETTERS E SETTERS ---
    // Necessários para o JSF (PrimeFaces) conseguir ler e escrever nos campos
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}