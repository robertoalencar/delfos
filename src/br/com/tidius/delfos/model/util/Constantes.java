package br.com.tidius.delfos.model.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Roberto Alencar
 * 
 */
public final class Constantes {
    
    /** Constantes criadas para o arquivos de propriedades das mensagens */
    public static Locale LOCALE_PADRAO = new Locale("pt", "BR");
    public static final String ARQUIVO_MENSAGENS = "messages";
    public static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(ARQUIVO_MENSAGENS, LOCALE_PADRAO);

    
    /** Chaves para as mensagens localizadas no arquivo de mensagens.properties */
    public static final String ERRO_ENTIDADE_NAO_ENCONTRADA = "ERRO_ENTIDADE_NAO_ENCONTRADA";
    public static final String ERRO_ENTIDADE_VERSAO_DESATUALIZADA = "ERRO_ENTIDADE_VERSAO_DESATUALIZADA";
    public static final String PARAMETRO_MSG_LOGIN_INCORRETO = "PARAMETRO_MSG_LOGIN_INCORRETO";
    public static final String PARAMETRO_MSG_SUCESSO_INCLUSAO = "PARAMETRO_MSG_SUCESSO_INCLUSAO";
    public static final String PARAMETRO_MSG_SUCESSO_ALTERACAO = "PARAMETRO_MSG_SUCESSO_ALTERACAO";
    public static final String PARAMETRO_MSG_SUCESSO_EXCLUSAO = "PARAMETRO_MSG_SUCESSO_EXCLUSAO";
    public static final String PARAMETRO_MSG_SUCESSO_IMPORTACAO = "PARAMETRO_MSG_SUCESSO_IMPORTACAO";
    public static final String PARAMETRO_MSG_OBJ_DUPLICADO = "PARAMETRO_MSG_OBJ_DUPLICADO";
    public static final String PARAMETRO_MSG_ERRO_CONVERSAO = "PARAMETRO_MSG_ERRO_CONVERSAO";
    public static final String PARAMETRO_MSG_CAMPOS_OBRIGATORIOS = "PARAMETRO_MSG_CAMPOS_OBRIGATORIOS";
    public static final String PARAMETRO_MSG_ERRO_INESPERADO = "PARAMETRO_MSG_ERRO_INESPERADO";
    public static final String PARAMETRO_MSG_ERRO_CONFIRMACAO_SENHA = "PARAMETRO_MSG_ERRO_CONFIRMACAO_SENHA";
    
    
    /** Nome dos arquivos das telas .xhtml */
    public static final String ARQUIVO_XHTML_INDEX = "../home/index.xhtml";
    public static final String ARQUIVO_XHTML_HOME = "../home/home.xhtml";
    public static final String ARQUIVO_XHTML_PESQUISAR_USUARIO = "../usuario/pesquisarUsuario.xhtml";
    public static final String ARQUIVO_XHTML_SALVAR_USUARIO = "../usuario/salvarUsuario.xhtml";
    public static final String ARQUIVO_XHTML_PESQUISAR_SETOR = "../setor/pesquisarSetor.xhtml";
    public static final String ARQUIVO_XHTML_SALVAR_SETOR = "../setor/salvarSetor.xhtml";
    public static final String ARQUIVO_XHTML_PESQUISAR_CARGO = "../cargo/pesquisarCargo.xhtml";
    public static final String ARQUIVO_XHTML_SALVAR_CARGO = "../cargo/salvarCargo.xhtml";
    public static final String ARQUIVO_XHTML_PESQUISAR_EMPRESA = "../empresa/pesquisarEmpresa.xhtml";
    public static final String ARQUIVO_XHTML_SALVAR_EMPRESA = "../empresa/salvarEmpresa.xhtml";
    
}