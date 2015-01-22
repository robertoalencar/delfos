package br.com.tidius.delfos.controller.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.tidius.delfos.model.comum.excecoes.ProjetoException;
import br.com.tidius.delfos.model.usuario.Cargo;
import br.com.tidius.delfos.model.usuario.CargoService;
import br.com.tidius.delfos.model.util.BeanFactory;
import br.com.tidius.delfos.model.util.Constantes;
import br.com.tidius.delfos.model.util.MensagemUtil;

/**
 * @author roberto.alencar
 * 
 */
@FacesConverter(value = "cargoConverter", forClass = Cargo.class)
public class CargoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

	if (value == null || value.equals("")) {
	    return null;
	} else {
	    try {
		CargoService cargoService = (CargoService) BeanFactory.getBean(CargoService.BEAN_NAME);
		Integer id = Integer.parseInt(value);
		return cargoService.obter(id);
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
	    return String.valueOf(((Cargo) object).getChavePrimaria());
	}
    }

}