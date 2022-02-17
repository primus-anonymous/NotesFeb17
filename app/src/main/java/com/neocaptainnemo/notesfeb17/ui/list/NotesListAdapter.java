package com.neocaptainnemo.notesfeb17.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.neocaptainnemo.notesfeb17.R;
import com.neocaptainnemo.notesfeb17.domain.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NoteViewHolder> {

    private final List<Note> data = new ArrayList<>();
    private final SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
    private OnNoteClicked onNoteClicked;

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
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView content;
        TextView date;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.findViewById(R.id.card).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getOnNoteClicked() != null) {

                        int clickedAt = getAdapterPosition();

                        getOnNoteClicked().onNoteClicked(data.get(clickedAt));
                    }

                }
            });

            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            date = itemView.findViewById(R.id.date);

        }
    }
}
