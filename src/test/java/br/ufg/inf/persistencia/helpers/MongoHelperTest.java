package br.ufg.inf.persistencia.helpers;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MongoHelperTest {

    private static final String COLECAO_PARECER = "parecer";
    private static final String COLECAO_RESOLUCAO = "resolucao";
    private static final String IDENTIFICADOR_UNICO = "id";

    @Test
    public void garanteUnicidadeIdentificadorParecer() {
       assertTrue(MongoHelper.garanteExistenciaIndice(COLECAO_PARECER, IDENTIFICADOR_UNICO));
    }

    @Test
    public void garanteUnicidadeIdentificadorResolucao() {
       assertTrue(MongoHelper.garanteExistenciaIndice(COLECAO_RESOLUCAO, IDENTIFICADOR_UNICO));
    }

}
