package br.com.tidius.delfos.model.empresa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import br.com.tidius.delfos.model.comum.negocio.EntidadeNegocio;
import br.com.tidius.delfos.model.comum.negocio.Tenant;

/**
 * @author Roberto Alencar
 * 
 */
@Entity
@Table(name = "EMPRESA")
@SequenceGenerator(name = "ID_GENERATOR", sequenceName = "EMPRESA_SEQ", allocationSize = 1)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Empresa extends EntidadeNegocio implements Serializable {

    private static final long serialVersionUID = 6996076758844938177L;

    public static final String BEAN_NAME = "empresa";

    public static final String ATRIBUTO_TENANT = "tenant";
    public static final String ATRIBUTO_EMPRESA_PRINCIPAL = "empresaPrincipal";
    public static final String ATRIBUTO_EMPRESA_CONTATO = "contato";
    public static final String ATRIBUTO_EMPRESA_ENDERECO = "endereco";
    public static final String ATRIBUTO_RAZAO_SOCIAL = "razaoSocial";
    public static final String ATRIBUTO_NOME_FANTASIA = "nomeFantasia";
    public static final String ATRIBUTO_CNPJ = "cnpj";
    public static final String ATRIBUTO_INSC_ESTADUAL = "inscricaoEstadual";
    public static final String ATRIBUTO_HOME_PAGE = "homePage";
    public static final String ATRIBUTO_DADOS_ADICIONAIS = "dadosAdicionais";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TENANT_ID")
    private Tenant tenant;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMPRESA_PRINCIPAL_ID", insertable = true, updatable = true)
    private Empresa empresaPrincipal;

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "EMPRESA_CONTATO_ID", insertable = true, updatable = true)
    private EmpresaContato contato;

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "EMPRESA_ENDERECO_ID", insertable = true, updatable = true)
    private EmpresaEndereco endereco;
    
    @Column(name = "RAZAO_SOCIAL")
    private String razaoSocial;
    
    @Column(name = "NOME_FANTASIA")
    private String nomeFantasia;

    @Column(name = "CNPJ")
    private String cnpj;

    @Column(name = "INSC_ESTADUAL")
    private String inscricaoEstadual;

    @Column(name = "HOME_PAGE")
    private String homePage;

    @Column(name = "DADOS_ADICIONAIS")
    private String dadosAdicionais;

    
    /* -------------------
     * GETTERS AND SETTERS
     * -------------------
     */

    
    public Tenant getTenant() {
        return tenant;
    }
    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
    public Empresa getEmpresaPrincipal() {
	return empresaPrincipal;
    }
    public void setEmpresaPrincipal(Empresa empresaPrincipal) {
	this.empresaPrincipal = empresaPrincipal;
    }
    public EmpresaContato getContato() {
	return contato;
    }
    public void setContato(EmpresaContato contato) {
	this.contato = contato;
    }
    public String getNomeFantasia() {
	return nomeFantasia;
    }
    public void setNomeFantasia(String nomeFantasia) {
	this.nomeFantasia = nomeFantasia;
    }
    public String getRazaoSocial() {
	return razaoSocial;
    }
    public void setRazaoSocial(String razaoSocial) {
	this.razaoSocial = razaoSocial;
    }
    public String getCnpj() {
	return cnpj;
    }
    public void setCnpj(String cnpj) {
	this.cnpj = cnpj;
    }
    public String getInscricaoEstadual() {
	return inscricaoEstadual;
    }
    public void setInscricaoEstadual(String inscricaoEstadual) {
	this.inscricaoEstadual = inscricaoEstadual;
    }
    public String getHomePage() {
	return homePage;
    }
    public void setHomePage(String homePage) {
	this.homePage = homePage;
    }
    public String getDadosAdicionais() {
	return dadosAdicionais;
    }
    public void setDadosAdicionais(String dadosAdicionais) {
	this.dadosAdicionais = dadosAdicionais;
    }
    public EmpresaEndereco getEndereco() {
        return endereco;
    }
    public void setEndereco(EmpresaEndereco endereco) {
        this.endereco = endereco;
    }
 
    
    @Override
    public String toString() {
	return nomeFantasia;
    }
}