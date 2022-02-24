package com.neocaptainnemo.notesfeb17.domain;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class SharedPreferencesNotesRepository implements NotesRepository {

    private static final String KEY_NOTES = "KEY_NOTES";

    private static NotesRepository INSTANCE;
    private final SharedPreferences sharedPreferences;
    private final Gson gson = new Gson();

    private List<Note> data = new ArrayList<>();


    public SharedPreferencesNotesRepository(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences("NotesPreferences", Context.MODE_PRIVATE);

        String value = sharedPreferences.getString(KEY_NOTES, "[]");

        Type notesType = new TypeToken<List<Note>>() {
        }.getType();

        data.addAll(gson.fromJson(value, notesType));
    }

    public static NotesRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SharedPreferencesNotesRepository(context);
        }
        return INSTANCE;
    }

    @Override
    public void getNotes(Callback<List<Note>> callback) {

        callback.onSuccess(data);
    }

    @Override
    public void add(String title, String content, Callback<Note> callback) {

        Note addedNote = new Note(UUID.randomUUID().toString(), title, content, new Date());

        Type notesType = new TypeToken<List<Note>>() {
        }.getType();

        data.add(addedNote);

        sharedPreferences.edit()
                .putString(KEY_NOTES, gson.toJson(data, notesType))
                .apply();

        callback.onSuccess(addedNote);
    }

    @Override
    public void delete(Note note, Callback<Void> callback) {

    }

    @Override
    public void update(String id, String newTitle, String newContent, Callback<Note> callback) {

    }
}
