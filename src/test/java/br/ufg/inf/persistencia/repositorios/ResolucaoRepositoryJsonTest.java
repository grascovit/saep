package br.ufg.inf.persistencia.repositorios;

import br.ufg.inf.es.saep.sandbox.dominio.Parecer;
import br.ufg.inf.es.saep.sandbox.dominio.Regra;
import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

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

    @Test
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

    private Resolucao obtenhaResolucao() {
        ArrayList<Regra> regras = new ArrayList<Regra>();
        ArrayList<String> dependeDe = new ArrayList<String>();
        dependeDe.add(obtenhaStringAleatoria());
        Regra regra = new Regra(obtenhaStringAleatoria(), 0, obtenhaStringAleatoria(), 10.0f, 0f, obtenhaStringAleatoria(), obtenhaStringAleatoria(),
                obtenhaStringAleatoria(), obtenhaStringAleatoria(), 10.0f, dependeDe);
        Regra outraRegra = new Regra(obtenhaStringAleatoria(), 1, obtenhaStringAleatoria(), 1.0f, -1.0f, obtenhaStringAleatoria(), obtenhaStringAleatoria(),
                obtenhaStringAleatoria(), obtenhaStringAleatoria(), 10.0f, dependeDe);
        regras.add(regra);
        regras.add(outraRegra);
        return new Resolucao(obtenhaStringAleatoria(), obtenhaStringAleatoria(), obtenhaStringAleatoria(), new Date(), regras);
    }

    private String obtenhaStringAleatoria() {
        return UUID.randomUUID().toString();
    }

}