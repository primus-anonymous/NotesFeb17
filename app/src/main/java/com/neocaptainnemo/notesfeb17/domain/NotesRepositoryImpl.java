package com.neocaptainnemo.notesfeb17.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class NotesRepositoryImpl implements NotesRepository {

    @Override
    public List<Note> getNotes() {
        ArrayList<Note> result = new ArrayList<>();

        result.add(new Note(UUID.randomUUID().toString(), "Title 1", "Contnet 1", new Date()));
        result.add(new Note(UUID.randomUUID().toString(), "Title 2", "Contnet 2", new Date()));
        result.add(new Note(UUID.randomUUID().toString(), "Title 3", "Contnet 3", new Date()));
        result.add(new Note(UUID.randomUUID().toString(), "Title 4", "Contnet 4", new Date()));
        result.add(new Note(UUID.randomUUID().toString(), "Title 5", "Contnet 5", new Date()));
        result.add(new Note(UUID.randomUUID().toString(), "Title 6", "Contnet 6", new Date()));
        result.add(new Note(UUID.randomUUID().toString(), "Title 7", "Contnet 7", new Date()));
        result.add(new Note(UUID.randomUUID().toString(), "Title 8", "Contnet 8", new Date()));
        result.add(new Note(UUID.randomUUID().toString(), "Title 9", "Contnet 9", new Date()));
        result.add(new Note(UUID.randomUUID().toString(), "Title 10", "Contnet 10", new Date()));


        for (int i = 0; i < 1000; i++) {
            result.add(new Note(UUID.randomUUID().toString(), "Title 10", "Contnet 10", new Date()));

        }
        return result;

    }
}
