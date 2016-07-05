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
        String json = gson.toJson(objeto);
        return Document.parse(json);
    }

    public static Object obtenhaObjeto(Document documentoMongo, Type tipoObjeto) {
        String json = documentoMongo.toJson();
        return gson.fromJson(json, tipoObjeto);
    }

}
