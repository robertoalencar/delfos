package br.com.tidius.delfos.model.usuario;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.tidius.delfos.model.comum.excecoes.NegocioException;
import br.com.tidius.delfos.model.comum.negocio.EntidadeNegocio;
import br.com.tidius.delfos.model.comum.negocio.Service;
import br.com.tidius.delfos.model.util.BeanFactory;
import br.com.tidius.delfos.model.util.Constantes;
import br.com.tidius.delfos.model.util.MensagemUtil;

/**
 * @author Roberto Alencar
 * 
 */
public class UsuarioService extends Service {

    public static final String BEAN_NAME = "usuarioService";

    @Override
    public EntidadeNegocio criar() {
	Usuario usuario = (Usuario) BeanFactory.getBean(Usuario.BEAN_NAME);
	usuario.setContato((UsuarioContato) BeanFactory.getBean(UsuarioContato.BEAN_NAME));
	usuario.setEndereco((UsuarioEndereco) BeanFactory.getBean(UsuarioEndereco.BEAN_NAME));
	return usuario;
    }

    @Override
    public Class<?> getClasseEntidade() {
	return BeanFactory.getClassPorID(Usuario.BEAN_NAME);
    }
    
    public UsuarioEndereco criarUsuarioEndereco() {
	return (UsuarioEndereco) BeanFactory.getBean(UsuarioEndereco.BEAN_NAME);
    }
    
    public UsuarioContato criarUsuarioContato() {
	return (UsuarioContato) BeanFactory.getBean(UsuarioContato.BEAN_NAME);
    }
    
    @SuppressWarnings("unchecked")
    public List<Usuario> listarUsuario(Map<String, Object> filtro) throws NegocioException {

	DetachedCriteria criteria = DetachedCriteria.forClass(getClasseEntidade());
	criteria.add(Restrictions.eq(EntidadeNegocio.ATRIBUTO_HABILITADO, Boolean.TRUE));
	criteria.addOrder(Order.asc(Usuario.ATRIBUTO_NOME));

	if (!filtro.isEmpty()) {
	    if ((filtro.get(Usuario.ATRIBUTO_CHAVE_PRIMARIA)) != null && (filtro.get(Usuario.ATRIBUTO_CHAVE_PRIMARIA) != "")) {
		criteria.add(Restrictions.eq(Usuario.ATRIBUTO_CHAVE_PRIMARIA, filtro.get(Usuario.ATRIBUTO_CHAVE_PRIMARIA)));
	    }
	    if (filtro.get(Usuario.ATRIBUTO_EMPRESA) != null) {
		criteria.add(Restrictions.eq(Usuario.ATRIBUTO_EMPRESA, filtro.get(Usuario.ATRIBUTO_EMPRESA)));
	    }
	    if ((filtro.get(Usuario.ATRIBUTO_NOME)) != null && (filtro.get(Usuario.ATRIBUTO_NOME) != "")) {
		criteria.add(Restrictions.ilike(Usuario.ATRIBUTO_NOME, "%" + filtro.get(Usuario.ATRIBUTO_NOME) + "%"));
	    }
	    if (filtro.get(Usuario.ATRIBUTO_SETOR) != null) {
		criteria.add(Restrictions.eq(Usuario.ATRIBUTO_SETOR, filtro.get(Usuario.ATRIBUTO_SETOR)));
	    }
	    if (filtro.get(Usuario.ATRIBUTO_CARGO) != null) {
		criteria.add(Restrictions.eq(Usuario.ATRIBUTO_CARGO, filtro.get(Usuario.ATRIBUTO_CARGO)));
	    }
	}

	return getHibernateTemplate().findByCriteria(criteria);
    }
    
    @SuppressWarnings("unchecked")
    public Usuario loginSistema(String login, String senha) throws NegocioException {

	Usuario usuario = null;

	DetachedCriteria criteria = DetachedCriteria.forClass(getClasseEntidade());
	criteria.add(Restrictions.eq(Usuario.ATRIBUTO_LOGIN, login));
	criteria.add(Restrictions.eq(Usuario.ATRIBUTO_SENHA, senha));
	List<EntidadeNegocio> lista = getHibernateTemplate().findByCriteria(criteria);

	if ((lista != null) && (!lista.isEmpty())) {
	    usuario = (Usuario) lista.get(0);
	}

	return usuario;
    }

    @Override
    public void preInsercao(EntidadeNegocio entidadeNegocio) throws NegocioException {

	verificaCamposObrigatorios((Usuario) entidadeNegocio);
	verificaDuplicidade((Usuario) entidadeNegocio);
    }

    @Override
    public void preAtualizacao(EntidadeNegocio entidadeNegocio) throws NegocioException {

	verificaCamposObrigatorios((Usuario) entidadeNegocio);
	verificaDuplicidade((Usuario) entidadeNegocio);
    }

    private void verificaCamposObrigatorios(Usuario usuario) throws NegocioException {
	StringBuilder sb = new StringBuilder();
	String camposObrigatorios = null;
	
	if (usuario.getEmpresa() == null) {
	    sb.append(Usuario.ATRIBUTO_EMPRESA);
	    sb.append(", ");
	}
	
	if (usuario.getNome() == null || usuario.getNome() == "") {
	    sb.append(Usuario.ATRIBUTO_NOME);
	    sb.append(", ");
	}
	
	if (usuario.getLogin() == null || usuario.getLogin() == "") {
	    sb.append(Usuario.ATRIBUTO_LOGIN);
	    sb.append(", ");
	}
	
	if (usuario.getSenha() == null || usuario.getSenha() == "") {
	    sb.append(Usuario.ATRIBUTO_SENHA);
	    sb.append(", ");
	}

	camposObrigatorios = sb.toString();

	if (camposObrigatorios.length() > 0) {
	    throw new NegocioException(MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, Constantes.PARAMETRO_MSG_CAMPOS_OBRIGATORIOS,
		    camposObrigatorios.substring(0, sb.toString().length() - 2)));
	}
    }

    @SuppressWarnings("unchecked")
    private void verificaDuplicidade(Usuario usuario) throws NegocioException {

	DetachedCriteria criteria = DetachedCriteria.forClass(getClasseEntidade());
	criteria.add(Restrictions.eq(EntidadeNegocio.ATRIBUTO_HABILITADO, Boolean.TRUE));
	criteria.add(Restrictions.eq(Usuario.ATRIBUTO_LOGIN, usuario.getLogin()).ignoreCase());

	if (usuario.getChavePrimaria() != null && usuario.getChavePrimaria() > 0) {
	    criteria.add(Restrictions.ne(Usuario.ATRIBUTO_CHAVE_PRIMARIA, usuario.getChavePrimaria()));
	}

	Collection<EntidadeNegocio> resultado = getHibernateTemplate().findByCriteria(criteria);
	if (resultado != null && resultado.size() > 0) {
	    Object argumentos[] = { Usuario.BEAN_NAME, "Login", "Login" };
	    throw new NegocioException(MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, Constantes.PARAMETRO_MSG_OBJ_DUPLICADO,
		    argumentos));
	}
    }

}