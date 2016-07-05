package br.ufg.inf.persistencia.adapters;

import br.ufg.inf.es.saep.sandbox.dominio.Avaliavel;
import br.ufg.inf.es.saep.sandbox.dominio.Pontuacao;
import br.ufg.inf.es.saep.sandbox.dominio.Relato;
import com.google.gson.*;

import java.lang.reflect.Type;

public class AvaliavelAdapter implements JsonSerializer<Avaliavel>, JsonDeserializer<Avaliavel> {

	public Avaliavel deserialize(JsonElement json, Type tipoClasse, JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		Type tipo = jsonObject.has("atributo") ? Pontuacao.class : Relato.class;
		return context.deserialize(json, tipo);
	}

	public JsonElement serialize(Avaliavel src, Type typeOfSrc, JsonSerializationContext context) {
		Type tipo = src.getClass();
		return context.serialize(src, tipo);
	}

}