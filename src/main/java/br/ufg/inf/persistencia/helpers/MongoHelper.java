package br.ufg.inf.persistencia.helpers;

import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorDesconhecido;
import com.mongodb.MongoClient;
import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;

public class MongoHelper {

    private static final String IP_BANCO_DADOS = "63.142.254.59";
    private static final String NOME_BANCO_DADOS = "saep";
    private static final String IDENTIFICADOR_UNICO = "id";
    private static MongoClient mongoClient = new MongoClient(IP_BANCO_DADOS);

    private static MongoDatabase getBancoDadosMongo(String nomeBancoDados) {
        return mongoClient.getDatabase(nomeBancoDados);
    }

    private static MongoCollection<Document> getColecao(String nomeColecao) {
        return getBancoDadosMongo(NOME_BANCO_DADOS).getCollection(nomeColecao);
    }

    static Boolean garanteExistenciaIndice(String nomeColecao, String nomeIndice) {
        String indice = getBancoDadosMongo(NOME_BANCO_DADOS)
		        .getCollection(nomeColecao)
		        .createIndex(new Document(nomeIndice, 1), new IndexOptions().unique(true));
        return indice != null;
    }

    public static void persistaDocumentoMongoComIdentificador(String nomeColecao, Document documentoMongo) {
        MongoCollection<Document> collection = getColecao(nomeColecao);
        try {
            collection.insertOne(documentoMongo);
        } catch (MongoWriteException identificadorDuplicado) {
            System.out.println("Já existe na coleção um documento com o identificador do documento informado");
        }
    }

    public static Document recuperaDocumentoMongoPeloIdentificador(String nomeColecao, String identificador) {
        FindIterable<Document> resultados = getColecao(nomeColecao).find(new Document(IDENTIFICADOR_UNICO, identificador));
        if (resultados == null) {
            throw new IdentificadorDesconhecido("Não há documento na coleção " + nomeColecao + " com o identificador " + identificador);
        } else {
            return resultados.first();
        }
    }

}
