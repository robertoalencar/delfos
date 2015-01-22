package br.com.tidius.delfos.controller.usuario;

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
import br.com.tidius.delfos.model.usuario.Cargo;
import br.com.tidius.delfos.model.usuario.CargoService;
import br.com.tidius.delfos.model.usuario.Setor;
import br.com.tidius.delfos.model.usuario.SetorService;
import br.com.tidius.delfos.model.usuario.Usuario;
import br.com.tidius.delfos.model.usuario.UsuarioService;
import br.com.tidius.delfos.model.util.BeanFactory;
import br.com.tidius.delfos.model.util.Constantes;
import br.com.tidius.delfos.model.util.MensagemUtil;
import br.com.tidius.delfos.model.util.Util;

/**
 * @author Roberto Alencar
 * 
 */
@ManagedBean
@ViewScoped
public class UsuarioController extends ComumController implements Serializable {

    private static final long serialVersionUID = 8021685771682706694L;

    private UsuarioService usuarioService;
    private EmpresaService empresaService;
    private SetorService setorService;
    private CargoService cargoService;
    private EnderecoService enderecoService;
    
    private Usuario usuario;
    private Usuario usuarioSelecionado;

    private List<Usuario> listaUsuario;
    private List<Setor> listaSetor;
    private List<Cargo> listaCargo;
    private List<Empresa> listaEmpresa;
    
    private String confirmacaoSenha;
    

    public UsuarioController() throws ProjetoException {

	// Verifica se o usuário está logado antes de carregar a tela
	verificaUsuarioLogado();
	
	// Carrega Services
	this.usuarioService = (UsuarioService) BeanFactory.getBean(UsuarioService.BEAN_NAME);
	this.setorService = (SetorService) BeanFactory.getBean(SetorService.BEAN_NAME);
	this.empresaService = (EmpresaService) BeanFactory.getBean(EmpresaService.BEAN_NAME);
	this.cargoService = (CargoService) BeanFactory.getBean(CargoService.BEAN_NAME);
	this.enderecoService = (EnderecoService) BeanFactory.getBean(EnderecoService.BEAN_NAME);
	
	//Carrega Combos
	this.listaEmpresa = empresaService.listarEmpresa(new HashMap<String, Object>());
	this.listaSetor = setorService.listarSetor(new HashMap<String, Object>());
	this.listaCargo = cargoService.listarCargo(new HashMap<String, Object>());
	setListaEstado(enderecoService.listarEstado(Boolean.TRUE));
	
	//Verifica atributos sessão para a tela de alteração e para a pós inclusão com pesquisa
	verificaAtributosSessao();
    }
    
    
    // Getters and Setters
    

    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public Usuario getUsuarioSelecionado() {
        return usuarioSelecionado;
    }
    public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
        this.usuarioSelecionado = usuarioSelecionado;
    }
    public List<Usuario> getListaUsuario() {
        return listaUsuario;
    }
    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }
    public List<Setor> getListaSetor() {
        return listaSetor;
    }
    public void setListaSetor(List<Setor> listaSetor) {
        this.listaSetor = listaSetor;
    }
    public List<Empresa> getListaEmpresa() {
        return listaEmpresa;
    }
    public void setListaEmpresa(List<Empresa> listaEmpresa) {
        this.listaEmpresa = listaEmpresa;
    }
    public List<Cargo> getListaCargo() {
        return listaCargo;
    }
    public void setListaCargo(List<Cargo> listaCargo) {
        this.listaCargo = listaCargo;
    }
    public String getConfirmacaoSenha() {
	if (confirmacaoSenha != null) {
	    return confirmacaoSenha;
	} else {
	    return this.usuario.getSenha();
	}
    }
    public void setConfirmacaoSenha(String confirmacaoSenha) {
        this.confirmacaoSenha = confirmacaoSenha;
    }
    
    
    
    // Métodos de controle
   


    public void limparPesquisa() throws ProjetoException {

	this.usuario = (Usuario) usuarioService.criar();
	this.setListaUsuario(usuarioService.listarUsuario(new HashMap<String, Object>()));
    }

    public void limparCadastro() throws ProjetoException {

	this.usuario = (Usuario) usuarioService.criar();
	limparCamposEndereco();
	setAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_SELECIONADA, null);
    }

    public void cancelar() throws ProjetoException, IOException {

	setAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_SELECIONADA, null);
	this.limparPesquisa();
	this.pesquisar();
	redirecionarTela(Constantes.ARQUIVO_XHTML_PESQUISAR_USUARIO);
    }
    
    public void pesquisar() throws ProjetoException {

	Map<String, Object> filtro = new HashMap<String, Object>();
	filtro.put(Usuario.ATRIBUTO_CHAVE_PRIMARIA, this.usuario.getChavePrimaria());
	filtro.put(Usuario.ATRIBUTO_NOME, this.usuario.getNome());
	filtro.put(Usuario.ATRIBUTO_SETOR, this.usuario.getSetor());
	filtro.put(Usuario.ATRIBUTO_CARGO, this.usuario.getCargo());
	filtro.put(Usuario.ATRIBUTO_EMPRESA, this.usuario.getEmpresa());
	this.setListaUsuario(usuarioService.listarUsuario(filtro));
    }

    public void salvarPesquisar() throws ProjetoException {

	this.salvar();
	setAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_INSERIDA, this.usuario);
	redirecionarTela(Constantes.ARQUIVO_XHTML_PESQUISAR_USUARIO);
    }

    public void salvar() throws ProjetoException {

	if (this.usuario.getChavePrimaria() != null && this.usuario.getChavePrimaria() != 0) {
	    verificaConfirmacaoSenha(this.usuario);
	    preencheAtributosEndereco(this.usuario);
	    preecheAtributosContato(this.usuario);
	    usuarioService.atualizar(this.usuario);
	    Util.exibeMensagemTela(FacesMessage.SEVERITY_INFO, Constantes.PARAMETRO_MSG_SUCESSO_ALTERACAO,
		    usuario.toString());
	} else {
	    Usuario novoUsuario = montarObjeto((Usuario) usuarioService.criar());
	    verificaConfirmacaoSenha(novoUsuario);
	    usuarioService.inserir(novoUsuario);
	    Util.exibeMensagemTela(FacesMessage.SEVERITY_INFO, Constantes.PARAMETRO_MSG_SUCESSO_INCLUSAO,
			novoUsuario.toString());
	}
    }

    public void exibirTelaAtualizacao() throws ProjetoException {
	
	setAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_SELECIONADA, this.usuarioSelecionado);
	redirecionarTela(Constantes.ARQUIVO_XHTML_SALVAR_USUARIO);
    }
    
    public void removerUsuario() throws ProjetoException {

	Usuario usuario = (Usuario) usuarioService.obter(this.usuarioSelecionado.getChavePrimaria());
	usuarioService.remover(usuario);
	limparPesquisa();
	Util.exibeMensagemTela(FacesMessage.SEVERITY_INFO, Constantes.PARAMETRO_MSG_SUCESSO_EXCLUSAO,
		this.usuarioSelecionado.toString());
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
    
    private void verificaAtributosSessao() throws NegocioException {
	
	if (getAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_SELECIONADA) != null) {
	    
	    this.usuario = (Usuario) getAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_SELECIONADA);
	    setListaEstado(enderecoService.listarEstado(Boolean.TRUE));
	    carregaAtributosEndereco();
	    carregaAtributosContato();
	    carregaCidadesPorEstado();
	    setAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_SELECIONADA, null);
	    
	} else if (getAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_INSERIDA) != null) {
	    
	    this.usuario = (Usuario) getAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_INSERIDA);
	    setAttributeSession(ATRIBUTO_SESSAO_ENTIDADE_INSERIDA, null);
	    
	    Map<String, Object> filtro = new HashMap<String, Object>();
	    filtro.put(Usuario.ATRIBUTO_NOME, this.usuario.getNome());
	    this.setListaUsuario(usuarioService.listarUsuario(filtro));
		
	} else {
	    
	    this.usuario = (Usuario) usuarioService.criar();
	    this.setListaUsuario(usuarioService.listarUsuario(new HashMap<String, Object>()));
	}
    }
    
    private void carregaAtributosContato() {
	
	this.usuario.setContato(usuarioService.criarUsuarioContato());
    }
    
    private void carregaAtributosEndereco() {

	if (this.usuario.getEndereco() == null) {
	  
	    this.usuario.setEndereco(usuarioService.criarUsuarioEndereco());
	    
	} else {
	    
	    this.setRua(this.usuario.getEndereco().getRua());
	    this.setBairro(this.usuario.getEndereco().getBairro());
	    this.setComplemento(this.usuario.getEndereco().getComplemento());
	    this.setNumero(this.usuario.getEndereco().getNumero());
	    this.setCep(this.usuario.getEndereco().getCep());
	    this.setEstado(this.usuario.getEndereco().getCidade().getEstado());
	    this.setCidade(this.usuario.getEndereco().getCidade());
	}
    }

    private Usuario montarObjeto(Usuario obj) {

	obj.setTenant(((Usuario) getAttributeSession(ATRIBUTO_SESSAO_USUARIO_LOGADO)).getTenant());
	obj.setNome(this.usuario.getNome());
	obj.setSetor(this.usuario.getSetor());
	obj.setEmpresa(this.usuario.getEmpresa());
	obj.setCargo(this.usuario.getCargo());
	obj.setLogin(this.usuario.getLogin());
	obj.setSenha(this.usuario.getSenha());
	obj.setDataNascimento(this.usuario.getDataNascimento());
	obj.setDataAdmissao(this.usuario.getDataAdmissao());
	preencheAtributosEndereco(obj);
	preecheAtributosContato(obj);
	return obj;
    }
    
    private void preencheAtributosEndereco(Usuario obj) {
	
	if (super.estaPreenchido()) { 
	    this.usuario.getEndereco().setDataCadastro(Calendar.getInstance().getTime());
	    this.usuario.getEndereco().setUltimaAlteracao(Calendar.getInstance().getTime());
	    this.usuario.getEndereco().setHabilitado(Boolean.TRUE);
	    this.usuario.getEndereco().setRua(this.getRua());
	    this.usuario.getEndereco().setBairro(this.getBairro());
	    this.usuario.getEndereco().setComplemento(this.getComplemento());
	    this.usuario.getEndereco().setNumero(this.getNumero());
	    this.usuario.getEndereco().setCep(this.getCep());
	    this.usuario.getEndereco().setCidade(this.getCidade());
	    obj.setEndereco(this.usuario.getEndereco());
	} else {
	    obj.setEndereco(null);
	}
    }
    
    private void preecheAtributosContato(Usuario obj) {
	
	if (this.usuario.getContato().estaPreenchido()) {
	    this.usuario.getContato().setDataCadastro(Calendar.getInstance().getTime());
	    this.usuario.getContato().setUltimaAlteracao(Calendar.getInstance().getTime());
	    this.usuario.getContato().setHabilitado(Boolean.TRUE);
	    obj.setContato(this.usuario.getContato());
	} else {
	    obj.setContato(null);
	}
    }

    private void verificaConfirmacaoSenha(Usuario usuario) throws NegocioException {

	if (usuario.getSenha() != null && !usuario.getSenha().equals(this.confirmacaoSenha)) {

	    throw new NegocioException(MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE,
		    Constantes.PARAMETRO_MSG_ERRO_CONFIRMACAO_SENHA));
	}
    }
}
