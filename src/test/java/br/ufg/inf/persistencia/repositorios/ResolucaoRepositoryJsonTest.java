package br.ufg.inf.persistencia.repositorios;

import br.ufg.inf.es.saep.sandbox.dominio.*;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ResolucaoRepositoryJsonTest {

    private ResolucaoRepositoryJson resolucaoRepositoryJson = new ResolucaoRepositoryJson();

    @Test
    public void byId() {
        Resolucao resolucao = obtenhaResolucao();
        String idResolucao = resolucao.getId();
        String idResolucaoPersistida = resolucaoRepositoryJson.persiste(resolucao);
        assertEquals("O id do objeto recuperado do banco é igual ao esperado", idResolucao, idResolucaoPersistida);
    }

    @Test
    public void byIdNaoEncontraResolucaoComIdentificadorDiferente() {
        Resolucao resolucao = obtenhaResolucao();
        resolucaoRepositoryJson.persiste(resolucao);
        assertNull("A resolução não é encontrada pois o identificador buscado é diferente", resolucaoRepositoryJson.byId(obtenhaStringAleatoria()));
    }

    @Test
    public void persiste() {
        Resolucao resolucao = obtenhaResolucao();
        String idResolucao = resolucao.getId();
        String idResolucaoPersistida = resolucaoRepositoryJson.persiste(resolucao);
        assertEquals("A resolução é persistida com sucesso", idResolucao, idResolucaoPersistida);
    }

    @Test(expected = IdentificadorExistente.class)
    public void persisteRetornaNuloPoisIdJaExisteNoBanco() {
        Resolucao resolucao = obtenhaResolucao();
        resolucaoRepositoryJson.persiste(resolucao);
        String idResolucaoPersistida = resolucaoRepositoryJson.persiste(resolucao);
        assertNull("A resolução não é persistida pois já existe uma resolução com mesmo identificador", idResolucaoPersistida);
    }

    @Test
    public void remove() {
        Resolucao resolucao = obtenhaResolucao();
        String idResolucaoPersistida = resolucaoRepositoryJson.persiste(resolucao);
        assertTrue("A resolução é removida com sucesso", resolucaoRepositoryJson.remove(idResolucaoPersistida));
    }

    @Test
    public void removeNaoFazNadaPoisResolucaoNaoExiste() {
        Resolucao resolucao = obtenhaResolucao();
        resolucaoRepositoryJson.persiste(resolucao);
        assertFalse("A resolução não é removida pois o identificador está incorreto", resolucaoRepositoryJson.remove(obtenhaStringAleatoria()));
    }

    @Test
    public void resolucoes() {
        Resolucao resolucao = obtenhaResolucao();
        String idResolucao = resolucaoRepositoryJson.persiste(resolucao);
        resolucao = obtenhaResolucao();
        String outroIdResolucao = resolucaoRepositoryJson.persiste(resolucao);
        List<String> identificadoresResolucoes = resolucaoRepositoryJson.resolucoes();
        assertTrue("Os identificadores das resoluções disponíveis são iguais aos persistidos", identificadoresResolucoes.contains(idResolucao));
        assertTrue("Os identificadores das resoluções disponíveis são iguais aos persistidos", identificadoresResolucoes.contains(outroIdResolucao));
    }

	@Test
	public void persisteTipo() {
		Tipo tipo = obtenhaTipo();
		String idTipo = tipo.getId();
		resolucaoRepositoryJson.persisteTipo(tipo);
		Tipo tipoPersistido = resolucaoRepositoryJson.tipoPeloCodigo(idTipo);
		assertEquals("O tipo é persistido com sucesso", idTipo, tipoPersistido.getId());
	}

    @Test(expected = IdentificadorExistente.class)
    public void persisteTipoFalhaPoisJaExisteTipoComMesmoNome() {
        Tipo tipo = obtenhaTipo();
        resolucaoRepositoryJson.persisteTipo(tipo);
        resolucaoRepositoryJson.persisteTipo(tipo);
    }

	@Test
	public void removeTipo() {
		Tipo tipo = obtenhaTipo();
		String idTipo = tipo.getId();
		resolucaoRepositoryJson.persisteTipo(tipo);
		resolucaoRepositoryJson.removeTipo(idTipo);
		assertNull("O tipo foi removido com sucesso", resolucaoRepositoryJson.tipoPeloCodigo(idTipo));
	}

	@Test(expected = ResolucaoUsaTipoException.class)
	public void removeTipoFalhaPoisExisteRegraDeResolucaoReferenciandoTipo() {
		Tipo tipo = obtenhaTipo();
		String idTipo = tipo.getId();
		resolucaoRepositoryJson.persisteTipo(tipo);
		Resolucao resolucao = obtenhaResolucao();
		Regra regra = new Regra(obtenhaStringAleatoria(), Regra.PONTOS, obtenhaStringAleatoria(), 5.0f, 0.0f, null, null, null, idTipo, 1.0f, null);
		resolucao.getRegras().add(regra);
		resolucaoRepositoryJson.persiste(resolucao);
		resolucaoRepositoryJson.removeTipo(idTipo);
	}

	@Test
	public void tipoPeloCodigo() {
		Tipo tipo = obtenhaTipo();
		String idTipo = tipo.getId();
		resolucaoRepositoryJson.persisteTipo(tipo);
		Tipo tipoPersistido = resolucaoRepositoryJson.tipoPeloCodigo(idTipo);
		assertEquals("O id do objeto recuperado do banco é igual ao esperado", idTipo, tipoPersistido.getId());
	}

	@Test
	public void tipoPeloCodigoNaoEncontraTipoComIdentificadorDiferente() {
		Tipo tipo = obtenhaTipo();
		resolucaoRepositoryJson.persisteTipo(tipo);
		assertNull("O tipo não é encontrado pois o identificador buscado é diferente", resolucaoRepositoryJson.tipoPeloCodigo(obtenhaStringAleatoria()));
	}

	@Test
	public void tiposPeloNome() {
		Tipo tipo = obtenhaTipo();
		String nomeTipo = tipo.getNome().substring(0, 5);
		resolucaoRepositoryJson.persisteTipo(tipo);
		List<Tipo> tiposPersistidos = resolucaoRepositoryJson.tiposPeloNome(nomeTipo);
		assertEquals("Os tipos que possuem nomes similares foram recuperados com sucesso", tipo.getNome(), tiposPersistidos.get(0).getNome());
	}

	@Test
	public void tiposPeloNomeNaoEncontraNadaComNomeDiferente() {
		Tipo tipo = obtenhaTipo();
		resolucaoRepositoryJson.persisteTipo(tipo);
		assertEquals("O método não encontra nenhum tipo pois não existem tipos persistidos com o nome buscado", 0, resolucaoRepositoryJson.tiposPeloNome(obtenhaStringAleatoria()).size());
	}

    private Resolucao obtenhaResolucao() {
        ArrayList<Regra> regras = new ArrayList<Regra>();
        ArrayList<String> dependeDe = new ArrayList<String>();
        dependeDe.add(obtenhaStringAleatoria());
        Regra regra = new Regra(obtenhaStringAleatoria(), Regra.PONTOS, obtenhaStringAleatoria(), 10.0f, 0f, null, null, null, obtenhaStringAleatoria(), 10.0f, null);
        Regra outraRegra = new Regra(obtenhaStringAleatoria(), Regra.EXPRESSAO, obtenhaStringAleatoria(), 1.0f, -1.0f, obtenhaStringAleatoria(), null, null, null, 0.0f, dependeDe);
        regras.add(regra);
        regras.add(outraRegra);
        return new Resolucao(obtenhaStringAleatoria(), obtenhaStringAleatoria(), obtenhaStringAleatoria(), new Date(), regras);
    }

	private Tipo obtenhaTipo() {
		Set<Atributo> atributos = new HashSet<Atributo>();
		atributos.add(new Atributo(obtenhaStringAleatoria(), obtenhaStringAleatoria(), Atributo.STRING));
		atributos.add(new Atributo(obtenhaStringAleatoria(), obtenhaStringAleatoria(), Atributo.STRING));
		return new Tipo(obtenhaStringAleatoria(), obtenhaStringAleatoria(), obtenhaStringAleatoria(), atributos);
	}

    private String obtenhaStringAleatoria() {
        return UUID.randomUUID().toString();
    }

}