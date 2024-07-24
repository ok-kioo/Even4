package br.upe.ui;

import br.upe.controller.UserController;
import br.upe.persistence.User;
import br.upe.persistence.Persistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    private UserController userController;
    private User testUser;

    @BeforeEach
    public void setUp() {
        userController = new UserController();
        testUser = new User();

        // Simula a configuração inicial do UserController
        HashMap<String, Persistence> mockUserMap = new HashMap<>();
        mockUserMap.put("test-id", testUser);
        userController.setUserHashMap(mockUserMap);

        // Simula a configuração do User para ter um email e CPF padrão
        testUser.create("test@example.com", "12345678900");
        userController.setUserLog(testUser);
    }

    @Test
    public void testCreateUser() {
        userController.create("newuser@example.com", "09876543211");
        // Simula a recuperação do novo usuário
        String email = userController.getData("email");
        assertEquals("newuser@example.com", email);
    }

    @Test
    public void testUpdateUser() {
        userController.update("updateduser@example.com", "11223344556");
        String email = userController.getData("email");
        assertEquals("updateduser@example.com", email);
        String cpf = userController.getData("cpf");
        assertEquals("11223344556", cpf);
    }

    @Test
    public void testDeleteUser() {
        userController.delete("test-id", "id");
        HashMap<String, Persistence> userMap = userController.getUserHashMap();
        assertFalse(userMap.containsKey("test-id"));
    }

    @Test
    public void testLoginValidateSuccess() {
        boolean result = userController.loginValidate("test@example.com", "12345678900");
        assertTrue(result);
    }

    @Test
    public void testLoginValidateFailure() {
        boolean result = userController.loginValidate("wrong@example.com", "00000000000");
        assertFalse(result);
    }
}
