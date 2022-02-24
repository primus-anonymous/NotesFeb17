package com.neocaptainnemo.notesfeb17.ui.edit;

import android.text.TextUtils;

import com.neocaptainnemo.notesfeb17.domain.NotesRepository;

public abstract class AbstractNotePresenter {

    protected EditNoteView view;
    protected NotesRepository repository;
    protected String title;
    protected String content;
    public AbstractNotePresenter(EditNoteView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void onTitleChanged(String title) {

        this.title = title;

        view.setActionButtonEnabled(!TextUtils.isEmpty(content) && !TextUtils.isEmpty(title));
    }

    public void onContentChanged(String content) {

        this.content = content;

        view.setActionButtonEnabled(!TextUtils.isEmpty(content) && !TextUtils.isEmpty(title));
    }

    abstract void refresh();

    abstract void onActionButtonClicked();
}
