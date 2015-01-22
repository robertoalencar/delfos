package br.com.tidius.delfos.model.usuario;

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
@Table(name = "USUARIO_ENDERECO")
@SequenceGenerator(name = "ID_GENERATOR", sequenceName = "USUARIO_ENDERECO_SEQ", allocationSize = 1)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class UsuarioEndereco extends Endereco implements Serializable {

    private static final long serialVersionUID = 8585444117706408299L;
    
    public static final String BEAN_NAME = "usuarioEndereco";
    
}