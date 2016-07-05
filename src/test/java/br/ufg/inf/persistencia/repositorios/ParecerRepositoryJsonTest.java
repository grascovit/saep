package br.ufg.inf.persistencia.repositorios;

import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.mongodb.MongoWriteException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ParecerRepositoryJsonTest {

    private ParecerRepositoryJson parecerRepositoryJson;
	private static final String IDENTIFICADOR_PARECER_TESTE = "idTeste";

    @Before
    public void setUp() {
        parecerRepositoryJson = new ParecerRepositoryJson();
    }

    @Test
    public void adicionaNota() {

    }

    @Test
    public void removeNota() {

    }

    @Test
    public void persisteParecer() {
        Parecer parecer = obtenhaParecer();
	    parecerRepositoryJson.persisteParecer(parecer);
	    Parecer parecerPersistido = parecerRepositoryJson.byId(IDENTIFICADOR_PARECER_TESTE);
	    assertEquals("O parecer foi persistido e o id do objeto recuperado do banco é igual ao esperado", IDENTIFICADOR_PARECER_TESTE, parecerPersistido.getId());
    }

	@Test
	public void persisteParecerFalhaPoisIdJaExisteNoBanco() throws MongoWriteException {
		Parecer parecer = obtenhaParecer();
		parecerRepositoryJson.persisteParecer(parecer);
	}

    @Test
    public void atualizaFundamentacao() {

    }

    @Test
    public void byId() {
        Parecer parecer = parecerRepositoryJson.byId(IDENTIFICADOR_PARECER_TESTE);
	    assertEquals("O id do objeto recuperado do banco é igual ao esperado", IDENTIFICADOR_PARECER_TESTE, parecer.getId());
    }

    @Test(expected = IdentificadorDesconhecido.class)
    public void byIdLancaExcecaoPoisNaoEncontrouParecer() {
        parecerRepositoryJson.byId("identificadorIncorreto");
    }

    @Test
    public void removeParecer() {

    }

    @Test
    public void radocById() {

    }

    @Test
    public void persisteRadoc() {

    }

    @Test
    public void removeRadoc() {

    }

    private Parecer obtenhaParecer() {
	    ArrayList<String> radocs = new ArrayList<String>();
	    radocs.add("idRadoc");
	    ArrayList<Pontuacao> pontuacoes = new ArrayList<Pontuacao>();
	    pontuacoes.add(new Pontuacao("fatorCargaHoraria", new Valor(5.0f)));
	    ArrayList<Nota> notas = new ArrayList<Nota>();
	    notas.add(new Nota(new Pontuacao("pontuacaoAntiga", new Valor(1.0f)), new Pontuacao("pontuacaoNova", new Valor(10.0f)), "Justificativa para a nota"));
	    return new Parecer(IDENTIFICADOR_PARECER_TESTE, "idDaResolucao", radocs, pontuacoes, "Fundamentação de exemplo com caractéres Unicode", notas);
    }

}