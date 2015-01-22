package br.com.tidius.delfos.controller.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.tidius.delfos.model.comum.excecoes.ProjetoException;
import br.com.tidius.delfos.model.empresa.Empresa;
import br.com.tidius.delfos.model.empresa.EmpresaService;
import br.com.tidius.delfos.model.util.BeanFactory;
import br.com.tidius.delfos.model.util.Constantes;
import br.com.tidius.delfos.model.util.MensagemUtil;

/**
 * @author roberto.alencar
 * 
 */
@FacesConverter(value = "empresaConverter", forClass = Empresa.class)
public class EmpresaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

	if (value == null || value.equals("")) {
	    return null;
	} else {
	    try {
		EmpresaService empresaService = (EmpresaService) BeanFactory.getBean(EmpresaService.BEAN_NAME);
		Integer id = Integer.parseInt(value);
		return empresaService.obter(id);
	    } catch (ProjetoException e) {
		Object argumentos[] = { value, component.getId() };
		throw new ConverterException(MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE,
			Constantes.PARAMETRO_MSG_ERRO_CONVERSAO, argumentos));
	    }
	}
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) throws ConverterException {

	if (object == null || object.equals("")) {
	    return "";
	} else {
	    return String.valueOf(((Empresa) object).getChavePrimaria());
	}
    }

}