package br.com.tidius.delfos.model.usuario;

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
import br.com.tidius.delfos.model.comum.negocio.Tenant;

/**
 * @author Roberto Alencar
 * 
 */
@Entity
@Table(name = "CARGO")
@SequenceGenerator(name = "ID_GENERATOR", sequenceName = "CARGO_SEQ", allocationSize = 1)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Cargo extends EntidadeNegocio implements Serializable {

    private static final long serialVersionUID = -8122950012128758625L;
    
    public static final String BEAN_NAME = "cargo";
    
    public static final String ATRIBUTO_TENANT = "tenant";
    public static final String ATRIBUTO_DESCRICAO = "descricao";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TENANT_ID")
    private Tenant tenant;
    
    @Column(name = "DESCRICAO")
    private String descricao;

    
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
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    
    @Override
    public String toString() {
	return descricao;
    }
}