package br.ufg.inf.persistencia.repositorios;

import br.ufg.inf.es.saep.sandbox.dominio.*;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ParecerRepositoryJsonTest {

    private ParecerRepositoryJson parecerRepositoryJson = new ParecerRepositoryJson();

    @Test
    public void adicionaNota() {
        Parecer parecer = obtenhaParecer();
        String id = parecer.getId();
        parecerRepositoryJson.persisteParecer(parecer);
	    int quantidadeNotas = parecer.getNotas().size();
	    Nota nota = obtenhaNota();
        parecerRepositoryJson.adicionaNota(id, nota);
        parecer = parecerRepositoryJson.byId(id);
        assertEquals("A nota foi adicionada com sucesso ao parecer já existente", ++quantidadeNotas, parecer.getNotas().size());
    }

    @Test(expected = IdentificadorDesconhecido.class)
    public void adicionaNotaFalhaPoisParecerNaoEstaPersistido() {
        Nota nota = obtenhaNota();
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
        Radoc radoc = obtenhaRadoc();
        String idRadoc = radoc.getId();
	    String idRadocPersistido = parecerRepositoryJson.persisteRadoc(radoc);
        radoc = parecerRepositoryJson.radocById(idRadoc);
	    assertEquals("O RADOC foi recuperado através do seu identificador com sucesso", idRadocPersistido, radoc.getId());
    }

    @Test
    public void persisteRadoc() {
	    Radoc radoc = obtenhaRadoc();
	    String idRadoc = radoc.getId();
	    String idRadocPersistido = parecerRepositoryJson.persisteRadoc(radoc);
	    assertEquals("O RADOC foi persistido com sucesso", idRadoc, idRadocPersistido);
    }

    @Test(expected = IdentificadorDesconhecido.class)
    public void persisteRadocFalhaPoisIdJaExisteNoBanco() {
        Radoc radoc = obtenhaRadoc();
        parecerRepositoryJson.persisteRadoc(radoc);
        parecerRepositoryJson.persisteRadoc(radoc);
    }

    @Test
    public void removeRadoc() {
        Radoc radoc = obtenhaRadoc();
        String idRadoc = radoc.getId();
        parecerRepositoryJson.persisteRadoc(radoc);
        parecerRepositoryJson.removeRadoc(idRadoc);
        radoc = parecerRepositoryJson.radocById(idRadoc);
        assertNull("O RADOC foi removido com sucesso", radoc);
    }

    @Test
    public void removeRadocFalhaPoisExisteParecerReferenciandoRadoc() {
        Radoc radoc = obtenhaRadoc();
        String idRadoc = radoc.getId();
        parecerRepositoryJson.persisteRadoc(radoc);
        Parecer parecer = obtenhaParecer();
        parecer.getRadocs().add(idRadoc);
        parecerRepositoryJson.persisteParecer(parecer);
        parecerRepositoryJson.removeRadoc(idRadoc);
        radoc = parecerRepositoryJson.radocById(idRadoc);
        assertEquals("O RADOC não foi removido pois existe um parecer que o referencia", idRadoc, radoc.getId());
    }

    private Parecer obtenhaParecer() {
	    ArrayList<String> radocs = new ArrayList<String>();
	    radocs.add(obtenhaStringAleatoria());
	    ArrayList<Pontuacao> pontuacoes = new ArrayList<Pontuacao>();
	    pontuacoes.add(new Pontuacao(obtenhaStringAleatoria(), new Valor(5.0f)));
	    ArrayList<Nota> notas = new ArrayList<Nota>();
	    notas.add(new Nota(new Pontuacao(obtenhaStringAleatoria(), new Valor(1.0f)), new Pontuacao(obtenhaStringAleatoria(), new Valor(10.0f)), obtenhaStringAleatoria()));
	    return new Parecer(obtenhaStringAleatoria(), obtenhaStringAleatoria(), radocs, pontuacoes, obtenhaStringAleatoria(), notas);
    }

	private Radoc obtenhaRadoc() {
		int anoBase = new Random().nextInt(2000) + 1000;
		ArrayList<Relato> relatos = new ArrayList<Relato>();
		Map<String, Valor> valores = new HashMap<String, Valor>();
		valores.put(obtenhaStringAleatoria(), new Valor(obtenhaStringAleatoria()));
		relatos.add(new Relato(obtenhaStringAleatoria(), valores));
		return new Radoc(obtenhaStringAleatoria(), anoBase, relatos);
	}

    private Nota obtenhaNota() {
        Pontuacao pontuacaoOriginal = new Pontuacao(obtenhaStringAleatoria(), new Valor(10.0f));
        Pontuacao pontuacaoNova = new Pontuacao(obtenhaStringAleatoria(), new Valor(5.0f));
        return new Nota(pontuacaoOriginal, pontuacaoNova, obtenhaStringAleatoria());
    }

    private String obtenhaStringAleatoria() {
        return UUID.randomUUID().toString();
    }
    
}