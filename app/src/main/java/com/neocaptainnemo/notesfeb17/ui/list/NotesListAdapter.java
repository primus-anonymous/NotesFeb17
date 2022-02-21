package com.neocaptainnemo.notesfeb17.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.neocaptainnemo.notesfeb17.R;
import com.neocaptainnemo.notesfeb17.domain.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NoteViewHolder> {

    private Fragment fragment;
    private final List<Note> data = new ArrayList<>();
    private final SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
    private OnNoteClicked onNoteClicked;

    public NotesListAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public OnNoteClicked getOnNoteClicked() {
        return onNoteClicked;
    }

    public void setOnNoteClicked(OnNoteClicked onNoteClicked) {
        this.onNoteClicked = onNoteClicked;
    }

    public void setData(Collection<Note> toSet) {
        data.clear();
        data.addAll(toSet);
    }


    public int addItem(Note toAdd) {
        data.add(toAdd);

        return data.size() - 1;
    }

    public void removeItem(int selectedNoteIndex) {
        data.remove(selectedNoteIndex);
    }

    public void updateItem(Note note, int selectedNoteIndex) {
        data.set(selectedNoteIndex, note);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note item = data.get(position);

        holder.title.setText(item.getTitle());
        holder.content.setText(item.getContent());

        holder.date.setText(format.format(item.getCreatedAt()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    interface OnNoteClicked {
        void onNoteClicked(Note note);

        void onNoteLongClicked(Note note, int position);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView content;
        TextView date;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            View card = itemView.findViewById(R.id.card);

            fragment.registerForContextMenu(card);

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getOnNoteClicked() != null) {

                        int clickedAt = getAdapterPosition();

                        getOnNoteClicked().onNoteClicked(data.get(clickedAt));
                    }

                }
            });

            card.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    if (getOnNoteClicked() != null) {

                        int clickedAt = getAdapterPosition();

                        getOnNoteClicked().onNoteLongClicked(data.get(clickedAt), clickedAt);
                    }

                    view.showContextMenu();

                    return true;
                }
            });

            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            date = itemView.findViewById(R.id.date);

        }
    }
}
