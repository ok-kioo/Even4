package br.upe.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.upe.controller.AttendeeController;
import br.upe.controller.EventController;
import br.upe.controller.SessionController;
import br.upe.controller.UserController;
import br.upe.persistence.Attendee;
import br.upe.persistence.Persistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class AttendeeControllerTest {

    private AttendeeController attendeeController;

    @BeforeEach
    public void setUp() {
        attendeeController = new AttendeeController();

    }

    @Test
    public void testCreateAttendee() throws FileNotFoundException {
        UserController userController = new UserController();
        EventController eventController = new EventController();
        SessionController sessionController = new SessionController();
        userController.create("newuser@example.com", "09876543211");
        if (userController.loginValidate("newuser@example.com", "09876543211")) {
            userController.setUserLog(userController.getUserHashMap().values().iterator().next());
        }

        eventController.create("Test Event", "31/12/2024", "Description", "Location", "owner-id");
        sessionController.create("Test Event", "SessionId1", "01/12/2024", "Session Description", "Session Location", "08:00", "10:00", "owner-id", "Event");
        attendeeController.create("John", "353738", userController.getData("id"));

        HashMap<String, Persistence> attendees = attendeeController.getAttendeeHashMap();
        boolean attendeeExists = attendees.values().stream().anyMatch(a -> a.getData("name").equals("John"));
        assertTrue(attendeeExists, "O participante não foi criado corretamente.");
    }


    @Test
    public void testReadAttendees() {
        attendeeController.read();

        HashMap<String, Persistence> attendees = attendeeController.getAttendeeHashMap();
        assertTrue(attendees != null && !attendees.isEmpty(), "A leitura dos participantes falhou.");
    }

    @Test
    public void testUpdateAttendee() throws FileNotFoundException {
        UserController userController = new UserController();
        SessionController sessionController = new SessionController();
        EventController eventController = new EventController();
        AttendeeController attendeeController = new AttendeeController();  // Certifique-se de inicializar este controlador

        userController.create("newuser@example.com", "09876543211");

        // Simular login e configurar userLog
        if (userController.loginValidate("newuser@example.com", "09876543211")) {
            userController.setUserLog(userController.getUserHashMap().values().iterator().next());
        }

        eventController.create("Test Event", "31/12/2024", "Description", "Location", "owner-id");
        sessionController.create("Test Event", "SessionId1", "01/12/2024", "Session Description", "Session Location", "08:00", "10:00", "owner-id", "Event");

        // Criar o participante
        attendeeController.create("John", "353738", userController.getData("id"));

        // Atualizar o participante
        attendeeController.update("Jane", "353738");

        // Verificar se o participante foi atualizado
        HashMap<String, Persistence> attendees = attendeeController.getAttendeeHashMap();
        boolean attendeeUpdated = attendees.values().stream().anyMatch(a -> a.getData("name").equals("Jane"));
        assertTrue(attendeeUpdated, "O participante não foi atualizado corretamente.");
    }


    @Test
    public void testDeleteAttendee() throws FileNotFoundException {
        attendeeController.create("John Doe", "sessionId1", "userId1");
        attendeeController.read();

        attendeeController.delete("userId1", "id", "sessionId1");

        HashMap<String, Persistence> attendees = attendeeController.getAttendeeHashMap();
        boolean attendeeDeleted = attendees.values().stream().noneMatch(a -> a.getData("name").equals("John Doe"));
        assertTrue(attendeeDeleted, "O participante não foi deletado corretamente.");
    }
}
