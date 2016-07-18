package br.ufg.inf.persistencia.modelos;

import java.util.HashMap;

public class ArquivoConfiguracao {

	public static final String AMBIENTE_PRODUCAO = "producao";
	public static final String AMBIENTE_TESTE = "teste";
	private static final String NOME_BANCO_DADOS = "nome-banco-dados";
	private static final String IP_BANCO_DADOS = "ip-banco-dados";

	public HashMap producao;
	public HashMap teste;

	public String getNomeBancoDados(String ambiente) {
		if (ambiente.equalsIgnoreCase(AMBIENTE_PRODUCAO)) {
			return producao.get(NOME_BANCO_DADOS).toString();
		} else {
			return teste.get(NOME_BANCO_DADOS).toString();
		}
	}

	public String getIpBancoDados(String ambiente) {
		if (ambiente.equalsIgnoreCase(AMBIENTE_PRODUCAO)) {
			return producao.get(IP_BANCO_DADOS).toString();
		} else {
			return teste.get(IP_BANCO_DADOS).toString();
		}
	}

}
