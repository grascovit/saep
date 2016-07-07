package br.ufg.inf.persistencia.helpers;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoHelper {

    private static final String IP_BANCO_DADOS = "63.142.254.59";
    private static final String NOME_BANCO_DADOS = "saep";
    private static MongoClient mongoClient = new MongoClient(IP_BANCO_DADOS);

    private static MongoDatabase getBancoDadosMongo(String nomeBancoDados) {
        return mongoClient.getDatabase(nomeBancoDados);
    }

    private static MongoCollection<Document> getColecao(String nomeColecao) {
        return getBancoDadosMongo(NOME_BANCO_DADOS).getCollection(nomeColecao);
    }

    public static void persistaDocumentoMongo(String nomeColecao, Document documentoASerPersistido) {
        MongoCollection<Document> colecao = getColecao(nomeColecao);
        colecao.insertOne(documentoASerPersistido);
    }

    public static Document recuperaDocumentoMongo(String nomeColecao, Document filtro) {
        FindIterable<Document> resultados = getColecao(nomeColecao).find(filtro);
        return resultados.first();
    }

    public static void atualizaDocumentoMongo(String nomeColecao, Document filtro, Document alteracao) {
        MongoCollection<Document> colecao = getColecao(nomeColecao);
        colecao.updateOne(filtro, alteracao);
    }

	public static void removeDocumentoMongo(String nomeColecao, Document filtro) {
		MongoCollection<Document> colecao = getColecao(nomeColecao);
		colecao.deleteOne(filtro);
	}

}
