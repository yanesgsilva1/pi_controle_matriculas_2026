/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle.matriculas.educontrol.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

/**
 * MenuBean - Controlador do menu principal Sistema de Controle de Matrícula
 */
@ManagedBean(name = "menuBean")
@SessionScoped
public class MenuBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String paginaAtual = "";

    //  Navegação - Cadastros

    public String navegarAlunos() {
        this.paginaAtual = "alunos";
        return "/cadastros/alunos?faces-redirect=true";
    }

    public String navegarProfessores() {
        this.paginaAtual = "professores";
        return "/cadastros/professores?faces-redirect=true";
    }

    public String navegarDisciplinas() {
        this.paginaAtual = "disciplinas";
        return "/cadastros/disciplinas?faces-redirect=true";
    }

    public String navegarTurmas() {
        this.paginaAtual = "turmas";
        return "/cadastros/turmas?faces-redirect=true";
    }

    public String navegarCursos() {
        this.paginaAtual = "cursos";
        return "/cadastros/cursos?faces-redirect=true";
    }

    //  Navegação - Relatórios

    public String navegarRelatorioMatriculas() {
        this.paginaAtual = "rel-matriculas";
        return "/relatorios/matriculas?faces-redirect=true";
    }

    public String navegarRelatorioAlunosTurma() {
        this.paginaAtual = "rel-alunos-turma";
        return "/relatorios/alunosPorTurma?faces-redirect=true";
    }

    public String navegarRelatorioHistorico() {
        this.paginaAtual = "rel-historico";
        return "/relatorios/historico?faces-redirect=true";
    }

    //  Navegação - Sistema
    public String navegarConfiguracoes() {
        this.paginaAtual = "configuracoes";
        return "/sistema/configuracoes?faces-redirect=true";
    }

    public String navegarUsuarios() {
        this.paginaAtual = "usuarios";
        return "/sistema/usuarios?faces-redirect=true";
    }

    public String sair() {
        this.paginaAtual = "";
        // Invalida a sessão e redireciona para o login
        javax.faces.context.FacesContext.getCurrentInstance()
                .getExternalContext().invalidateSession();
        return "/login?faces-redirect=true";
    }

    //  Getters / Setters
    public String getPaginaAtual() {
        return paginaAtual;
    }

    public void setPaginaAtual(String paginaAtual) {
        this.paginaAtual = paginaAtual;
    }
}
