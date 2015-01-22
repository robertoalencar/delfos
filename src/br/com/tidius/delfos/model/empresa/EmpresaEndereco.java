package br.com.tidius.delfos.model.empresa;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.tidius.delfos.model.comum.endereco.Endereco;

/**
 * @author Roberto Alencar
 *
 */
@Entity
@Table(name = "EMPRESA_ENDERECO")
@SequenceGenerator(name = "ID_GENERATOR", sequenceName = "EMPRESA_ENDERECO_SEQ", allocationSize = 1)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class EmpresaEndereco extends Endereco implements Serializable {

    private static final long serialVersionUID = -186214205181368101L;
    
    public static final String BEAN_NAME = "empresaEndereco";
    
}