package br.com.tidius.delfos.model.comum.negocio;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.StringUtils;


/**
 * @author Roberto Alencar
 *
 */
@MappedSuperclass
public abstract class EntidadeContato extends EntidadeNegocio {

    public static final String ATRIBUTO_FONE_PESSOAL = "fonePessoal";
    public static final String ATRIBUTO_FONE_COMERCIAL = "foneComercial";
    public static final String ATRIBUTO_FONE_CELULAR = "foneCelular";
    public static final String ATRIBUTO_FONE_OUTROS = "foneOutros";
    public static final String ATRIBUTO_EMAIL_PRINCIPAL = "emailPrincipal";
    public static final String ATRIBUTO_EMAIL_SECUNDARIO = "emailSecundario";
    
    @Column(name = "FONE_PESSOAL")
    private String fonePessoal;
    
    @Column(name = "FONE_COMERCIAL")
    private String foneComercial;
    
    @Column(name = "FONE_CELULAR")
    private String foneCelular;
    
    @Column(name = "FONE_OUTROS")
    private String foneOutros;
    
    @Column(name = "EMAIL_PRINCIPAL")
    private String emailPrincipal;
    
    @Column(name = "EMAIL_SECUNDARIO")
    private String emailSecundario;
    
    
    public String getFonePessoal() {
        return fonePessoal;
    }
    public void setFonePessoal(String fonePessoal) {
        this.fonePessoal = fonePessoal;
    }
    public String getFoneComercial() {
        return foneComercial;
    }
    public void setFoneComercial(String foneComercial) {
        this.foneComercial = foneComercial;
    }
    public String getFoneCelular() {
        return foneCelular;
    }
    public void setFoneCelular(String foneCelular) {
        this.foneCelular = foneCelular;
    }
    public String getFoneOutros() {
        return foneOutros;
    }
    public void setFoneOutros(String foneOutros) {
        this.foneOutros = foneOutros;
    }
    public String getEmailPrincipal() {
        return emailPrincipal;
    }
    public void setEmailPrincipal(String emailPrincipal) {
        this.emailPrincipal = emailPrincipal;
    }
    public String getEmailSecundario() {
        return emailSecundario;
    }
    public void setEmailSecundario(String emailSecundario) {
        this.emailSecundario = emailSecundario;
    }
    
    
    public boolean estaPreenchido() {
	
	boolean estaPreenchida = false;
	
	if (StringUtils.isNotEmpty(this.getFonePessoal()) || StringUtils.isNotEmpty(this.getFoneComercial())
		|| StringUtils.isNotEmpty(this.getFoneCelular()) || StringUtils.isNotEmpty(this.getFoneOutros())
		|| StringUtils.isNotEmpty(this.getEmailPrincipal())
		|| StringUtils.isNotEmpty(this.getEmailSecundario())) {
	    
	    estaPreenchida = Boolean.TRUE;
	}
	
	return estaPreenchida;
    }
    
}