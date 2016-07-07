package br.ufg.inf.persistencia.repositorios;

import br.ufg.inf.es.saep.sandbox.dominio.*;
import br.ufg.inf.persistencia.helpers.GsonHelper;
import br.ufg.inf.persistencia.helpers.MongoHelper;
import org.bson.Document;

public class ParecerRepositoryJson implements ParecerRepository {

    private static final String COLECAO_PARECER = "parecer";
    private static final String FUNDAMENTACAO_PARECER = "fundamentacao";
    private static final String NOTAS_PARECER = "notas";
    private static final String AVALIAVEL_ORIGINAL = "original";
    private static final String FILTRO_AVALIAVEL_ORIGINAL = "notas.original";
    private static final String IDENTIFICADOR_UNICO = "id";

    public void adicionaNota(String parecer, Nota nota) {
        Parecer parecerASerAtualizado = byId(parecer);

        if (parecerASerAtualizado == null) {
            throw new IdentificadorDesconhecido("O parecer à ser atualizado não existe no banco de dados");
        }

        Document documentoMongoParecer = GsonHelper.obtenhaDocumentoMongo(parecerASerAtualizado);
        Document adicaoNota = GsonHelper.obtenhaDocumentoMongo(new Document("$push", new Document(NOTAS_PARECER, nota)));
        MongoHelper.atualizaAtributoDocumentoMongo(COLECAO_PARECER, documentoMongoParecer, adicaoNota);
    }

    public void removeNota(Avaliavel original) {
        Document filtroPeloAvaliavel = GsonHelper.obtenhaDocumentoMongo(new Document(FILTRO_AVALIAVEL_ORIGINAL, original));
        Document remocaoNota = GsonHelper.obtenhaDocumentoMongo(new Document("$pull", new Document(NOTAS_PARECER, new Document(AVALIAVEL_ORIGINAL, original))));
        MongoHelper.atualizaAtributoDocumentoMongo(COLECAO_PARECER, filtroPeloAvaliavel, remocaoNota);
    }

    public void persisteParecer(Parecer parecer) {
        Document documentoMongoParecer = GsonHelper.obtenhaDocumentoMongo(parecer);

        if (!documentoMongoParecer.containsKey(IDENTIFICADOR_UNICO) || documentoMongoParecer.get(IDENTIFICADOR_UNICO) == null) {
            throw new IdentificadorDesconhecido("O documento não possui um identificador");
        }

        MongoHelper.persistaDocumentoMongoComIdentificador(COLECAO_PARECER, documentoMongoParecer);
    }

    public void atualizaFundamentacao(String parecer, String fundamentacao) {
        Parecer parecerASerAtualizado = byId(parecer);

        if (parecerASerAtualizado == null) {
            throw new IdentificadorDesconhecido("O parecer à ser atualizado não existe no banco de dados");
        }

        Document documentoMongoParecer = GsonHelper.obtenhaDocumentoMongo(parecerASerAtualizado);
        Document alteracaoFundamentacao = GsonHelper.obtenhaDocumentoMongo(new Document("$set", new Document(FUNDAMENTACAO_PARECER, fundamentacao)));
        MongoHelper.atualizaAtributoDocumentoMongo(COLECAO_PARECER, documentoMongoParecer, alteracaoFundamentacao);
    }

    public Parecer byId(String id) {
        Document documentoMongoParecer = MongoHelper.recuperaDocumentoMongoPeloIdentificador(COLECAO_PARECER, id);
        return documentoMongoParecer == null ? null : (Parecer) GsonHelper.obtenhaObjeto(documentoMongoParecer, Parecer.class);
    }

    public void removeParecer(String s) {

    }

    public Radoc radocById(String s) {
        return null;
    }

    public String persisteRadoc(Radoc radoc) {
        return null;
    }

    public void removeRadoc(String s) {

    }

}
