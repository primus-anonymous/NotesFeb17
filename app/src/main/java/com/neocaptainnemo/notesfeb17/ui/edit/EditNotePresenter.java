package com.neocaptainnemo.notesfeb17.ui.edit;

import android.os.Bundle;

import com.neocaptainnemo.notesfeb17.R;
import com.neocaptainnemo.notesfeb17.domain.Callback;
import com.neocaptainnemo.notesfeb17.domain.Note;
import com.neocaptainnemo.notesfeb17.domain.NotesRepository;

public class EditNotePresenter extends AbstractNotePresenter {

    public static final String KEY_UPDATE = "KEY_UPDATE";

    private final Note toEdit;

    public EditNotePresenter(EditNoteView view, NotesRepository repository, Note note) {
        super(view, repository);
        toEdit = note;

        title = toEdit.getTitle();
        content = toEdit.getContent();
    }

    @Override
    void refresh() {
        view.setButtonTitle(R.string.btn_update);
        view.setNoteTitle(toEdit.getTitle());
        view.setNoteDescription(toEdit.getContent());
    }

    @Override
    void onActionButtonClicked() {
        view.showProgress();

        repository.update(toEdit.getId(), title, content, new Callback<Note>() {
            @Override
            public void onSuccess(Note data) {
                view.hideProgress();

                Bundle bundle = new Bundle();
                bundle.putParcelable(EditNoteBottomSheetDialogFragment.ARG_NOTE, data);

                view.publishResult(KEY_UPDATE, bundle);
            }
        });

    }
}
