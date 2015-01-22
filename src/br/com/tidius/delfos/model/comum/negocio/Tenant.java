package br.com.tidius.delfos.model.comum.negocio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Roberto Alencar
 *
 */
@Entity
@Table(name = "TENANT")
@SequenceGenerator(name = "ID_GENERATOR", sequenceName = "TENANT_SEQ", allocationSize = 1)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Tenant extends EntidadeNegocio implements Serializable {

    private static final long serialVersionUID = -6651903293689245896L;
    
    public static final String BEAN_NAME = "tenant";
    
}