package br.com.tidius.delfos.model.usuario;

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
@Table(name = "USUARIO_CONTATO")
@SequenceGenerator(name = "ID_GENERATOR", sequenceName = "USUARIO_CONTATO_SEQ", allocationSize = 1)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class UsuarioContato extends EntidadeContato implements Serializable {

    private static final long serialVersionUID = -1905338966736809018L;

    public static final String BEAN_NAME = "usuarioContato";
    
}