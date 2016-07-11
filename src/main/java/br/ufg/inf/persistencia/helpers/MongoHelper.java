package br.ufg.inf.persistencia.helpers;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoHelper {

    private static final String IP_BANCO_DADOS = "63.142.254.59";
    private static final String NOME_BANCO_DADOS = "saep";
    private static Fongo mongoClient = new Fongo(IP_BANCO_DADOS);

    private static MongoDatabase getBancoDadosMongo(String nomeBancoDados) {
        return mongoClient.getDatabase(nomeBancoDados);
    }

    private static MongoCollection<Document> getColecao(String nomeColecao) {
        return getBancoDadosMongo(NOME_BANCO_DADOS).getCollection(nomeColecao);
    }

    private static FindIterable<Document> recuperaIteravelMongo(String nomeColecao, Document filtro) {
        return getColecao(nomeColecao).find(filtro);
    }

    public static void persistaDocumentoMongo(String nomeColecao, Document documentoASerPersistido) {
        MongoCollection<Document> colecao = getColecao(nomeColecao);
        colecao.insertOne(documentoASerPersistido);
    }

    public static Document recuperaDocumentoMongo(String nomeColecao, Document filtro) {
        FindIterable<Document> resultados = recuperaIteravelMongo(nomeColecao, filtro);
        return resultados.first();
    }

    public static List<Document> recuperaDocumentosMongo(String nomeColecao, Document filtro) {
        List<Document> documentosMongo = new ArrayList<Document>();
        FindIterable<Document> resultados = recuperaIteravelMongo(nomeColecao, filtro);

        for (Document documentoMongo : resultados) {
            documentosMongo.add(documentoMongo);
        }

        return documentosMongo;
    }

    public static void atualizaDocumentoMongo(String nomeColecao, Document filtro, Document alteracao) {
        MongoCollection<Document> colecao = getColecao(nomeColecao);
        colecao.updateOne(filtro, alteracao);
    }

	public static Boolean removeDocumentoMongo(String nomeColecao, Document filtro) {
		MongoCollection<Document> colecao = getColecao(nomeColecao);
		DeleteResult resultadoRemocao = colecao.deleteOne(filtro);
        return resultadoRemocao.getDeletedCount() > 0;
	}

}
