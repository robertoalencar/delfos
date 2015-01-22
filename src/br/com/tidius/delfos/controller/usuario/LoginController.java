package br.com.tidius.delfos.controller.usuario;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.tidius.delfos.controller.util.ComumController;
import br.com.tidius.delfos.model.comum.excecoes.ProjetoException;
import br.com.tidius.delfos.model.usuario.Usuario;
import br.com.tidius.delfos.model.usuario.UsuarioService;
import br.com.tidius.delfos.model.util.BeanFactory;
import br.com.tidius.delfos.model.util.Constantes;
import br.com.tidius.delfos.model.util.Util;

/**
 * @author Roberto Alencar
 * 
 */
@ManagedBean
@ViewScoped
public class LoginController extends ComumController implements Serializable {

    private static final long serialVersionUID = 7861798390347753601L;

    private UsuarioService usuarioService;
    private String login;
    private String senha;
    private boolean continuarConectado;

    public LoginController() throws ProjetoException {

	this.usuarioService = (UsuarioService) BeanFactory.getBean(UsuarioService.BEAN_NAME);
    }

    public String getLogin() {
	return login;
    }

    public void setLogin(String login) {
	this.login = login;
    }

    public String getSenha() {
	return senha;
    }

    public void setSenha(String senha) {
	this.senha = senha;
    }

    public boolean isContinuarConectado() {
	return continuarConectado;
    }

    public void setContinuarConectado(boolean continuarConectado) {
	this.continuarConectado = continuarConectado;
    }

    public void login() throws ProjetoException {

	Usuario usuario = usuarioService.loginSistema(login, senha);

	if (usuario != null) {

	    setAttributeSession(ATRIBUTO_SESSAO_USUARIO_LOGADO, usuario);
	    redirecionarTela(Constantes.ARQUIVO_XHTML_HOME);

	} else {

	    Util.exibeMensagemTela(FacesMessage.SEVERITY_ERROR, Constantes.PARAMETRO_MSG_LOGIN_INCORRETO);
	}
    }

    public void logout() throws ProjetoException {

	setAttributeSession(ATRIBUTO_SESSAO_USUARIO_LOGADO, null);
	setAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_SELECIONADA, null);
	setAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_INSERIDA, null);
	redirecionarTela(Constantes.ARQUIVO_XHTML_INDEX);
    }
}