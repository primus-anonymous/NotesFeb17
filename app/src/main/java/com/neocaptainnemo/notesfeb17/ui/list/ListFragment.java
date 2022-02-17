package com.neocaptainnemo.notesfeb17.ui.list;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neocaptainnemo.notesfeb17.R;
import com.neocaptainnemo.notesfeb17.domain.Note;
import com.neocaptainnemo.notesfeb17.domain.NotesRepository;
import com.neocaptainnemo.notesfeb17.domain.NotesRepositoryImpl;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ListFragment extends Fragment {

    private NotesRepository repository = new NotesRepositoryImpl();

    public ListFragment() {
        super(R.layout.fragment_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView list = view.findViewById(R.id.list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);

        list.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL);

        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider));

        list.addItemDecoration(dividerItemDecoration);

        NotesListAdapter adapter = new NotesListAdapter();

        adapter.setOnNoteClicked(new NotesListAdapter.OnNoteClicked() {
            @Override
            public void onNoteClicked(Note note) {
                Toast.makeText(requireContext(), note.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        list.setAdapter(adapter);

        adapter.setData(repository.getNotes());

        adapter.notifyDataSetChanged();

//        LinearLayout container = view.findViewById(R.id.container);
//
//        for (Note note : repository.getNotes()) {
//
//            View itemView = getLayoutInflater().inflate(R.layout.item_note, container, false);
//
//            itemView.findViewById(R.id.card).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(requireContext(), note.getTitle(), Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            TextView title = itemView.findViewById(R.id.title);
//            TextView content = itemView.findViewById(R.id.content);
//            TextView date = itemView.findViewById(R.id.date);
//
//            title.setText(note.getTitle());
//            content.setText(note.getContent());
//            date.setText(format.format(note.getCreatedAt()));
//
//            container.addView(itemView);
//
//        }
    }
}
