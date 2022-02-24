package com.neocaptainnemo.notesfeb17.ui.edit;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.neocaptainnemo.notesfeb17.R;
import com.neocaptainnemo.notesfeb17.domain.FirestoreNotesRepository;
import com.neocaptainnemo.notesfeb17.domain.InMemoryNotesRepositoryImpl;
import com.neocaptainnemo.notesfeb17.domain.Note;
import com.neocaptainnemo.notesfeb17.domain.NotesRepository;
import com.neocaptainnemo.notesfeb17.domain.SharedPreferencesNotesRepository;

public class EditNoteBottomSheetDialogFragment extends BottomSheetDialogFragment implements EditNoteView {

    public static final String ARG_NOTE = "ARG_NOTE";

    private Button actionButton;
    private EditText title;
    private EditText content;
    private ProgressBar progressBar;
    private AbstractNotePresenter presenter;

    public static EditNoteBottomSheetDialogFragment newUpdateInstance(Note note) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);

        EditNoteBottomSheetDialogFragment fragment = new EditNoteBottomSheetDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static EditNoteBottomSheetDialogFragment newAddInstance() {
        return new EditNoteBottomSheetDialogFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_note_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        actionButton = view.findViewById(R.id.action_button);
        progressBar = view.findViewById(R.id.progress);

        title = view.findViewById(R.id.edit_title);

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.onTitleChanged(editable.toString());
            }
        });

        content = view.findViewById(R.id.edit_content);

        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.onContentChanged(editable.toString());
            }
        });

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onActionButtonClicked();
            }
        });

        if (getArguments() != null && getArguments().containsKey(ARG_NOTE)) {
            Note note = requireArguments().getParcelable(ARG_NOTE);
            presenter = new EditNotePresenter(this, FirestoreNotesRepository.INSTANCE, note);
        } else {
            presenter = new AddNotePresenter(this, FirestoreNotesRepository.INSTANCE);
        }

        presenter.refresh();
    }

    @Override
    public void setButtonTitle(int title) {
        actionButton.setText(title);
    }

    @Override
    public void setNoteTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void setNoteDescription(String contentStr) {
        content.setText(contentStr);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setActionButtonEnabled(boolean isEnabled) {
        actionButton.setEnabled(isEnabled);
    }

    @Override
    public void publishResult(String key, Bundle bundle) {

        getParentFragmentManager()
                .setFragmentResult(key, bundle);

        dismiss();
    }
}
