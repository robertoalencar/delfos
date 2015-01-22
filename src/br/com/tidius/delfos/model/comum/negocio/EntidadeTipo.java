package br.com.tidius.delfos.model.comum.negocio;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author Roberto Alencar
 * 
 */
@MappedSuperclass
public abstract class EntidadeTipo extends EntidadeNegocio {

    public static final String ATRIBUTO_DESCRICAO = "descricao";
    public static final String LABEL_DESCRICAO = "Descrição";

    @Column(name = "DESCRICAO")
    private String descricao;

    public String getDescricao() {
	return descricao;
    }

    public void setDescricao(String descricao) {
	this.descricao = descricao;
    }

    public String toString() {
	return descricao;
    }

}