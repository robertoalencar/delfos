package br.com.tidius.delfos.controller.usuario;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.tidius.delfos.controller.util.ComumController;
import br.com.tidius.delfos.model.comum.excecoes.ProjetoException;

/**
 * @author Roberto Alencar
 * 
 */
@ManagedBean
@ViewScoped
public class HomeController extends ComumController implements Serializable {

    private static final long serialVersionUID = -7994450761553725736L;

    private String teste;

    public HomeController() throws ProjetoException {

	// Verifica se o usuário está logado antes de carregar a tela
	verificaUsuarioLogado();
    }

    public String getTeste() {
	return teste;
    }

    public void setTeste(String teste) {
	this.teste = teste;
    }

}