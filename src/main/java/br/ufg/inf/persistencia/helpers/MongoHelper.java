package br.ufg.inf.persistencia.helpers;

import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorDesconhecido;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoHelper {

    private static final String IP_BANCO_DADOS = "127.0.0.1";
    private static final String NOME_BANCO_DADOS = "saep";
    private static final String IDENTIFICADOR_UNICO = "id";
    private static MongoClient mongoClient = new MongoClient(IP_BANCO_DADOS);

    private static MongoDatabase getBancoDadosMongo(String nomeBancoDados) {
        return mongoClient.getDatabase(nomeBancoDados);
    }

    private static MongoCollection<Document> getColecao(String nomeColecao) {
        return getBancoDadosMongo(NOME_BANCO_DADOS).getCollection(nomeColecao);
    }

    public static void persistaDocumentoMongoComIdentificador(String nomeColecao, Document documentoMongo) {
        MongoCollection<Document> colecao = getColecao(nomeColecao);
        Document documentoJaExistente = recuperaDocumentoMongoPeloIdentificador(nomeColecao, documentoMongo.get(IDENTIFICADOR_UNICO).toString());

        if (documentoJaExistente != null) {
            throw new IdentificadorDesconhecido("Já existe na coleção um documento com o identificador do documento informado");
        }

        colecao.insertOne(documentoMongo);
    }

    public static Document recuperaDocumentoMongoPeloIdentificador(String nomeColecao, String identificador) {
        FindIterable<Document> resultados = getColecao(nomeColecao).find(new Document(IDENTIFICADOR_UNICO, identificador));
        return resultados.first();
    }

    public static void atualizaAtributoDocumentoMongo(Document documentoMongo, Document documentoAlteracao, String nomeColecao) {
        MongoCollection<Document> colecao = getColecao(nomeColecao);
        colecao.updateOne(documentoMongo, documentoAlteracao);
    }

}
