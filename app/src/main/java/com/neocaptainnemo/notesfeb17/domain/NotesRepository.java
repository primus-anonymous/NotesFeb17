package com.neocaptainnemo.notesfeb17.domain;

import java.util.List;

public interface NotesRepository {

    void getNotes(Callback<List<Note>> callback);

    void add(String title, String content, Callback<Note> callback);

    void delete(Note note, Callback<Void> callback);

    void update(String id, String newTitle, String newContent, Callback<Note> callback);
}
