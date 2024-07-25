package br.upe.persistence;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

public class SubmitArticle implements Persistence {

    private final String baseDir = "C:\\Users\\deyvi\\even4\\db\\Articles";

    private String eventDir;

    public SubmitArticle(String eventName) {
        // Define o diretório do evento
        this.eventDir = Paths.get(baseDir+"\\"+eventName).toString();
    }

    public void create(Object... params) {
        if (params.length != 2) {
            throw new IllegalArgumentException("Número inválido de parâmetros. Esperado: 2 (caminho do arquivo e nome do evento).");
        }
        String sourcePath = (String) params[0];
        String eventName = (String) params[1];

        // Atualiza o diretório do evento
        this.eventDir = Paths.get(baseDir, eventName).toString();

        // Verifica se o diretório do evento existe, se não cria
        File directory = new File(eventDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        Path source = Paths.get(sourcePath);
        Path destination = Paths.get(eventDir, source.getFileName().toString());

        try {
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Artigo criado com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao criar artigo: " + e.getMessage());
        }
    }

    @Override
    public void delete(Object... params) {
        if (params.length != 2) {
            throw new IllegalArgumentException("Número inválido de parâmetros.");
        }
        String articleName = (String) params[0];
        String eventName = (String) params[1];
        Path articlePath = Paths.get(baseDir, eventName, articleName);

        try {
            if (Files.exists(articlePath)) {
                Files.delete(articlePath);
                System.out.println("Artigo deletado com sucesso.");
            } else {
                System.out.println("Artigo não encontrado.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao deletar artigo: " + e.getMessage());
        }
    }

    @Override
    public void update(Object... params) {
        if (params.length != 3) {
            throw new IllegalArgumentException("Número inválido de parâmetros.");
        }
        String oldArticleName = (String) params[0];
        String newSourcePath = (String) params[1];
        String eventName = (String) params[2];
        Path oldArticlePath = Paths.get(baseDir, eventName, oldArticleName);
        Path newSource = Paths.get(newSourcePath);
        Path newArticlePath = Paths.get(baseDir, eventName, newSource.getFileName().toString());

        try {
            if (Files.exists(oldArticlePath)) {
                Files.delete(oldArticlePath);
                Files.copy(newSource, newArticlePath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Artigo atualizado com sucesso.");
            } else {
                System.out.println("Artigo antigo não encontrado.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao atualizar artigo: " + e.getMessage());
        }
    }

    @Override
    public String getData(String dataToGet) {
        return "";
    }

    @Override
    public void setData(String dataToSet, String data) {
        // Implementar conforme necessário
    }

    @Override
    public HashMap<String, Persistence> read() {
        return null;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public void setName(String name) {
        // Implementar conforme necessário
    }
}
