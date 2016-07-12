package br.ufg.inf.persistencia.repositorios;

import br.ufg.inf.es.saep.sandbox.dominio.*;
import br.ufg.inf.persistencia.helpers.GsonHelper;
import br.ufg.inf.persistencia.helpers.MongoHelper;
import org.bson.Document;

public class ParecerRepositoryJson implements ParecerRepository {

	private static final String COLECAO_PARECER = "parecer";
	private static final String COLECAO_RADOC = "radoc";
	private static final String RADOCS_PARECER = "radocs";
	private static final String FUNDAMENTACAO_PARECER = "fundamentacao";
	private static final String NOTAS_PARECER = "notas";
	private static final String AVALIAVEL_ORIGINAL = "original";
	private static final String IDENTIFICADOR_UNICO = "id";

	public void adicionaNota(String id, Nota nota) {
		Parecer parecerASerAtualizado = byId(id);

		if (parecerASerAtualizado == null) {
			throw new IdentificadorDesconhecido("O parecer à ser atualizado não existe no banco de dados");
		}

        removeNota(id, nota.getItemOriginal());
		Document filtroPeloId = GsonHelper.obtenhaDocumentoMongo(new Document(IDENTIFICADOR_UNICO, parecerASerAtualizado.getId()));
        Document adicaoNota = GsonHelper.obtenhaDocumentoMongo(new Document("$push", new Document(NOTAS_PARECER, nota)));
        MongoHelper.atualizaDocumentoMongo(COLECAO_PARECER, filtroPeloId, adicaoNota);
	}

	public void removeNota(String id, Avaliavel original) {
		Document filtroPeloId = GsonHelper.obtenhaDocumentoMongo(new Document(IDENTIFICADOR_UNICO, id));
		Document remocaoNota = GsonHelper.obtenhaDocumentoMongo(new Document("$pull", new Document(NOTAS_PARECER, new Document(AVALIAVEL_ORIGINAL, original))));
		MongoHelper.atualizaDocumentoMongo(COLECAO_PARECER, filtroPeloId, remocaoNota);
	}

	public void persisteParecer(Parecer parecer) {
		Document documentoMongoParecer = GsonHelper.obtenhaDocumentoMongo(parecer);
		Document filtroPeloId = GsonHelper.obtenhaDocumentoMongo(new Document(IDENTIFICADOR_UNICO, parecer.getId()));
		Document parecerJaExistente = MongoHelper.recuperaDocumentoMongo(COLECAO_PARECER, filtroPeloId);

		if (parecerJaExistente != null) {
			throw new IdentificadorExistente("Já existe na coleção um documento com o identificador do parecer informado");
		}

		MongoHelper.persistaDocumentoMongo(COLECAO_PARECER, documentoMongoParecer);
	}

	public void atualizaFundamentacao(String parecer, String fundamentacao) {
		Parecer parecerASerAtualizado = byId(parecer);

		if (parecerASerAtualizado == null) {
			throw new IdentificadorDesconhecido("O parecer à ser atualizado não existe no banco de dados");
		}

		Document documentoMongoParecer = GsonHelper.obtenhaDocumentoMongo(parecerASerAtualizado);
		Document alteracaoFundamentacao = GsonHelper.obtenhaDocumentoMongo(new Document("$set", new Document(FUNDAMENTACAO_PARECER, fundamentacao)));
		MongoHelper.atualizaDocumentoMongo(COLECAO_PARECER, documentoMongoParecer, alteracaoFundamentacao);
	}

	public Parecer byId(String id) {
		Document filtroPeloId = GsonHelper.obtenhaDocumentoMongo(new Document(IDENTIFICADOR_UNICO, id));
		Document documentoMongoParecer = MongoHelper.recuperaDocumentoMongo(COLECAO_PARECER, filtroPeloId);
		return (Parecer) GsonHelper.obtenhaObjeto(documentoMongoParecer, Parecer.class);
	}

	public void removeParecer(String id) {
		Document filtroPeloId = GsonHelper.obtenhaDocumentoMongo(new Document(IDENTIFICADOR_UNICO, id));
		MongoHelper.removeDocumentoMongo(COLECAO_PARECER, filtroPeloId);
	}

	public Radoc radocById(String identificador) {
		Document filtroPeloId = GsonHelper.obtenhaDocumentoMongo(new Document(IDENTIFICADOR_UNICO, identificador));
		Document documentoMongoRadoc = MongoHelper.recuperaDocumentoMongo(COLECAO_RADOC, filtroPeloId);
		return (Radoc) GsonHelper.obtenhaObjeto(documentoMongoRadoc, Radoc.class);
	}

	public String persisteRadoc(Radoc radoc) {
		Document documentoMongoRadoc = GsonHelper.obtenhaDocumentoMongo(radoc);
		Document filtroPeloId = GsonHelper.obtenhaDocumentoMongo(new Document(IDENTIFICADOR_UNICO, radoc.getId()));
		Document radocJaExistente = MongoHelper.recuperaDocumentoMongo(COLECAO_RADOC, filtroPeloId);

		if (radocJaExistente != null) {
			throw new IdentificadorExistente("Já existe na coleção um documento com o identificador do RADOC informado");
		}

		MongoHelper.persistaDocumentoMongo(COLECAO_RADOC, documentoMongoRadoc);
		radocJaExistente = MongoHelper.recuperaDocumentoMongo(COLECAO_RADOC, filtroPeloId);

		return radocJaExistente.get(IDENTIFICADOR_UNICO) != null ? radocJaExistente.get(IDENTIFICADOR_UNICO).toString() : null;
	}

	public void removeRadoc(String identificador) {
		Document filtroPeloId = GsonHelper.obtenhaDocumentoMongo(new Document(RADOCS_PARECER, identificador));
		Document parecerReferenciandoRadoc = MongoHelper.recuperaDocumentoMongo(COLECAO_PARECER, filtroPeloId);

		if (parecerReferenciandoRadoc != null) {
			throw new ExisteParecerReferenciandoRadoc("O RADOC não pode ser removido pois existe um parecer que o referencia");
		}

		filtroPeloId = GsonHelper.obtenhaDocumentoMongo(new Document(IDENTIFICADOR_UNICO, identificador));
		MongoHelper.removeDocumentoMongo(COLECAO_RADOC, filtroPeloId);
	}

}
