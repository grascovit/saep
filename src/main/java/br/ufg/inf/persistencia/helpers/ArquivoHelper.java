package br.ufg.inf.persistencia.helpers;

import br.ufg.inf.persistencia.modelos.ArquivoConfiguracao;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ArquivoHelper {

	private static final String CAMINHO_ARQUIVO_AMBIENTE = "ambiente.yml";

	private String ambiente;
	private ArquivoConfiguracao configuracoesAmbiente;

	public ArquivoHelper(String ambiente) {
		this.ambiente = ambiente;
		try {
			Yaml leitorYaml = new Yaml(new Constructor(ArquivoConfiguracao.class));
			configuracoesAmbiente = (ArquivoConfiguracao) leitorYaml.load(new FileInputStream(new File(CAMINHO_ARQUIVO_AMBIENTE)));
		} catch (FileNotFoundException e) {
			System.out.println("O arquivo contendo as variáveis de ambiente (" + ambiente + ") não foi encontrado na raíz do projeto");
		}
	}

	public String obtenhaNomeBancoDados() {
		return configuracoesAmbiente.getNomeBancoDados(ambiente);
	}

	public String obtenhaIpBancoDados() {
		return configuracoesAmbiente.getIpBancoDados(ambiente);
	}

}
