package br.com.tidius.delfos.controller.util;

import java.io.IOException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import br.com.tidius.delfos.model.comum.endereco.Cidade;
import br.com.tidius.delfos.model.comum.endereco.EnderecoService;
import br.com.tidius.delfos.model.comum.endereco.Estado;
import br.com.tidius.delfos.model.comum.excecoes.ProjetoException;
import br.com.tidius.delfos.model.util.BeanFactory;
import br.com.tidius.delfos.model.util.Constantes;
import br.com.tidius.delfos.model.util.Util;

/**
 * @author Roberto Alencar
 * 
 */
public class ComumController {

    protected static final String ATRIBUTO_SESSAO_USUARIO_LOGADO = "usuarioLogado";
    protected static final String ATRIBUTO_SESSAO_ENTIDADE_SELECIONADA = "entidadeSelecionada";
    protected static final String ATRIBUTO_SESSAO_ENTIDADE_INSERIDA = "entidadeInserida";

    private EnderecoService enderecoService;

    // Atributos de endereco
    private List<Estado> listaEstado;
    private List<Cidade> listaCidade;
    private Estado estado;
    private Cidade cidade;
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;

    // Getters and Setters

    public List<Estado> getListaEstado() {
	return listaEstado;
    }

    public void setListaEstado(List<Estado> listaEstado) {
	this.listaEstado = listaEstado;
    }

    public List<Cidade> getListaCidade() {
	return listaCidade;
    }

    public void setListaCidade(List<Cidade> listaCidade) {
	this.listaCidade = listaCidade;
    }

    public Estado getEstado() {
	return estado;
    }

    public void setEstado(Estado estado) {
	this.estado = estado;
    }

    public Cidade getCidade() {
	return cidade;
    }

    public void setCidade(Cidade cidade) {
	this.cidade = cidade;
    }

    public String getRua() {
	return rua;
    }

    public void setRua(String rua) {
	this.rua = rua;
    }

    public String getNumero() {
	return numero;
    }

    public void setNumero(String numero) {
	this.numero = numero;
    }

    public String getComplemento() {
	return complemento;
    }

    public void setComplemento(String complemento) {
	this.complemento = complemento;
    }

    public String getBairro() {
	return bairro;
    }

    public void setBairro(String bairro) {
	this.bairro = bairro;
    }

    public String getCep() {
	return cep;
    }

    public void setCep(String cep) {
	this.cep = cep;
    }

    public void limparCamposEndereco() throws ProjetoException {

	this.enderecoService = (EnderecoService) BeanFactory.getBean(EnderecoService.BEAN_NAME);
	setListaEstado(enderecoService.listarEstado(Boolean.TRUE));
	setListaCidade(null);
	setEstado(null);
	setCidade(null);
	setRua(null);
	setNumero(null);
	setComplemento(null);
	setBairro(null);
	setCep(null);
    }

    protected boolean estaPreenchido() {

	boolean estaPreenchida = false;

	if (StringUtils.isNotEmpty(this.getRua()) || StringUtils.isNotEmpty(this.getNumero())
		|| StringUtils.isNotEmpty(this.getComplemento()) || StringUtils.isNotEmpty(this.getBairro())
		|| StringUtils.isNotEmpty(this.getCep()) || this.getCidade() != null) {

	    estaPreenchida = Boolean.TRUE;
	}

	return estaPreenchida;
    }

    protected void redirecionarTela(String tela) {

	try {
	    FacesContext.getCurrentInstance().getExternalContext().redirect(tela);
	} catch (IOException e) {
	    e.printStackTrace();
	    Util.exibeMensagemTela(FacesMessage.SEVERITY_FATAL, Constantes.PARAMETRO_MSG_ERRO_INESPERADO);
	}
    }

    protected Object getAttributeSession(String id) {

	HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	return session.getAttribute(id);
    }

    protected void setAttributeSession(String id, Object param) {

	HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	session.setAttribute(id, param);
    }

    protected void verificaUsuarioLogado() {

	if (getAttributeSession(ATRIBUTO_SESSAO_USUARIO_LOGADO) == null) {
	    redirecionarTela(Constantes.ARQUIVO_XHTML_INDEX);
	}
    }
}