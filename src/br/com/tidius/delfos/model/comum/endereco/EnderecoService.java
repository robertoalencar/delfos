package br.com.tidius.delfos.model.comum.endereco;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.tidius.delfos.model.comum.excecoes.NegocioException;
import br.com.tidius.delfos.model.comum.negocio.EntidadeNegocio;
import br.com.tidius.delfos.model.comum.negocio.Service;
import br.com.tidius.delfos.model.util.BeanFactory;

/**
 * @author Roberto Alencar
 * 
 */
public class EnderecoService extends Service {
    
    public static final String BEAN_NAME = "enderecoService";

    @Override
    public EntidadeNegocio criar() {
	return (EntidadeNegocio) BeanFactory.getBean(Endereco.BEAN_NAME);
    }

    @Override
    public Class<?> getClasseEntidade() {
	return BeanFactory.getClassPorID(Endereco.BEAN_NAME);
    }
    
    public EntidadeNegocio criarCidade() {
	return (EntidadeNegocio) BeanFactory.getBean(Cidade.BEAN_NAME);
    }

    public Class<?> getClasseEntidadeCidade() {
	return BeanFactory.getClassPorID(Cidade.BEAN_NAME);
    }

    public EntidadeNegocio criarEstado() {
	return (EntidadeNegocio) BeanFactory.getBean(Estado.BEAN_NAME);
    }

    public Class<?> getClasseEntidadeEstado() {
	return BeanFactory.getClassPorID(Estado.BEAN_NAME);
    }

    @SuppressWarnings("unchecked")
    public List<Estado> listarEstado(Boolean habilitado) throws NegocioException {

	DetachedCriteria criteria = DetachedCriteria.forClass(getClasseEntidadeEstado());
	criteria.add(Restrictions.eq(EntidadeNegocio.ATRIBUTO_HABILITADO, habilitado));
	criteria.addOrder(Order.asc(Estado.ATRIBUTO_NOME));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    public List<Cidade> listarCidade(Map<String, Object> filtro) throws NegocioException {

	DetachedCriteria criteria = DetachedCriteria.forClass(getClasseEntidadeCidade());
	criteria.add(Restrictions.eq(EntidadeNegocio.ATRIBUTO_HABILITADO, Boolean.TRUE));
	criteria.add(Restrictions.eq(Cidade.ATRIBUTO_ESTADO, filtro.get(Cidade.ATRIBUTO_ESTADO)));
	criteria.addOrder(Order.asc(Cidade.ATRIBUTO_NOME));
	return getHibernateTemplate().findByCriteria(criteria);
    }
}