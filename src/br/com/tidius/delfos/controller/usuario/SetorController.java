package br.com.tidius.delfos.controller.usuario;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.tidius.delfos.controller.util.ComumController;
import br.com.tidius.delfos.model.comum.excecoes.NegocioException;
import br.com.tidius.delfos.model.comum.excecoes.ProjetoException;
import br.com.tidius.delfos.model.usuario.Setor;
import br.com.tidius.delfos.model.usuario.SetorService;
import br.com.tidius.delfos.model.usuario.Usuario;
import br.com.tidius.delfos.model.util.BeanFactory;
import br.com.tidius.delfos.model.util.Constantes;
import br.com.tidius.delfos.model.util.Util;

/**
 * @author Roberto Alencar
 * 
 */
@ManagedBean
@ViewScoped
public class SetorController extends ComumController implements Serializable {

    private static final long serialVersionUID = 2236603261432477651L;
    
    private SetorService setorService;
    private Setor setor;
    private Setor setorSelecionado;
    private List<Setor> listaSetor;

    public SetorController() throws ProjetoException {

	// Verifica se o usuário está logado antes de carregar a tela
	verificaUsuarioLogado();
	
	// Carrega Services
	this.setorService = (SetorService) BeanFactory.getBean(SetorService.BEAN_NAME);

	// Verifica atributos sessão para a tela de alteração e para a pós inclusão com pesquisa
	verificaAtributosSessao();

    }
    
    
    // Getters and Setters
    

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    public Setor getSetorSelecionado() {
        return setorSelecionado;
    }

    public void setSetorSelecionado(Setor setorSelecionado) {
        this.setorSelecionado = setorSelecionado;
    }

    public List<Setor> getListaSetor() {
        return listaSetor;
    }

    public void setListaSetor(List<Setor> listaSetor) {
        this.listaSetor = listaSetor;
    }


    
    // Métodos de controle


    public void limparPesquisa() throws ProjetoException {

	this.setor = (Setor) setorService.criar();
	this.setListaSetor(setorService.listarSetor(new HashMap<String, Object>()));
    }

    public void limparCadastro() throws ProjetoException {

	this.setor = (Setor) setorService.criar();
	setAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_SELECIONADA, null);
    }

    public void cancelar() throws ProjetoException, IOException {

	setAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_SELECIONADA, null);
	this.limparPesquisa();
	this.pesquisar();
	redirecionarTela(Constantes.ARQUIVO_XHTML_PESQUISAR_SETOR);
    }
    
    public void pesquisar() throws ProjetoException {

	Map<String, Object> filtro = new HashMap<String, Object>();
	filtro.put(Setor.ATRIBUTO_CHAVE_PRIMARIA, this.setor.getChavePrimaria());
	filtro.put(Setor.ATRIBUTO_DESCRICAO, this.setor.getDescricao());
	this.setListaSetor(setorService.listarSetor(filtro));
    }

    public void salvarPesquisar() throws ProjetoException {

	this.salvar();
	setAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_INSERIDA, this.setor);
	redirecionarTela(Constantes.ARQUIVO_XHTML_PESQUISAR_SETOR);
    }

    public void salvar() throws ProjetoException {

	if (this.setor.getChavePrimaria() != null && this.setor.getChavePrimaria() != 0) {
	    setorService.atualizar(this.setor);
	    Util.exibeMensagemTela(FacesMessage.SEVERITY_INFO, Constantes.PARAMETRO_MSG_SUCESSO_ALTERACAO,
		    setor.toString());
	} else {
	    Setor novoSetor = montarObjeto((Setor) setorService.criar());
	    setorService.inserir(novoSetor);
	    Util.exibeMensagemTela(FacesMessage.SEVERITY_INFO, Constantes.PARAMETRO_MSG_SUCESSO_INCLUSAO,
			novoSetor.toString());
	}
    }

    public void exibirTelaAtualizacao() throws ProjetoException {
	
	setAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_SELECIONADA, setorSelecionado);
	redirecionarTela(Constantes.ARQUIVO_XHTML_SALVAR_SETOR);
    }
    
    public void removerSetor() throws ProjetoException {

	Setor setor = (Setor) setorService.obter(this.setorSelecionado.getChavePrimaria());
	setorService.remover(setor);
	limparPesquisa();
	Util.exibeMensagemTela(FacesMessage.SEVERITY_INFO, Constantes.PARAMETRO_MSG_SUCESSO_EXCLUSAO,
		this.setorSelecionado.toString());
    }

    private void verificaAtributosSessao() throws NegocioException {
	
	if (getAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_SELECIONADA) != null) {
	    
	    this.setor = (Setor) getAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_SELECIONADA);
	    setAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_SELECIONADA, null);
	    
	} else if (getAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_INSERIDA) != null) {
	    
	    this.setor = (Setor) getAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_INSERIDA);
	    setAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_INSERIDA, null);
	    
	    Map<String, Object> filtro = new HashMap<String, Object>();
	    filtro.put(Setor.ATRIBUTO_DESCRICAO, this.setor.getDescricao());
	    this.setListaSetor(setorService.listarSetor(filtro));
		
	} else {
	    
	    this.setor = (Setor) setorService.criar();
	    this.setListaSetor(setorService.listarSetor(new HashMap<String, Object>()));
	}
    }
    
    private Setor montarObjeto(Setor obj) {

	obj.setTenant(((Usuario) getAttributeSession(ATRIBUTO_SESSAO_USUARIO_LOGADO)).getTenant());
	obj.setDescricao(this.setor.getDescricao());
	return obj;
    }

}
