package com.neocaptainnemo.notesfeb17.ui.list;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neocaptainnemo.notesfeb17.R;
import com.neocaptainnemo.notesfeb17.domain.Note;
import com.neocaptainnemo.notesfeb17.domain.NotesRepositoryImpl;
import com.neocaptainnemo.notesfeb17.ui.edit.EditNoteBottomSheetDialogFragment;

import java.util.List;

public class ListFragment extends Fragment implements ListView {

    private NotesListAdapter adapter;

    private RecyclerView list;

    private ListPresenter presenter;

    private ProgressBar progressBar;

    public ListFragment() {
        super(R.layout.fragment_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new ListPresenter(NotesRepositoryImpl.INSTANCE, this);

        list = view.findViewById(R.id.list);

        progressBar = view.findViewById(R.id.progress);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);

        list.setLayoutManager(layoutManager);

        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();

        defaultItemAnimator.setSupportsChangeAnimations(true);
        defaultItemAnimator.setRemoveDuration(600L);

        list.setItemAnimator(defaultItemAnimator);

        adapter = new NotesListAdapter(this);

        adapter.setOnNoteClicked(new NotesListAdapter.OnNoteClicked() {
            @Override
            public void onNoteClicked(Note note) {
                Toast.makeText(requireContext(), note.getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNoteLongClicked(Note note, int position) {
                presenter.setSelectedNote(note);
                presenter.setSelectedNoteIndex(position);
            }
        });

        list.setAdapter(adapter);

        view.findViewById(R.id.add_note).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter.addItem();
            }
        });

        getParentFragmentManager().setFragmentResultListener(EditNoteBottomSheetDialogFragment.KEY_REQUEST, getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Note note = result.getParcelable(EditNoteBottomSheetDialogFragment.ARG_NOTE);

                adapter.updateItem(note, presenter.getSelectedNoteIndex());

                adapter.notifyItemChanged(presenter.getSelectedNoteIndex());
            }
        });

        presenter.requestNotes();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        requireActivity().getMenuInflater().inflate(R.menu.menu_notes_list_context, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:

                EditNoteBottomSheetDialogFragment.newInstance(presenter.getSelectedNote())
                        .show(getParentFragmentManager(), "EditNoteBottomSheetDialogFragment");

                return true;
            case R.id.action_delete:

                presenter.deleteItem();

                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void showNotes(List<Note> notes) {
        adapter.setData(notes);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void addNote(Note note) {
        int index = adapter.addItem(note);

        adapter.notifyItemInserted(index);

        list.smoothScrollToPosition(index);
    }

    @Override
    public void removeNote(Note note, int index) {
        adapter.removeItem(index);

        adapter.notifyItemRemoved(index);

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }
}
