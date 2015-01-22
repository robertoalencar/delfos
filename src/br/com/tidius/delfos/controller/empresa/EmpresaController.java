package br.com.tidius.delfos.controller.empresa;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.tidius.delfos.controller.util.ComumController;
import br.com.tidius.delfos.model.comum.endereco.Cidade;
import br.com.tidius.delfos.model.comum.endereco.EnderecoService;
import br.com.tidius.delfos.model.comum.excecoes.NegocioException;
import br.com.tidius.delfos.model.comum.excecoes.ProjetoException;
import br.com.tidius.delfos.model.empresa.Empresa;
import br.com.tidius.delfos.model.empresa.EmpresaService;
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
public class EmpresaController extends ComumController implements Serializable {

    private static final long serialVersionUID = 6083297547245972685L;

    private EmpresaService empresaService;
    private EnderecoService enderecoService;
    
    private Empresa empresa;
    private Empresa empresaSelecionado;
   
    private List<Empresa> listaEmpresa;
    
    
    public EmpresaController() throws ProjetoException {

	System.out.println("teste");
	
	// Verifica se o usu�rio est� logado antes de carregar a tela
	verificaUsuarioLogado();
	
	// Carrega Services
	this.empresaService = (EmpresaService) BeanFactory.getBean(EmpresaService.BEAN_NAME);
	this.enderecoService = (EnderecoService) BeanFactory.getBean(EnderecoService.BEAN_NAME);
	
	//Carrega Combos
	setListaEstado(enderecoService.listarEstado(Boolean.TRUE));
	
	//Verifica atributos sess�o para a tela de altera��o e para a p�s inclus�o com pesquisa
	verificaAtributosSessao();
    }
 
    
    
    public Empresa getEmpresa() {
        return empresa;
    }
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    public Empresa getEmpresaSelecionado() {
        return empresaSelecionado;
    }
    public void setEmpresaSelecionado(Empresa empresaSelecionado) {
        this.empresaSelecionado = empresaSelecionado;
    }
    public List<Empresa> getListaEmpresa() {
        return listaEmpresa;
    }
    public void setListaEmpresa(List<Empresa> listaEmpresa) {
        this.listaEmpresa = listaEmpresa;
    }



    // M�todos de controle
    


    public void pesquisar() throws ProjetoException {

	Map<String, Object> filtro = new HashMap<String, Object>();
	filtro.put(Empresa.ATRIBUTO_CHAVE_PRIMARIA, this.empresa.getChavePrimaria());
	filtro.put(Empresa.ATRIBUTO_NOME_FANTASIA, this.empresa.getNomeFantasia());
	this.setListaEmpresa(empresaService.listarEmpresa(filtro));
    }
    
    public void salvarPesquisar() throws ProjetoException {

	this.salvar();
	setAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_INSERIDA, this.empresa);
	redirecionarTela(Constantes.ARQUIVO_XHTML_PESQUISAR_EMPRESA);
    }

    public void salvar() throws ProjetoException {

	if (this.empresa.getChavePrimaria() != null && this.empresa.getChavePrimaria() != 0) {
	    preencheAtributosEndereco(this.empresa);
	    preecheAtributosContato(this.empresa);
	    empresaService.atualizar(this.empresa);
	    Util.exibeMensagemTela(FacesMessage.SEVERITY_INFO, Constantes.PARAMETRO_MSG_SUCESSO_ALTERACAO, empresa.toString());
	} else {
	    Empresa novoEmpresa = montarObjeto((Empresa) empresaService.criar());
	    empresaService.inserir(novoEmpresa);
	    Util.exibeMensagemTela(FacesMessage.SEVERITY_INFO, Constantes.PARAMETRO_MSG_SUCESSO_INCLUSAO, novoEmpresa.toString());
	}
    }
    
    public void exibirTelaAtualizacao() throws ProjetoException {
	
	setAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_SELECIONADA, this.empresaSelecionado);
	redirecionarTela(Constantes.ARQUIVO_XHTML_SALVAR_EMPRESA);
    }
    
    public void limparPesquisa() throws ProjetoException {

	this.empresa = (Empresa) empresaService.criar();
	this.setListaEmpresa(empresaService.listarEmpresa(new HashMap<String, Object>()));
    }

    public void limparCadastro() throws ProjetoException {

	this.empresa = (Empresa) empresaService.criar();
	limparCamposEndereco();
	setAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_SELECIONADA, null);
    }
    
    public void removerEmpresa() throws ProjetoException {

	Empresa empresa = (Empresa) empresaService.obter(this.empresaSelecionado.getChavePrimaria());
	empresaService.remover(empresa);
	limparPesquisa();
	Util.exibeMensagemTela(FacesMessage.SEVERITY_INFO, Constantes.PARAMETRO_MSG_SUCESSO_EXCLUSAO, this.empresaSelecionado.toString());
    }
    
    public void carregaCidadesPorEstado() throws NegocioException {

	if ((super.getEstado() != null) && (!"".equals(super.getEstado()))) {

	    Map<String, Object> filtro = new HashMap<String, Object>();
	    filtro.put(Cidade.ATRIBUTO_ESTADO, super.getEstado());
	    setListaCidade(enderecoService.listarCidade(filtro));

	} else {
	    setListaCidade(null);
	}
    }
    
    public void cancelar() throws ProjetoException, IOException {

	setAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_SELECIONADA, null);
	this.limparPesquisa();
	this.pesquisar();
	redirecionarTela(Constantes.ARQUIVO_XHTML_PESQUISAR_EMPRESA);
    }
    
    private void verificaAtributosSessao() throws NegocioException {
	
	if (getAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_SELECIONADA) != null) {
	    
	    this.empresa = (Empresa) getAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_SELECIONADA);
	    setListaEstado(enderecoService.listarEstado(Boolean.TRUE));
	    carregaAtributosEndereco();
	    carregaAtributosContato();
	    carregaCidadesPorEstado();
	    setAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_SELECIONADA, null);
	    
	} else if (getAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_INSERIDA) != null) {
	    
	    this.empresa = (Empresa) getAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_INSERIDA);
	    setAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_INSERIDA, null);
	    
	    Map<String, Object> filtro = new HashMap<String, Object>();
	    filtro.put(Empresa.ATRIBUTO_CNPJ, this.empresa.getCnpj());
	    this.setListaEmpresa(empresaService.listarEmpresa(filtro));
		
	} else {
	    
	    this.empresa = (Empresa) empresaService.criar();
	    this.setListaEmpresa(empresaService.listarEmpresa(new HashMap<String, Object>()));
	}
    }
    
    private void carregaAtributosContato() {
	
	this.empresa.setContato(empresaService.criarEmpresaContato());
    }

    private void carregaAtributosEndereco() {
	
	if (this.empresa.getEndereco() == null) {
	   
	    this.empresa.setEndereco(empresaService.criarEmpresaEndereco());
	    
	} else {
	    
	    this.setRua(this.empresa.getEndereco().getRua());
	    this.setBairro(this.empresa.getEndereco().getBairro());
	    this.setComplemento(this.empresa.getEndereco().getComplemento());
	    this.setNumero(this.empresa.getEndereco().getNumero());
	    this.setCep(this.empresa.getEndereco().getCep());
	    this.setEstado(this.empresa.getEndereco().getCidade().getEstado());
	    this.setCidade(this.empresa.getEndereco().getCidade());
	}
    }
    
    private Empresa montarObjeto(Empresa obj) {
	
	obj.setTenant(((Usuario) getAttributeSession(ATRIBUTO_SESSAO_USUARIO_LOGADO)).getTenant());
	obj.setEmpresaPrincipal(this.empresa.getEmpresaPrincipal());
	obj.setNomeFantasia(this.empresa.getNomeFantasia());
	obj.setRazaoSocial(this.empresa.getRazaoSocial());
	obj.setCnpj(this.empresa.getCnpj());
	obj.setInscricaoEstadual(this.empresa.getInscricaoEstadual());
	obj.setHomePage(this.empresa.getHomePage());
	obj.setDadosAdicionais(this.empresa.getDadosAdicionais());
	preencheAtributosEndereco(obj);
	preecheAtributosContato(obj);
	
	return obj;
    }
    
    private void preencheAtributosEndereco(Empresa obj) {
	
	if (super.estaPreenchido()) { 
	    this.empresa.getEndereco().setDataCadastro(Calendar.getInstance().getTime());
	    this.empresa.getEndereco().setUltimaAlteracao(Calendar.getInstance().getTime());
	    this.empresa.getEndereco().setHabilitado(Boolean.TRUE);
	    this.empresa.getEndereco().setRua(this.getRua());
	    this.empresa.getEndereco().setBairro(this.getBairro());
	    this.empresa.getEndereco().setComplemento(this.getComplemento());
	    this.empresa.getEndereco().setNumero(this.getNumero());
	    this.empresa.getEndereco().setCep(this.getCep());
	    this.empresa.getEndereco().setCidade(this.getCidade());
	    obj.setEndereco(this.empresa.getEndereco());
	} else {
	    obj.setEndereco(null);
	}
    }
    
    private void preecheAtributosContato(Empresa obj) {
	
	if (this.empresa.getContato().estaPreenchido()) {
	    this.empresa.getContato().setDataCadastro(Calendar.getInstance().getTime());
	    this.empresa.getContato().setUltimaAlteracao(Calendar.getInstance().getTime());
	    this.empresa.getContato().setHabilitado(Boolean.TRUE);
	    obj.setContato(this.empresa.getContato());
	} else {
	    obj.setContato(null);
	}
    }
    
}