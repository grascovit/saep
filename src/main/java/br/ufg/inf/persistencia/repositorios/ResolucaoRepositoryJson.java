package br.ufg.inf.persistencia.repositorios;

import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.dominio.ResolucaoRepository;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;

import java.util.List;

public class ResolucaoRepositoryJson implements ResolucaoRepository {

    public Resolucao byId(String id) {
        return null;
    }

    public String persiste(Resolucao resolucao) {
        return null;
    }

    public boolean remove(String identificador) {
        return false;
    }

    public List<String> resolucoes() {
        return null;
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
