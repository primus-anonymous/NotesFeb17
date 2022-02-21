package com.neocaptainnemo.notesfeb17.ui.list;

import com.neocaptainnemo.notesfeb17.domain.Note;

import java.util.List;

public interface ListView {

    void showNotes(List<Note> notes);

    void addNote(Note note);

    void removeNote(Note note, int index);

    void showProgress();

    void hideProgress();
}
