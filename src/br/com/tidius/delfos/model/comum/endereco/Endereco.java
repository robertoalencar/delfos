package br.com.tidius.delfos.model.comum.endereco;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import br.com.tidius.delfos.model.comum.negocio.EntidadeNegocio;

@MappedSuperclass
public class Endereco extends EntidadeNegocio implements Serializable {

    private static final long serialVersionUID = 3402960927713731095L;

    public static final String BEAN_NAME = "endereco";
    
    public static final String ATRIBUTO_CIDADE = "cidade";
    public static final String ATRIBUTO_RUA = "rua";
    public static final String ATRIBUTO_NUMERO = "numero";
    public static final String ATRIBUTO_COMPLEMENTO = "complemento";
    public static final String ATRIBUTO_BAIRRO = "bairro"; 
    public static final String ATRIBUTO_CEP = "cep"; 
    
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CIDADE_ID", insertable = true, updatable = true)
    private Cidade cidade;

    @Column(name = "RUA")
    private String rua;

    @Column(name = "NUMERO")
    private String numero;

    @Column(name = "COMPLEMENTO")
    private String complemento;

    @Column(name = "BAIRRO")
    private String bairro;

    @Column(name = "CEP")
    private String cep;

    
    /* -------------------
     * GETTERS AND SETTERS
     * -------------------
     */

    public Cidade getCidade() {
        return cidade;
    }
    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
    public String getRua() {
        return rua;
    }
    public void setRua(String rua) {
        this.rua = rua;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public String getComplemento() {
        return complemento;
    }
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
    public String getBairro() {
        return bairro;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }


    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();

	if (rua != null) {
	    builder.append("Rua ");
	    builder.append(rua);

	    if (numero != null) {
		builder.append(", No. ");
		builder.append(numero);
	    }
	    if (complemento != null) {
		builder.append(", ");
		builder.append(complemento);
	    }
	    if (bairro != null) {
		builder.append(", ");
		builder.append(bairro);
	    }
	    if (cidade != null) {
		builder.append(", ");
		builder.append(cidade.getNome());
		builder.append("-");
		builder.append(cidade.getEstado().getSigla());
	    }
	}

	return builder.toString();
    }
    
}
