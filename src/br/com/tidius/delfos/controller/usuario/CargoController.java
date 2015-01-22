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
import br.com.tidius.delfos.model.usuario.Cargo;
import br.com.tidius.delfos.model.usuario.CargoService;
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
public class CargoController extends ComumController implements Serializable {

    private static final long serialVersionUID = -800798449790716019L;
    
    private CargoService cargoService;
    private Cargo cargo;
    private Cargo cargoSelecionado;
    private List<Cargo> listaCargo;

    public CargoController() throws ProjetoException {

	// Verifica se o usuário está logado antes de carregar a tela
	verificaUsuarioLogado();
	
	// Carrega Services
	this.cargoService = (CargoService) BeanFactory.getBean(CargoService.BEAN_NAME);

	// Verifica atributos sessão para a tela de alteração e para a pós inclusão com pesquisa
	verificaAtributosSessao();
    }
    
    
    // Getters and Setters
    

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Cargo getCargoSelecionado() {
        return cargoSelecionado;
    }

    public void setCargoSelecionado(Cargo cargoSelecionado) {
        this.cargoSelecionado = cargoSelecionado;
    }

    public List<Cargo> getListaCargo() {
        return listaCargo;
    }

    public void setListaCargo(List<Cargo> listaCargo) {
        this.listaCargo = listaCargo;
    }


    // Métodos de controle


    public void limparPesquisa() throws ProjetoException {

	this.cargo = (Cargo) cargoService.criar();
	this.setListaCargo(cargoService.listarCargo(new HashMap<String, Object>()));
    }

    public void limparCadastro() throws ProjetoException {

	this.cargo = (Cargo) cargoService.criar();
	setAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_SELECIONADA, null);
    }

    public void cancelar() throws ProjetoException, IOException {

	setAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_SELECIONADA, null);
	this.limparPesquisa();
	this.pesquisar();
	redirecionarTela(Constantes.ARQUIVO_XHTML_PESQUISAR_CARGO);
    }
    
    public void pesquisar() throws ProjetoException {

	Map<String, Object> filtro = new HashMap<String, Object>();
	filtro.put(Cargo.ATRIBUTO_CHAVE_PRIMARIA, this.cargo.getChavePrimaria());
	filtro.put(Cargo.ATRIBUTO_DESCRICAO, this.cargo.getDescricao());
	this.setListaCargo(cargoService.listarCargo(filtro));
    }

    public void salvarPesquisar() throws ProjetoException {

	this.salvar();
	setAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_INSERIDA, this.cargo);
	redirecionarTela(Constantes.ARQUIVO_XHTML_PESQUISAR_CARGO);
    }

    public void salvar() throws ProjetoException {

	if (this.cargo.getChavePrimaria() != null && this.cargo.getChavePrimaria() != 0) {
	    cargoService.atualizar(this.cargo);
	    Util.exibeMensagemTela(FacesMessage.SEVERITY_INFO, Constantes.PARAMETRO_MSG_SUCESSO_ALTERACAO,
		    cargo.toString());
	} else {
	    Cargo novoCargo = montarObjeto((Cargo) cargoService.criar());
	    cargoService.inserir(novoCargo);
	    Util.exibeMensagemTela(FacesMessage.SEVERITY_INFO, Constantes.PARAMETRO_MSG_SUCESSO_INCLUSAO,
			novoCargo.toString());
	}
    }

    public void exibirTelaAtualizacao() throws ProjetoException {
	
	setAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_SELECIONADA, cargoSelecionado);
	redirecionarTela(Constantes.ARQUIVO_XHTML_SALVAR_CARGO);
    }
    
    public void removerCargo() throws ProjetoException {

	Cargo cargo = (Cargo) cargoService.obter(this.cargoSelecionado.getChavePrimaria());
	cargoService.remover(cargo);
	limparPesquisa();
	Util.exibeMensagemTela(FacesMessage.SEVERITY_INFO, Constantes.PARAMETRO_MSG_SUCESSO_EXCLUSAO,
		this.cargoSelecionado.toString());
    }

    private void verificaAtributosSessao() throws NegocioException {
	
	if (getAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_SELECIONADA) != null) {
	    
	    this.cargo = (Cargo) getAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_SELECIONADA);
	    setAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_SELECIONADA, null);
	    
	} else if (getAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_INSERIDA) != null) {
	    
	    this.cargo = (Cargo) getAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_INSERIDA);
	    setAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_INSERIDA, null);
	    
	    Map<String, Object> filtro = new HashMap<String, Object>();
	    filtro.put(Cargo.ATRIBUTO_DESCRICAO, this.cargo.getDescricao());
	    this.setListaCargo(cargoService.listarCargo(filtro));
		
	} else {
	    
	    this.cargo = (Cargo) cargoService.criar();
	    this.setListaCargo(cargoService.listarCargo(new HashMap<String, Object>()));
	}
    }
    
    private Cargo montarObjeto(Cargo obj) {

	obj.setTenant(((Usuario) getAttributeSession(ATRIBUTO_SESSAO_USUARIO_LOGADO)).getTenant());	
	obj.setDescricao(this.cargo.getDescricao());
	return obj;
    }

}
