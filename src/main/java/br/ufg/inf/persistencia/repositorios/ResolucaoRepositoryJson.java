package br.ufg.inf.persistencia.repositorios;

import br.ufg.inf.es.saep.sandbox.dominio.*;
import br.ufg.inf.persistencia.helpers.GsonHelper;
import br.ufg.inf.persistencia.helpers.MongoHelper;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ResolucaoRepositoryJson implements ResolucaoRepository {

    private static final String COLECAO_RESOLUCAO = "resolucao";
    private static final String IDENTIFICADOR_UNICO = "id";

    public Resolucao byId(String id) {
        Document filtroPeloId = GsonHelper.obtenhaDocumentoMongo(new Document(IDENTIFICADOR_UNICO, id));
        Document documentoMongoResolucao = MongoHelper.recuperaDocumentoMongo(COLECAO_RESOLUCAO, filtroPeloId);
        return documentoMongoResolucao == null ? null : (Resolucao) GsonHelper.obtenhaObjeto(documentoMongoResolucao, Resolucao.class);
    }

    public String persiste(Resolucao resolucao) {
        Document documentoMongoResolucao = GsonHelper.obtenhaDocumentoMongo(resolucao);

        if (!documentoMongoResolucao.containsKey(IDENTIFICADOR_UNICO) || documentoMongoResolucao.get(IDENTIFICADOR_UNICO) == null) {
            throw new CampoExigidoNaoFornecido("O documento não possui um identificador");
        }

        Document filtroPeloId = GsonHelper.obtenhaDocumentoMongo(new Document(IDENTIFICADOR_UNICO, resolucao.getId()));
        Document resolucaoJaExistente = MongoHelper.recuperaDocumentoMongo(COLECAO_RESOLUCAO, filtroPeloId);

        if (resolucaoJaExistente != null) {
            return null;
        }

        MongoHelper.persistaDocumentoMongo(COLECAO_RESOLUCAO, documentoMongoResolucao);
        resolucaoJaExistente = MongoHelper.recuperaDocumentoMongo(COLECAO_RESOLUCAO, filtroPeloId);

        return resolucaoJaExistente.get(IDENTIFICADOR_UNICO) != null ? resolucaoJaExistente.get(IDENTIFICADOR_UNICO).toString() : null;
    }

    public boolean remove(String identificador) {
        Document filtroPeloId = GsonHelper.obtenhaDocumentoMongo(new Document(IDENTIFICADOR_UNICO, identificador));
        return MongoHelper.removeDocumentoMongo(COLECAO_RESOLUCAO, filtroPeloId);
    }

    public List<String> resolucoes() {
        List<Document> documentosResolucoesDisponiveis = MongoHelper.recuperaDocumentosMongo(COLECAO_RESOLUCAO, new Document());
        List<String> idsResolucoesDisponiveis = new ArrayList<String>();

        for (Document documentoResolucao : documentosResolucoesDisponiveis) {
            Resolucao resolucao = (Resolucao) GsonHelper.obtenhaObjeto(documentoResolucao, Resolucao.class);
            idsResolucoesDisponiveis.add(resolucao.getId());
        }

        return idsResolucoesDisponiveis;
    }

    public void persisteTipo(Tipo tipo) {

    }

    public void removeTipo(String codigo) {

    }

    public Tipo tipoPeloCodigo(String codigo) {
        return null;
    }

    public List<Tipo> tiposPeloNome(String nome) {
        return null;
    }

}
