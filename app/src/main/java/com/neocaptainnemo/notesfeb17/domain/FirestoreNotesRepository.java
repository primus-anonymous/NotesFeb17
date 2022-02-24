package com.neocaptainnemo.notesfeb17.domain;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirestoreNotesRepository implements NotesRepository {

    public static final NotesRepository INSTANCE = new FirestoreNotesRepository();
    private static final String NOTES = "notes";
    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static final String CREATED_AT = "createdAt";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void getNotes(Callback<List<Note>> callback) {
        db.collection(NOTES)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();

                        ArrayList<Note> result = new ArrayList<>();

                        for (DocumentSnapshot snapshot : documents) {
                            String id = snapshot.getId();

                            String title = snapshot.getString(TITLE);
                            String content = snapshot.getString(CONTENT);
                            Date createdAt = snapshot.getDate(CREATED_AT);

                            result.add(new Note(id, title, content, createdAt));
                        }

                        callback.onSuccess(result);

                    }
                });
    }

    @Override
    public void add(String title, String content, Callback<Note> callback) {

        Date createdAt = new Date();

        Map<String, Object> data = new HashMap<>();
        data.put(TITLE, title);
        data.put(CONTENT, content);
        data.put(CREATED_AT, createdAt);

        db.collection(NOTES)
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        String id = documentReference.getId();

                        callback.onSuccess(new Note(id, title, content, createdAt));

                    }
                });
    }

    @Override
    public void delete(Note note, Callback<Void> callback) {

        db.collection(NOTES)
                .document(note.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        callback.onSuccess(unused);
                    }
                });
    }

    @Override
    public void update(String id, String newTitle, String newContent, Callback<Note> callback) {

        Map<String, Object> data = new HashMap<>();
        data.put(TITLE, newTitle);
        data.put(CONTENT, newContent);

        db.collection(NOTES)
                .document(id)
                .update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        callback.onSuccess(new Note(id, newTitle, newContent, new Date()));

                    }
                });

    }
}
