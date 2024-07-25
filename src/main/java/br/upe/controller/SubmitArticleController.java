package br.upe.controller;

import br.upe.persistence.SubmitArticle;

import java.io.File;

public class SubmitArticleController implements Controller {

    @Override
    public void create(Object... params) {
        if (params.length != 2) {
            throw new IllegalArgumentException("Número inválido de parâmetros.");
        }
        String sourcePath = (String) params[0];
        String eventName = (String) params[1];

        // Verifique se o arquivo existe antes de passar para a persistência
        File file = new File(sourcePath);
        if (!file.exists() || !file.isFile()) {
            System.out.println("O caminho fornecido não é um arquivo válido.");
            return;
        }

        SubmitArticle submitArticlePersistence = new SubmitArticle(eventName);
        submitArticlePersistence.create(sourcePath, eventName);
    }

    @Override
    public void update(Object... params) {
        if (params.length != 3) {
            throw new IllegalArgumentException("Número inválido de parâmetros.");
        }
        String oldArticleName = (String) params[0];
        String newSourcePath = (String) params[1];
        String eventName = (String) params[2];
        SubmitArticle submitArticlePersistence = new SubmitArticle(eventName);
        submitArticlePersistence.update(oldArticleName, newSourcePath, eventName);
    }

    @Override
    public void read() {
        // Implementar conforme necessário
    }

    @Override
    public void delete(Object... params) {
        if (params.length != 2) {
            throw new IllegalArgumentException("Número inválido de parâmetros.");
        }
        String articleName = (String) params[0];
        String eventName = (String) params[1];
        SubmitArticle submitArticlePersistence = new SubmitArticle(eventName);
        submitArticlePersistence.delete(articleName, eventName);
    }

    @Override
    public boolean list(String idowner) {
        // Implementar conforme necessário
        return false;
    }

    @Override
    public void show(String id) {
        // Implementar conforme necessário
    }

    @Override
    public boolean loginValidate(String email, String cpf) {
        // Implementar conforme necessário
        return false;
    }

    @Override
    public String getData(String dataToGet) {
        // Implementar conforme necessário
        return "";
    }

    @Override
    public void SubmitArticleController(String string) {
        // Implementar conforme necessário
    }
}
