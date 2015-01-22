package br.com.tidius.delfos.model.comum.endereco;

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

import br.com.tidius.delfos.model.comum.negocio.EntidadeNegocio;

@Entity
@Table(name = "CIDADE")
@SequenceGenerator(name = "ID_GENERATOR", sequenceName = "CIDADE_SEQ", allocationSize = 1)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Cidade extends EntidadeNegocio implements Serializable {

    private static final long serialVersionUID = -1714107194260337149L;

    public static final String BEAN_NAME = "cidade";
    public static final String ATRIBUTO_ESTADO = "estado";
    public static final String ATRIBUTO_NOME = "nome";
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ESTADO_ID", insertable = true, updatable = true)
    private Estado estado;

    @Column(name = "NOME")
    private String nome;

    
    /* -------------------
     * GETTERS AND SETTERS
     * -------------------
     */
    
    
    public Estado getEstado() {
	return estado;
    }
    public void setEstado(Estado estado) {
	this.estado = estado;
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