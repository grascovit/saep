package br.ufg.inf.persistencia.repositorios;

import br.ufg.inf.es.saep.sandbox.dominio.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ParecerRepositoryJsonTest {

    private ParecerRepositoryJson parecerRepositoryJson;

    @Before
    public void setUp() {
        parecerRepositoryJson = new ParecerRepositoryJson();
    }

    @Test
    public void adicionaNota() {
        Parecer parecer = obtenhaParecer();
        String id = parecer.getId();
        parecerRepositoryJson.persisteParecer(parecer);
	    int quantidadeNotas = parecer.getNotas().size();
	    Nota nota = new Nota(new Pontuacao(obtenhaStringAleatoria(), new Valor(10.0f)), new Pontuacao(obtenhaStringAleatoria(), new Valor(5.0f)), obtenhaStringAleatoria());
        parecerRepositoryJson.adicionaNota(id, nota);
        parecer = parecerRepositoryJson.byId(id);
        assertEquals("A nota foi adicionada com sucesso ao parecer já existente", ++quantidadeNotas, parecer.getNotas().size());
    }

    @Test(expected = IdentificadorDesconhecido.class)
    public void adicionaNotaFalhaPoisParecerNaoEstaPersistido() {
        Nota nota = new Nota(new Pontuacao(obtenhaStringAleatoria(), new Valor(10.0f)), new Pontuacao(obtenhaStringAleatoria(), new Valor(5.0f)), obtenhaStringAleatoria());
        parecerRepositoryJson.adicionaNota(obtenhaStringAleatoria(), nota);
    }

    @Test
    public void removeNota() {
        Parecer parecer = obtenhaParecer();
        String id = parecer.getId();
        Pontuacao pontuacaoOriginal = (Pontuacao) parecer.getNotas().get(0).getItemOriginal();
        parecerRepositoryJson.persisteParecer(parecer);
        int quantidadeNotas = parecer.getNotas().size();
        parecerRepositoryJson.removeNota(pontuacaoOriginal);
        parecer = parecerRepositoryJson.byId(id);
        assertEquals("A nota foi removida com sucesso através do seu avaliável original", --quantidadeNotas, parecer.getNotas().size());
    }

    @Test
    public void removeNotaNaoFazNadaPoisNaoHaNotaComAvaliavelFornecido() {
        Parecer parecer = obtenhaParecer();
        String id = parecer.getId();
        parecerRepositoryJson.persisteParecer(parecer);
        int quantidadeNotas = parecer.getNotas().size();
        Pontuacao pontuacaoOriginal = new Pontuacao("avaliavelOriginalQueNaoExiste", new Valor(999.0f));
        parecerRepositoryJson.removeNota(pontuacaoOriginal);
        parecer = parecerRepositoryJson.byId(id);
        assertEquals("Nenhuma nota foi removida pois não existe nota que possui este avaliável original", quantidadeNotas, parecer.getNotas().size());
    }

    @Test
    public void persisteParecer() {
        Parecer parecer = obtenhaParecer();
        String id = parecer.getId();
	    parecerRepositoryJson.persisteParecer(parecer);
	    Parecer parecerPersistido = parecerRepositoryJson.byId(id);
	    assertEquals("O parecer foi persistido e o id do objeto recuperado do banco é igual ao esperado", id, parecerPersistido.getId());
    }

	@Test(expected = IdentificadorDesconhecido.class)
	public void persisteParecerFalhaPoisIdJaExisteNoBanco() {
		Parecer parecer = obtenhaParecer();
        parecerRepositoryJson.persisteParecer(parecer);
        parecerRepositoryJson.persisteParecer(parecer);
	}

    @Test
    public void atualizaFundamentacao() {
        Parecer parecer = obtenhaParecer();
        String id = parecer.getId();
        parecerRepositoryJson.persisteParecer(parecer);
        String novaFundamentacao = "Nova fundamentação";
        parecerRepositoryJson.atualizaFundamentacao(id, novaFundamentacao);
        parecer = parecerRepositoryJson.byId(id);
        assertEquals("A fundamentação foi atualizada com sucesso", novaFundamentacao, parecer.getFundamentacao());
    }

    @Test(expected = IdentificadorDesconhecido.class)
    public void atualizaFundamentacaoFalhaPoisParecerNaoEstaPersistido() {
        parecerRepositoryJson.atualizaFundamentacao(obtenhaStringAleatoria(), "Nova fundamentação");
    }

    @Test
    public void byId() {
        Parecer parecer = obtenhaParecer();
        String id = parecer.getId();
        parecerRepositoryJson.persisteParecer(parecer);
        parecer = parecerRepositoryJson.byId(id);
	    assertEquals("O id do objeto recuperado do banco é igual ao esperado", id, parecer.getId());
    }

    @Test
    public void byIdNaoEncontraParecerComOutroIdentificador() {
        Parecer parecer = obtenhaParecer();
        parecerRepositoryJson.persisteParecer(parecer);
        assertNull(parecerRepositoryJson.byId(obtenhaStringAleatoria()));
    }

    @Test
    public void removeParecer() {
	    Parecer parecer = obtenhaParecer();
	    String id = parecer.getId();
	    parecerRepositoryJson.persisteParecer(parecer);
	    parecerRepositoryJson.removeParecer(id);
	    parecer = parecerRepositoryJson.byId(id);
	    assertNull("O parecer foi removido com sucesso através do seu identificador", parecer);
    }

	@Test
	public void removeParecerNaoFazNadaPoisParecerNaoExiste() {
		Parecer parecer = obtenhaParecer();
		String id = parecer.getId();
		parecerRepositoryJson.persisteParecer(parecer);
		parecerRepositoryJson.removeParecer(obtenhaStringAleatoria());
		parecer = parecerRepositoryJson.byId(id);
		assertNotNull("O parecer não foi removido pois o identificador está incorreto", parecer);
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
        String id = obtenhaStringAleatoria();
	    ArrayList<String> radocs = new ArrayList<String>();
	    radocs.add(obtenhaStringAleatoria());
	    ArrayList<Pontuacao> pontuacoes = new ArrayList<Pontuacao>();
	    pontuacoes.add(new Pontuacao(obtenhaStringAleatoria(), new Valor(5.0f)));
	    ArrayList<Nota> notas = new ArrayList<Nota>();
	    notas.add(new Nota(new Pontuacao(obtenhaStringAleatoria(), new Valor(1.0f)), new Pontuacao(obtenhaStringAleatoria(), new Valor(10.0f)), obtenhaStringAleatoria()));
	    return new Parecer(id, obtenhaStringAleatoria(), radocs, pontuacoes, obtenhaStringAleatoria(), notas);
    }

    private String obtenhaStringAleatoria() {
        return UUID.randomUUID().toString();
    }
    
}