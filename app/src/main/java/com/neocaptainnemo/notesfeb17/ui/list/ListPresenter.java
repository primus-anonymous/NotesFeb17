package com.neocaptainnemo.notesfeb17.ui.list;

import com.neocaptainnemo.notesfeb17.domain.Callback;
import com.neocaptainnemo.notesfeb17.domain.Note;
import com.neocaptainnemo.notesfeb17.domain.NotesRepository;

import java.util.List;

public class ListPresenter {

    private NotesRepository repository;
    private ListView view;

    private Note selectedNote;
    private int selectedNoteIndex;

    public ListPresenter(NotesRepository repository, ListView view) {
        this.repository = repository;
        this.view = view;
    }

    public Note getSelectedNote() {
        return selectedNote;
    }

    public void setSelectedNote(Note selectedNote) {
        this.selectedNote = selectedNote;
    }

    public void setSelectedNoteIndex(int selectedNoteIndex) {
        this.selectedNoteIndex = selectedNoteIndex;
    }

    public int getSelectedNoteIndex() {
        return selectedNoteIndex;
    }


    public void deleteItem() {

        view.showProgress();

        repository.delete(selectedNote, new Callback<Void>() {
            @Override
            public void onSuccess(Void data) {
                view.hideProgress();

                view.removeNote(selectedNote, selectedNoteIndex);
            }
        });

    }

    public void requestNotes() {

        view.showProgress();

        repository.getNotes(new Callback<List<Note>>() {
            @Override
            public void onSuccess(List<Note> data) {

                view.showNotes(data);
                view.hideProgress();
            }
        });
    }
}
