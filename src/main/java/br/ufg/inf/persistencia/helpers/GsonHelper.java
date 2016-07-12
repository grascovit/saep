package br.ufg.inf.persistencia.helpers;

import br.ufg.inf.es.saep.sandbox.dominio.Avaliavel;
import br.ufg.inf.persistencia.adapters.AvaliavelAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.Document;

import java.lang.reflect.Type;

public class GsonHelper {

    private static GsonBuilder builder = new GsonBuilder().registerTypeAdapter(Avaliavel.class, new AvaliavelAdapter());
    private static Gson gson = builder.create();

    public static Document obtenhaDocumentoMongo(Object objeto) {
        String json = objeto != null ? gson.toJson(objeto) : null;
        return Document.parse(json);
    }

    public static Object obtenhaObjeto(Document documentoMongo, Type tipoObjeto) {
        String json = documentoMongo != null ? documentoMongo.toJson() : null;
        return gson.fromJson(json, tipoObjeto);
    }

}
