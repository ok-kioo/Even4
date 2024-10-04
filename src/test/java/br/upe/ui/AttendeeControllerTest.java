package br.upe.ui;

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

import static org.junit.jupiter.api.Assertions.*;

public class AttendeeControllerTest {

    private UserController userController;
    private EventController eventController;
    private SessionController sessionController;
    private AttendeeController attendeeController;

    @BeforeEach
    public void setUp() {
        attendeeController = new AttendeeController();
        userController = new UserController();
        eventController = new EventController();
        sessionController = new SessionController();
    }

    @Test
    public void testCreateAttendee() throws FileNotFoundException {
        attendeeController.create("Man", "353738", "353730");
        attendeeController.read();

        HashMap<String, Persistence> attendees = attendeeController.getAttendeeHashMap();
        boolean attendeeExists = attendees.values().stream().anyMatch(a -> a.getData("name").equals("Man"));
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
        attendeeController.create("Man", "353738", "353730");
        attendeeController.update("Jane", "353738");
        attendeeController.read();

        HashMap<String, Persistence> attendees = attendeeController.getAttendeeHashMap();
        boolean attendeeUpdated = attendees.values().stream().anyMatch(a -> a.getData("name").equals("Jane"));
        assertTrue(attendeeUpdated, "O participante não foi atualizado corretamente.");
    }

    @Test
    public void testDeleteAttendee() throws FileNotFoundException {
        userController.create("newuser@example.com", "09876543211");

        if (userController.loginValidate("newuser@example.com", "09876543211")) {
            userController.setUserLog(userController.getUserHashMap().values().iterator().next());
        }

        attendeeController.create("James", "353738", "353730");
        attendeeController.read();

        attendeeController.delete("353730", "id", "353738");

        HashMap<String, Persistence> attendees = attendeeController.getAttendeeHashMap();
        boolean attendeeDeleted = attendees.values().stream().noneMatch(a -> a.getData("name").equals("James"));
        assertTrue(attendeeDeleted, "O participante não foi deletado corretamente.");
    }
}
