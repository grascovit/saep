package br.ufg.inf.persistencia.repositorios;

import br.ufg.inf.es.saep.sandbox.dominio.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ParecerRepositoryJsonTest {

    private ParecerRepositoryJson parecerRepositoryJson;

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
        String id = obtenhaIdAleatorio();
        Parecer parecer = obtenhaParecer(id);
	    parecerRepositoryJson.persisteParecer(parecer);
	    Parecer parecerPersistido = parecerRepositoryJson.byId(id);
	    assertEquals("O parecer foi persistido e o id do objeto recuperado do banco é igual ao esperado", id, parecerPersistido.getId());
    }

	@Test(expected = IdentificadorDesconhecido.class)
	public void persisteParecerFalhaPoisIdJaExisteNoBanco() {
        String id = obtenhaIdAleatorio();
		Parecer parecer = obtenhaParecer(id);
        parecerRepositoryJson.persisteParecer(parecer);
        parecerRepositoryJson.persisteParecer(parecer);
	}

    @Test
    public void atualizaFundamentacao() {
        String id = obtenhaIdAleatorio();
        Parecer parecer = obtenhaParecer(id);
        parecerRepositoryJson.persisteParecer(parecer);
        String novaFundamentacao = "Nova fundamentação";
        parecerRepositoryJson.atualizaFundamentacao(id, novaFundamentacao);
        parecer = parecerRepositoryJson.byId(id);
        assertEquals("A fundamentação foi atualizada com sucesso", novaFundamentacao, parecer.getFundamentacao());
    }

    @Test(expected = IdentificadorDesconhecido.class)
    public void atualizaFundamentacaoFalhaPoisParecerNaoEstaPersistido() {
        parecerRepositoryJson.atualizaFundamentacao(obtenhaIdAleatorio(), "Nova fundamentação");
    }

    @Test
    public void byId() {
        String id = obtenhaIdAleatorio();
        Parecer parecer = obtenhaParecer(id);
        parecerRepositoryJson.persisteParecer(parecer);
        parecer = parecerRepositoryJson.byId(id);
	    assertEquals("O id do objeto recuperado do banco é igual ao esperado", id, parecer.getId());
    }

    @Test
    public void byIdNaoEncontraParecerComOutroIdentificador() {
        String id = obtenhaIdAleatorio();
        Parecer parecer = obtenhaParecer(id);
        parecerRepositoryJson.persisteParecer(parecer);
        assertNull(parecerRepositoryJson.byId(obtenhaIdAleatorio()));
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

    private Parecer obtenhaParecer(String id) {
	    ArrayList<String> radocs = new ArrayList<String>();
	    radocs.add("idRadoc");
	    ArrayList<Pontuacao> pontuacoes = new ArrayList<Pontuacao>();
	    pontuacoes.add(new Pontuacao("fatorCargaHoraria", new Valor(5.0f)));
	    ArrayList<Nota> notas = new ArrayList<Nota>();
	    notas.add(new Nota(new Pontuacao("pontuacaoAntiga", new Valor(1.0f)), new Pontuacao("pontuacaoNova", new Valor(10.0f)), "Justificativa para a nota"));
	    return new Parecer(id, "idDaResolucao", radocs, pontuacoes, "Fundamentação de exemplo com caractéres Unicode", notas);
    }

    private String obtenhaIdAleatorio() {
        return UUID.randomUUID().toString();
    }
    
}