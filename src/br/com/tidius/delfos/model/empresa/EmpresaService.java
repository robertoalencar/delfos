package br.com.tidius.delfos.model.empresa;

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
import br.com.tidius.delfos.model.usuario.Usuario;
import br.com.tidius.delfos.model.usuario.UsuarioService;
import br.com.tidius.delfos.model.util.BeanFactory;
import br.com.tidius.delfos.model.util.Constantes;
import br.com.tidius.delfos.model.util.MensagemUtil;

/**
 * @author Roberto Alencar
 * 
 */
public class EmpresaService extends Service {

    public static final String BEAN_NAME = "empresaService";

    @Override
    public EntidadeNegocio criar() {
	Empresa empresa = (Empresa) BeanFactory.getBean(Empresa.BEAN_NAME);
	empresa.setContato((EmpresaContato) BeanFactory.getBean(EmpresaContato.BEAN_NAME));
	empresa.setEndereco((EmpresaEndereco) BeanFactory.getBean(EmpresaEndereco.BEAN_NAME));
	return empresa;
    }

    @Override
    public Class<?> getClasseEntidade() {
	return BeanFactory.getClassPorID(Empresa.BEAN_NAME);
    }
    
    public EmpresaEndereco criarEmpresaEndereco() {
	return (EmpresaEndereco) BeanFactory.getBean(EmpresaEndereco.BEAN_NAME);
    }
    
    public EmpresaContato criarEmpresaContato() {
	return (EmpresaContato) BeanFactory.getBean(EmpresaContato.BEAN_NAME);
    }

    @SuppressWarnings("unchecked")
    public List<Empresa> listarEmpresa(Map<String, Object> filtro) throws NegocioException {

	DetachedCriteria criteria = DetachedCriteria.forClass(getClasseEntidade());
	criteria.add(Restrictions.eq(EntidadeNegocio.ATRIBUTO_HABILITADO, Boolean.TRUE));
	criteria.addOrder(Order.asc(Empresa.ATRIBUTO_NOME_FANTASIA));
	
	if (!filtro.isEmpty()) {
	    if ((filtro.get(Empresa.ATRIBUTO_CHAVE_PRIMARIA)) != null && (filtro.get(Empresa.ATRIBUTO_CHAVE_PRIMARIA) != "")) {
		criteria.add(Restrictions.eq(Empresa.ATRIBUTO_CHAVE_PRIMARIA, filtro.get(Empresa.ATRIBUTO_CHAVE_PRIMARIA)));
	    }
	    if ((filtro.get(Empresa.ATRIBUTO_NOME_FANTASIA)) != null && (filtro.get(Empresa.ATRIBUTO_NOME_FANTASIA) != "")) {
		criteria.add(Restrictions.ilike(Empresa.ATRIBUTO_NOME_FANTASIA, "%" + filtro.get(Empresa.ATRIBUTO_NOME_FANTASIA) + "%"));
	    }
	}
	
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public void preInsercao(EntidadeNegocio entidadeNegocio) throws NegocioException {

	verificaCamposObrigatorios((Empresa) entidadeNegocio);
	verificaDuplicidade((Empresa) entidadeNegocio);
    }

    @Override
    public void preAtualizacao(EntidadeNegocio entidadeNegocio) throws NegocioException {

	verificaCamposObrigatorios((Empresa) entidadeNegocio);
	verificaDuplicidade((Empresa) entidadeNegocio);
    }

    public boolean existeRelacionamento(EntidadeNegocio entidadeNegocio) throws NegocioException {
	boolean retorno = Boolean.FALSE;
	
	UsuarioService usuarioService = (UsuarioService) BeanFactory.getBean(UsuarioService.BEAN_NAME);
	Map<String, Object> filtro = new HashMap<String, Object>();
	filtro.put(Usuario.ATRIBUTO_EMPRESA, entidadeNegocio);
	List<Usuario> listaUsuario = usuarioService.listarUsuario(filtro);
	
	if ((listaUsuario != null) && (!listaUsuario.isEmpty())) {
	    retorno = Boolean.TRUE;
	}
	
	return retorno; 
    }

    private void verificaCamposObrigatorios(Empresa empresa) throws NegocioException {
	StringBuilder sb = new StringBuilder();
	String camposObrigatorios = null;
	
	if (empresa.getNomeFantasia() == null || empresa.getNomeFantasia() == "") {
	    sb.append(Empresa.ATRIBUTO_NOME_FANTASIA);
	    sb.append(", ");
	}
	
	camposObrigatorios = sb.toString();

	if (camposObrigatorios.length() > 0) {
	    throw new NegocioException(MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, Constantes.PARAMETRO_MSG_CAMPOS_OBRIGATORIOS,
		    camposObrigatorios.substring(0, sb.toString().length() - 2)));
	}
    }

    @SuppressWarnings("unchecked")
    private void verificaDuplicidade(Empresa empresa) throws NegocioException {

	DetachedCriteria criteria = DetachedCriteria.forClass(getClasseEntidade());
	criteria.add(Restrictions.eq(EntidadeNegocio.ATRIBUTO_HABILITADO, Boolean.TRUE));
	criteria.add(Restrictions.eq(Empresa.ATRIBUTO_NOME_FANTASIA, empresa.getNomeFantasia()).ignoreCase());

	if (empresa.getChavePrimaria() != null && empresa.getChavePrimaria() > 0) {
	    criteria.add(Restrictions.ne(Empresa.ATRIBUTO_CHAVE_PRIMARIA, empresa.getChavePrimaria()));
	}

	Collection<EntidadeNegocio> resultado = getHibernateTemplate().findByCriteria(criteria);
	if (resultado != null && resultado.size() > 0) {
	    Object argumentos[] = { Empresa.BEAN_NAME, "Nome Fantasia", "Nome Fantasia" };
	    throw new NegocioException(MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, Constantes.PARAMETRO_MSG_OBJ_DUPLICADO,
		    argumentos));
	}
    }
    
}