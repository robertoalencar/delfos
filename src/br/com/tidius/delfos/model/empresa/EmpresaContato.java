package br.com.tidius.delfos.model.empresa;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.tidius.delfos.model.comum.negocio.EntidadeContato;

/**
 * @author Roberto Alencar
 *
 */
@Entity
@Table(name = "EMPRESA_CONTATO")
@SequenceGenerator(name = "ID_GENERATOR", sequenceName = "EMPRESA_CONTATO_SEQ", allocationSize = 1)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class EmpresaContato extends EntidadeContato implements Serializable {

    private static final long serialVersionUID = -8688623745771840573L;
    
    public static final String BEAN_NAME = "empresaContato";
    
}