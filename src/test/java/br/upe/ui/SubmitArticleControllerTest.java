package br.upe.ui;

import br.upe.persistence.Persistence;
import br.upe.persistence.SubmitArticle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SubmitArticleControllerTest {
    private SubmitArticle submitArticle;
    private File testDirectory;
    private File testFile;

    @BeforeEach
    public void setUp() throws IOException {
        submitArticle = new SubmitArticle();
        testDirectory = new File(".\\even4\\db\\Articles\\TestEvent");
        testFile = new File(testDirectory, "testArticle.txt");

        // Create test directory and file
        if (!testDirectory.exists()) {
            testDirectory.mkdirs();
        }
        if (!testFile.exists()) {
            Files.createFile(testFile.toPath());
        }
    }

    @Test
    public void testCreate() {
        // Arrange
        String eventName = "TestEvent";
        String filePath = testFile.getAbsolutePath();

        // Act
        submitArticle.create(eventName, filePath);

        // Assert
        File newFile = new File(testDirectory, "testArticle.txt");
        assertTrue(newFile.exists(), "O arquivo deve existir após a operação de criação.");
    }

    @Test
    public void testRead() {
        // Arrange
        String eventName = "TestEvent";
        HashMap<String, Persistence> articles = submitArticle.read(eventName);

        // Act
        SubmitArticle article = (SubmitArticle) articles.get("testArticle.txt");

        // Assert
        assertEquals("testArticle.txt", article.getData("name"));
        assertEquals(testFile.getAbsolutePath(), article.getData("path"));
    }

    @Test
    public void testUpdate() {
        // Arrange
        String newFilePath = ".\\even4\\db\\Articles\\TestEvent\\updatedArticle.txt";
        File newFile = new File(newFilePath);

        // Create new file for testing
        try {
            if (!newFile.exists()) {
                Files.createFile(newFile.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Act
        submitArticle.update("testArticle.txt", newFilePath);

        // Assert
        File updatedFile = new File(testDirectory, "updatedArticle.txt");
        assertTrue(updatedFile.exists(), "O arquivo deve ser atualizado para o novo nome.");
    }

    @Test
    public void testDelete() {
        // Arrange
        String fileName = "testArticle.txt";

        // Act
        submitArticle.delete(fileName);

        // Assert
        File deletedFile = new File(testDirectory, fileName);
        assertTrue(!deletedFile.exists(), "O arquivo deve ser deletado após a operação de exclusão.");
    }
}
