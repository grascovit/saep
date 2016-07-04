package br.ufg.inf.persistencia.repositorios;

import br.ufg.inf.es.saep.sandbox.dominio.*;
import br.ufg.inf.persistencia.helpers.GsonHelper;
import br.ufg.inf.persistencia.helpers.MongoHelper;
import com.mongodb.client.FindIterable;
import org.bson.Document;

public class ParecerRepositoryJson implements ParecerRepository {

    private static final String COLECAO_PARECER = "parecer";
    private static final String IDENTIFICADOR_UNICO = "id";

    public void adicionaNota(String s, Nota nota) {

    }

    public void removeNota(Avaliavel avaliavel) {

    }

    public void persisteParecer(Parecer parecer) {
        Document documentoMongoParecer = GsonHelper.obtenhaDocumentoMongo(parecer);

        if (!documentoMongoParecer.containsKey(IDENTIFICADOR_UNICO) || documentoMongoParecer.get(IDENTIFICADOR_UNICO) == null) {
            throw new IdentificadorDesconhecido("O documento não possui um identificador");
        }

        MongoHelper.persistaDocumentoMongoComIdentificador(COLECAO_PARECER, documentoMongoParecer);
    }

    public void atualizaFundamentacao(String s, String s1) {

    }

    public Parecer byId(String id) {
        Document documentoMongoParecer = MongoHelper.recuperaDocumentoMongoPeloIdentificador(COLECAO_PARECER, id);
        return (Parecer) GsonHelper.obtenhaObjeto(documentoMongoParecer, Parecer.class);
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
