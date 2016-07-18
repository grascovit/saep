package br.ufg.inf.persistencia.helpers;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoHelper {

	private String nomeBancoDados;
    private MongoClient mongoClient;

	public MongoHelper(ArquivoHelper arquivoHelper) {
		this.mongoClient = new MongoClient(arquivoHelper.obtenhaIpBancoDados());
		this.nomeBancoDados = arquivoHelper.obtenhaNomeBancoDados();
	}

	/**
	 * Obtém o banco de dados Mongo para operações
	 * através do nome que é passado como parâmetro
	 * @param nomeBancoDados nome do banco de dados à ser utilizado
	 * @return banco de dados que será utilizado
	 */
    private MongoDatabase getBancoDadosMongo(String nomeBancoDados) {
        return mongoClient.getDatabase(nomeBancoDados);
    }

	/**
	 * Obtém uma coleção do banco de dados para operações
	 * através do nome que é passado como parâmetro
	 * @param nomeColecao nome da coleção à ser utilizada
	 * @return coleção que será utilizada
	 */
    private MongoCollection<Document> getColecao(String nomeColecao) {
        return getBancoDadosMongo(nomeBancoDados).getCollection(nomeColecao);
    }

	/**
	 * Obtém objeto iterável do driver Mongo para Java.
	 * Este iterável é obtido através do filtro passado
	 * como parâmetro
	 * @param nomeColecao nome da coleção à ser filtrada
	 * @param filtro documento que servirá como filtro para a busca
	 * @return iterável contendo documentos filtrados
	 */
    private FindIterable<Document> recuperaIteravelMongo(String nomeColecao, Document filtro) {
        return getColecao(nomeColecao).find(filtro);
    }

	/**
	 * Persiste um documento Mongo na coleção especificada
	 * @param nomeColecao nome da coleção onde o documento será persistido
	 * @param documentoASerPersistido documento que será persistido na coleção
	 */
    public void persistaDocumentoMongo(String nomeColecao, Document documentoASerPersistido) {
        MongoCollection<Document> colecao = getColecao(nomeColecao);
        colecao.insertOne(documentoASerPersistido);
    }

	/**
	 * Busca um documento Mongo na coleção especificada
	 * através de um filtro especificado
	 * @param nomeColecao nome da coleção onde o documento será buscado
	 * @param filtro documento que servirá como filtro para a busca
	 * @return o primeiro documento que atender o filtro utilizado
	 */
    public Document recuperaDocumentoMongo(String nomeColecao, Document filtro) {
        FindIterable<Document> resultados = recuperaIteravelMongo(nomeColecao, filtro);
        return resultados.first();
    }

	/**
	 * Busca uma lista de documentos Mongo na coleção especificada
	 * através de um filtro especificado
	 * @param nomeColecao nome da coleção onde os documentos serão buscados
	 * @param filtro documento que servirá como filtro para a busca
	 * @return lista de documentos que atenderem o filtro utilizado
	 */
    public List<Document> recuperaDocumentosMongo(String nomeColecao, Document filtro) {
        List<Document> documentosMongo = new ArrayList<Document>();
        FindIterable<Document> resultados = recuperaIteravelMongo(nomeColecao, filtro);

        for (Document documentoMongo : resultados) {
            documentosMongo.add(documentoMongo);
        }

        return documentosMongo;
    }

	/**
	 * Atualiza um documento Mongo na coleção especificada
	 * através de um filtro
	 * @param nomeColecao nome da coleção onde o documento será atualizado
	 * @param filtro documento que servirá como filtro para a busca
	 * @param alteracao documento que substituirá a versão anterior do documento filtrado
	 */
    public void atualizaDocumentoMongo(String nomeColecao, Document filtro, Document alteracao) {
        MongoCollection<Document> colecao = getColecao(nomeColecao);
        colecao.updateOne(filtro, alteracao);
    }

	/**
	 * Remove um documento Mongo da coleção especificada
	 * através de um filtro
	 * @param nomeColecao nome da coleção onde o documento será removido
	 * @param filtro documento que servirá como filtro para a busca
	 * @return {@code true} caso a operação tenha sido executada com sucesso
	 * e {@code false} caso não remova nenhum documento ou aconteça algum erro
	 */
	public Boolean removeDocumentoMongo(String nomeColecao, Document filtro) {
		MongoCollection<Document> colecao = getColecao(nomeColecao);
		DeleteResult resultadoRemocao = colecao.deleteOne(filtro);
        return resultadoRemocao.getDeletedCount() > 0;
	}

}
