package br.com.tidius.delfos.model.usuario;

import java.util.Collection;
import java.util.HashMap;
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
public class SetorService extends Service {

    public static final String BEAN_NAME = "setorService";

    @Override
    public EntidadeNegocio criar() {
	return (Setor) BeanFactory.getBean(Setor.BEAN_NAME);
    }

    @Override
    public Class<?> getClasseEntidade() {
	return BeanFactory.getClassPorID(Setor.BEAN_NAME);
    }

    @SuppressWarnings("unchecked")
    public List<Setor> listarSetor(Map<String, Object> filtro) throws NegocioException {

	DetachedCriteria criteria = DetachedCriteria.forClass(getClasseEntidade());
	criteria.add(Restrictions.eq(EntidadeNegocio.ATRIBUTO_HABILITADO, Boolean.TRUE));
	criteria.addOrder(Order.asc(Setor.ATRIBUTO_DESCRICAO));

	if (!filtro.isEmpty()) {
	    if ((filtro.get(Setor.ATRIBUTO_CHAVE_PRIMARIA)) != null && (filtro.get(Setor.ATRIBUTO_CHAVE_PRIMARIA) != "")) {
		criteria.add(Restrictions.eq(Setor.ATRIBUTO_CHAVE_PRIMARIA, filtro.get(Setor.ATRIBUTO_CHAVE_PRIMARIA)));
	    }
	    if ((filtro.get(Setor.ATRIBUTO_DESCRICAO)) != null && (filtro.get(Setor.ATRIBUTO_DESCRICAO) != "")) {
		criteria.add(Restrictions.ilike(Setor.ATRIBUTO_DESCRICAO, "%" + filtro.get(Setor.ATRIBUTO_DESCRICAO) + "%"));
	    }
	}

	return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public void preInsercao(EntidadeNegocio entidadeNegocio) throws NegocioException {

	verificaCamposObrigatorios((Setor) entidadeNegocio);
	verificaDuplicidade((Setor) entidadeNegocio);
    }

    @Override
    public void preAtualizacao(EntidadeNegocio entidadeNegocio) throws NegocioException {

	verificaCamposObrigatorios((Setor) entidadeNegocio);
	verificaDuplicidade((Setor) entidadeNegocio);
    }
    
    public boolean existeRelacionamento(EntidadeNegocio entidadeNegocio) throws NegocioException {
	boolean retorno = Boolean.FALSE;
	
	UsuarioService usuarioService = (UsuarioService) BeanFactory.getBean(UsuarioService.BEAN_NAME);
	Map<String, Object> filtro = new HashMap<String, Object>();
	filtro.put(Usuario.ATRIBUTO_SETOR, entidadeNegocio);
	List<Usuario> listaUsuario = usuarioService.listarUsuario(filtro);
	
	if ((listaUsuario != null) && (!listaUsuario.isEmpty())) {
	    retorno = Boolean.TRUE;
	}
	
	return retorno; 
    }

    private void verificaCamposObrigatorios(Setor setor) throws NegocioException {
	StringBuilder sb = new StringBuilder();
	String camposObrigatorios = null;

	if (setor.getDescricao() == null || setor.getDescricao().length() == 0) {
	    sb.append(Setor.ATRIBUTO_DESCRICAO);
	    sb.append(", ");
	}

	camposObrigatorios = sb.toString();

	if (camposObrigatorios.length() > 0) {
	    throw new NegocioException(MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, Constantes.PARAMETRO_MSG_CAMPOS_OBRIGATORIOS,
		    camposObrigatorios.substring(0, sb.toString().length() - 2)));
	}
    }

    @SuppressWarnings("unchecked")
    private void verificaDuplicidade(Setor setor) throws NegocioException {

	DetachedCriteria criteria = DetachedCriteria.forClass(getClasseEntidade());
	criteria.add(Restrictions.eq(EntidadeNegocio.ATRIBUTO_HABILITADO, Boolean.TRUE));
	criteria.add(Restrictions.eq(Setor.ATRIBUTO_DESCRICAO, setor.getDescricao()).ignoreCase());

	if (setor.getChavePrimaria() != null && setor.getChavePrimaria() > 0) {
	    criteria.add(Restrictions.ne(Setor.ATRIBUTO_CHAVE_PRIMARIA, setor.getChavePrimaria()));
	}

	Collection<EntidadeNegocio> resultado = getHibernateTemplate().findByCriteria(criteria);
	if (resultado != null && resultado.size() > 0) {
	    Object argumentos[] = { Setor.BEAN_NAME, "Descrição", "Descrição" };
	    throw new NegocioException(MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, Constantes.PARAMETRO_MSG_OBJ_DUPLICADO,
		    argumentos));
	}
    }
}