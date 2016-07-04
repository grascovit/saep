package br.ufg.inf.persistencia.repositorios;

import br.ufg.inf.es.saep.sandbox.dominio.Nota;
import br.ufg.inf.es.saep.sandbox.dominio.Parecer;
import br.ufg.inf.es.saep.sandbox.dominio.Pontuacao;
import br.ufg.inf.es.saep.sandbox.dominio.Valor;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ParecerRepositoryJsonTest {

    private ParecerRepositoryJson parecerRepositoryJson;

    @Before
    public void setUp() {
        parecerRepositoryJson = new ParecerRepositoryJson();
    }

    @Test
    public void adicionaNota() {

    }

    @Test
    public void removeNota() {

    }

    @Test
    public void persisteParecer() {
        Parecer parecer = obtenhaParecer();
        parecerRepositoryJson.persisteParecer(parecer);
    }

    @Test
    public void atualizaFundamentacao() {

    }

    @Test
    public void byId() {
        String identificador = "266955b4-73fa-4fad-9727-9a0a6fae42ee";
        parecerRepositoryJson.byId(identificador);
    }

    @Test
    public void removeParecer() {

    }

    @Test
    public void radocById() {

    }

    @Test
    public void persisteRadoc() {

    }

    @Test
    public void removeRadoc() {

    }

    private Parecer obtenhaParecer() {
        Parecer parecer = new Parecer();
        ArrayList<String> radocs = new ArrayList<String>();
        radocs.add("idRadoc");
        ArrayList<Pontuacao> pontuacoes = new ArrayList<Pontuacao>();
        pontuacoes.add(new Pontuacao("fatorCargaHoraria", new Valor(5.0f)));
        ArrayList<Nota> notas = new ArrayList<Nota>();
        notas.add(new Nota(new Pontuacao("pontuacaoAntiga", new Valor(1.0f)), new Pontuacao("pontuacaoNova", new Valor(10.0f)), "Justificativa para a nota"));
        return new Parecer(parecer.getId(), "idDaResolucao", radocs, pontuacoes, "Fundamentação de exemplo com caractéres Unicode", notas);
    }

}