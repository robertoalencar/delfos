package br.com.tidius.delfos.model.usuario;

import java.io.Serializable;
import java.util.Date;

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
import br.com.tidius.delfos.model.empresa.Empresa;

/**
 * @author Roberto Alencar
 * 
 */
@Entity
@Table(name = "USUARIO")
@SequenceGenerator(name = "ID_GENERATOR", sequenceName = "USUARIO_SEQ", allocationSize = 1)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Usuario extends EntidadeNegocio implements Serializable {

    private static final long serialVersionUID = 1168273202465286131L;
    
    public static final String BEAN_NAME = "usuario";
    
    public static final String ATRIBUTO_TENANT = "tenant";
    public static final String ATRIBUTO_EMPRESA = "empresa";
    public static final String ATRIBUTO_CARGO = "cargo";
    public static final String ATRIBUTO_CONTATO = "contato";
    public static final String ATRIBUTO_ENDERECO = "endereco";
    public static final String ATRIBUTO_SETOR = "setor";
    public static final String ATRIBUTO_NOME = "nome";
    public static final String ATRIBUTO_LOGIN = "login";
    public static final String ATRIBUTO_SENHA = "senha";
    public static final String ATRIBUTO_DATA_NASCIMENTO = "dataNascimento";
    public static final String ATRIBUTO_DATA_ADMISSAO  = "dataAdmissao"; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TENANT_ID")
    private Tenant tenant;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMPRESA_ID", insertable = true, updatable = true)
    private Empresa empresa;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CARGO_ID", insertable = true, updatable = true)
    private Cargo cargo;

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "USUARIO_CONTATO_ID", insertable = true, updatable = true)
    private UsuarioContato contato;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "USUARIO_ENDERECO_ID", insertable = true, updatable = true)
    private UsuarioEndereco endereco;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SETOR_ID", insertable = true, updatable = true)
    private Setor setor;

    @Column(name = "NOME")
    private String nome;
    
    @Column(name = "LOGIN")
    private String login;
    
    @Column(name = "SENHA")
    private String senha;
    
    @Column(name = "DT_NASCIMENTO")
    private Date dataNascimento;
    
    @Column(name = "DT_ADMISSAO")
    private Date dataAdmissao;
    
//    @OneToMany(fetch = FetchType.EAGER)
//    @Cascade(CascadeType.SAVE_UPDATE)
//    @JoinColumn(name="USUARIO_ID")
//    private List<UsuarioTelefone> listaUsuarioTelefone;
    
    
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
    public Empresa getEmpresa() {
        return empresa;
    }
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    public Cargo getCargo() {
        return cargo;
    }
    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
    public UsuarioContato getContato() {
        return contato;
    }
    public void setContato(UsuarioContato contato) {
        this.contato = contato;
    }
    public UsuarioEndereco getEndereco() {
        return endereco;
    }
    public void setEndereco(UsuarioEndereco endereco) {
        this.endereco = endereco;
    }
    public Setor getSetor() {
        return setor;
    }
    public void setSetor(Setor setor) {
        this.setor = setor;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
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
    public Date getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public Date getDataAdmissao() {
        return dataAdmissao;
    }
    public void setDataAdmissao(Date dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }
    
    
    @Override
    public String toString() {
	return nome;
    }
}