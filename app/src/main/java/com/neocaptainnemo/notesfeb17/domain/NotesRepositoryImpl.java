package com.neocaptainnemo.notesfeb17.domain;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NotesRepositoryImpl implements NotesRepository {

    public static final NotesRepository INSTANCE = new NotesRepositoryImpl();

    private Executor executor = Executors.newSingleThreadExecutor();

    private final List<Note> result = new ArrayList<>();

    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    public NotesRepositoryImpl() {

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

    }


    @Override
    public void getNotes(Callback<List<Note>> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(result);
                    }
                });
            }
        });

    }

    @Override
    public void add(String title, String content, Callback<Note> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Note note = new Note(UUID.randomUUID().toString(), title, content, new Date());

                result.add(note);

                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(note);
                    }
                });
            }
        });
    }

    @Override
    public void delete(Note note, Callback<Void> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                result.remove(note);

                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(null);
                    }
                });
            }
        });
    }


    @Override
    public Note update(String id, String newTitle, String newContent) {

        Note toChange = null;
        int indexToChange = -1;

        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).getId().equals(id)) {
                toChange = result.get(i);
                indexToChange = i;
                break;
            }
        }

        Note newNote = new Note(toChange.getId(), newTitle, newContent, toChange.getCreatedAt());

        result.set(indexToChange, newNote);

        return newNote;
    }
}
