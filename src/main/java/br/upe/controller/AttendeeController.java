package br.upe.controller;
import br.upe.persistence.Attendee;
import br.upe.persistence.Event;
import br.upe.persistence.Persistence;
import br.upe.persistence.SubEvent;


import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class AttendeeController implements Controller {
    private HashMap<String, Persistence> eventHashMap;
    private Persistence EventLog;


    public void AttendeeController() {
        this.read();
    }

    public HashMap<String, Persistence> getEventHashMap() {
        return eventHashMap;
    }


    public void setEventHashMap(HashMap<String, Persistence> eventHashMap) {
        this.eventHashMap = eventHashMap;
    }

    @Override
    public boolean list(String ownerId){
        boolean isnull = true;
        try {
            boolean found = false;
            for (Map.Entry<String, Persistence> entry : eventHashMap.entrySet()) {
                Persistence persistence = entry.getValue();
                if (persistence.getData("ownerId").equals(ownerId)){
                    System.out.println(persistence.getData("name"));
                    found = true;
                    isnull = false;
                }
            }
            if (!found){
                System.out.println("Seu usuário atual é organizador de nenhum evento");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isnull;
    }

    @Override
    public void show(String id) {
        for (Map.Entry<String, Persistence> entry : eventHashMap.entrySet()) {
            Persistence persistence = entry.getValue();
            if (!persistence.getData("ownerId").equals(id)){
                System.out.println(persistence.getData("name") + " - " + persistence.getData("id"));
            }
        }
    }

    @Override
    public void update(Object... params) throws FileNotFoundException {
        if (params.length != 2) {
            System.out.println("Só pode ter 6 parametros");
            return;
        }

        String oldName = (String) params[0];
        String newName = (String) params[1];
        String newDate = (String) params[2];
        String newDescription = (String) params[3];
        String newLocation = (String) params[4];
        String userId = (String) params[5];

        boolean isOwner = false;
        String id = null;

        for (Map.Entry<String, Persistence> entry : eventHashMap.entrySet()) {
            Persistence persistence = entry.getValue();
            String name = persistence.getData("name");
            String ownerId = persistence.getData("ownerId");

            if (name != null && name.equals(oldName) && ownerId != null && ownerId.equals(userId)) {
                isOwner = true;
                id = persistence.getData("id");
                break;
            }
        }

        if (isOwner) {
            boolean nameExists = false;
            for (Map.Entry<String, Persistence> entry : eventHashMap.entrySet()) {
                Persistence subEvent = entry.getValue();
                String name = subEvent.getData("name");
                if (name != null && name.equals(newName)) {
                    nameExists = true;
                    break;
                }
            }

            if (nameExists) {
                System.out.println("Nome em uso");
                return;
            }

            if (id != null) {
                Persistence newSubEvent = eventHashMap.get(id);
                if (newSubEvent != null) {
                    newSubEvent.setData("name", newName);
                    newSubEvent.setData("date", newDate);
                    newSubEvent.setData("description", newDescription);
                    newSubEvent.setData("location", newLocation);
                    eventHashMap.put(id, newSubEvent);
                    Persistence subEventPersistence = new SubEvent();
                    subEventPersistence.update(eventHashMap);
                } else {
                    System.out.println("SubEvento não encontrado");
                }
            } else {
                System.out.println("Você não pode alterar este SubEvento");
            }
        } else {
            System.out.println("Você não pode alterar este SubEvento");
        }
    }


    @Override
    public void read() {
        Persistence eventPersistence = (Persistence) new Event();
        this.eventHashMap = eventPersistence.read();
    }

    @Override
    public boolean loginValidate(String email, String cpf) {
        return false;
    }


    @Override
    public String getData(String dataToGet) {
        return null;
    }

    @Override
    public void SubmitArticleController(String string) {

    }

    @Override
    public void create(Object... params) {
        if (params.length != 2) {
            System.out.println("Só pode ter 2 parâmetros");
            return;
        }

        String name = (String) params[0];
        String userId = (String) params[1];
        String sessionId = (String) params[2];

        Persistence attendee = new Attendee();
        attendee.create(userId, userId, sessionId);

    }


    @Override
    public void delete(Object... params) {
        String ownerId = "";
        for (Map.Entry<String, Persistence> entry : eventHashMap.entrySet()) {
            Persistence persistence = entry.getValue();
            if (persistence.getData("name").equals((String) params[0])){
                ownerId = persistence.getData("ownerId");
            }
        }

        if (((String) params[1]).equals("name") && ((String) params[2]).equals(ownerId)) {
            Iterator<Map.Entry<String, Persistence>> iterator = eventHashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Persistence> entry = iterator.next();
                Persistence eventindice = entry.getValue();
                if (eventindice.getData("name").equals((String) params[0])) {
                    iterator.remove();
                }
            }
            Persistence eventPersistence = new Event();
            eventPersistence.delete(eventHashMap);
        } else {
            System.out.println("Você não pode deletar esse evento");
        }
    }
    private String getSessionId(String searchId) {
        AttendeeController ac = new AttendeeController() {
            @Override
            public void SubmitArticleController(String string) {

            }
        };
        String sessionId = "";
        HashMap<String, Persistence> list = ac.getEventHashMap();
        boolean found = false;
        for (Map.Entry<String, Persistence> entry : list.entrySet()) {
            Persistence listIndice = entry.getValue();
            if (listIndice.getData("name").equals(searchId)) {
                sessionId = listIndice.getData("id");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Participante não encontrado\n");
        }

        return sessionId;
    }

    private String getFatherOwnerId(String eventId) {
        AttendeeController ac = new AttendeeController() {
            @Override
            public void SubmitArticleController(String string) {

            }
        };
        String fatherOwnerId = "";
        HashMap<String, Persistence> list = ac.getEventHashMap();
        for (Map.Entry<String, Persistence> entry : list.entrySet()) {
            Persistence listIndice = entry.getValue();
            if (listIndice.getData("id").equals(eventId)) {
                fatherOwnerId = listIndice.getData("ownerId");
                break;
            }
        }
        return fatherOwnerId;
    }
}
