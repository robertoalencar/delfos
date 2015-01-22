package br.com.tidius.delfos.model.comum.endereco;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.tidius.delfos.model.comum.negocio.EntidadeNegocio;

@Entity
@Table(name = "ESTADO")
@SequenceGenerator(name = "ID_GENERATOR", sequenceName = "ESTADO_SEQ", allocationSize = 1)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Estado extends EntidadeNegocio implements Serializable {

    private static final long serialVersionUID = 8254812628842263439L;

    public static final String BEAN_NAME = "estado";
    public static final String ATRIBUTO_SIGLA = "sigla";
    public static final String ATRIBUTO_NOME = "nome";
    
    @Column(name = "SIGLA")
    private String sigla;

    @Column(name = "NOME")
    private String nome;

    
    /* -------------------
     * GETTERS AND SETTERS
     * -------------------
     */
    
    
    public String getSigla() {
	return sigla;
    }
    public void setSigla(String sigla) {
	this.sigla = sigla;
    }
    public String getNome() {
	return nome;
    }
    public void setNome(String nome) {
	this.nome = nome;
    }

    
    @Override
    public String toString() {
	return nome;
    }
}