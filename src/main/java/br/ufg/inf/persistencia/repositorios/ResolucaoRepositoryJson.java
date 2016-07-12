package br.ufg.inf.persistencia.repositorios;

import br.ufg.inf.es.saep.sandbox.dominio.*;
import br.ufg.inf.persistencia.helpers.GsonHelper;
import br.ufg.inf.persistencia.helpers.MongoHelper;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ResolucaoRepositoryJson implements ResolucaoRepository {

    private static final String COLECAO_RESOLUCAO = "resolucao";
    private static final String COLECAO_TIPO = "tipo";
    private static final String TIPO_REGRA = "regras.tipoRelato";
    private static final String NOME_TIPO = "nome";
    private static final String IDENTIFICADOR_UNICO = "id";

    public Resolucao byId(String id) {
        Document filtroPeloId = GsonHelper.obtenhaDocumentoMongo(new Document(IDENTIFICADOR_UNICO, id));
        Document documentoMongoResolucao = MongoHelper.recuperaDocumentoMongo(COLECAO_RESOLUCAO, filtroPeloId);
        return (Resolucao) GsonHelper.obtenhaObjeto(documentoMongoResolucao, Resolucao.class);
    }

    public String persiste(Resolucao resolucao) {
        Document documentoMongoResolucao = GsonHelper.obtenhaDocumentoMongo(resolucao);

        if (!documentoMongoResolucao.containsKey(IDENTIFICADOR_UNICO) || documentoMongoResolucao.get(IDENTIFICADOR_UNICO) == null) {
            throw new CampoExigidoNaoFornecido("O documento não possui um identificador");
        }

        Document filtroPeloId = GsonHelper.obtenhaDocumentoMongo(new Document(IDENTIFICADOR_UNICO, resolucao.getId()));
        Document resolucaoJaExistente = MongoHelper.recuperaDocumentoMongo(COLECAO_RESOLUCAO, filtroPeloId);

        if (resolucaoJaExistente != null) {
            throw new IdentificadorExistente("Já existe na coleção um documento com o identificador da resolução informada");
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
        Document filtroPeloCodigo = GsonHelper.obtenhaDocumentoMongo(new Document(NOME_TIPO, tipo.getNome()));
        Tipo tipoJaExistente = (Tipo) GsonHelper.obtenhaObjeto(MongoHelper.recuperaDocumentoMongo(COLECAO_TIPO, filtroPeloCodigo), Tipo.class);

        if (tipoJaExistente != null) {
            throw new IdentificadorExistente("Já existe na coleção um documento com o nome do tipo informado");
        }

        Document documentoMongoTipo = GsonHelper.obtenhaDocumentoMongo(tipo);
	    MongoHelper.persistaDocumentoMongo(COLECAO_TIPO, documentoMongoTipo);
    }

    public void removeTipo(String codigo) {
	    Document filtroPeloId = GsonHelper.obtenhaDocumentoMongo(new Document(TIPO_REGRA, codigo));
	    Document resolucaoReferenciandoTipo = MongoHelper.recuperaDocumentoMongo(COLECAO_RESOLUCAO, filtroPeloId);

	    if (resolucaoReferenciandoTipo != null) {
		    throw new ResolucaoUsaTipoException("Não foi possível remover o tipo pois o mesmo é utilizado por pelo menos uma resolução");
	    }

	    filtroPeloId = GsonHelper.obtenhaDocumentoMongo(new Document(IDENTIFICADOR_UNICO, codigo));
	    MongoHelper.removeDocumentoMongo(COLECAO_TIPO, filtroPeloId);
    }

    public Tipo tipoPeloCodigo(String codigo) {
	    Document filtroPeloId = GsonHelper.obtenhaDocumentoMongo(new Document(IDENTIFICADOR_UNICO, codigo));
	    Document documentoMongoTipo = MongoHelper.recuperaDocumentoMongo(COLECAO_TIPO, filtroPeloId);
        return (Tipo) GsonHelper.obtenhaObjeto(documentoMongoTipo, Tipo.class);
    }

    public List<Tipo> tiposPeloNome(String nome) {
	    List<Document> documentosTiposPeloNome = MongoHelper.recuperaDocumentosMongo(COLECAO_TIPO, new Document(NOME_TIPO, Pattern.compile(nome)));
	    List<Tipo> tiposPeloNome = new ArrayList<Tipo>();

	    for (Document documentoTipo : documentosTiposPeloNome) {
		    Tipo tipo = (Tipo) GsonHelper.obtenhaObjeto(documentoTipo, Tipo.class);
		    tiposPeloNome.add(tipo);
	    }

	    return tiposPeloNome;
    }

}
