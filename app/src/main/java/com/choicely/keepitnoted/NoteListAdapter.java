 package com.choicely.keepitnoted;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    private final static String TAG = "NoteListAdapter";

    private Context context;
    private List<NoteData> noteList = new ArrayList<>();

    public NoteListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_note_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        NoteData note = noteList.get(position);

        holder.noteID = note.getId();
        holder.noteText.setText(note.getText());
        holder.noteTitle.setText(note.getTitle());
        holder.noteCard.setCardBackgroundColor(note.getColor());
        if (note.getColor() == Color.parseColor("#FFFFFF")) {
            holder.noteTitle.setTextColor(Color.parseColor("#000000"));
            holder.noteText.setTextColor(Color.parseColor("#000000"));
        } else {
            holder.noteTitle.setTextColor(-1);
            holder.noteText.setTextColor(-1);
        }
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void clear() {
        noteList.clear();
    }

    public void updateItem(NoteData updated) {
        boolean isOldNoteInList = false;
        if (noteList.size() > 0) {
            for (int index = 0; index < noteList.size(); index++) {
                if (noteList.get(index).getId().equals(updated.getId())) {
                    noteList.set(index, updated);
                    isOldNoteInList = true;
                    break;
                }
            }
        }
        if (!isOldNoteInList) {
            noteList.add(updated);
        }
    }

    public void add(NoteData newNote) {
        noteList.add(newNote);
        Log.d(TAG, "note received with id: " + newNote.getId());
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{
        String noteID;
        TextView noteTitle, noteText;
        CardView noteCard;

        public NoteViewHolder(View v) {
            super(v);
            v.setOnClickListener(onNoteClick);

            noteTitle = v.findViewById(R.id.activity_note_list_title);
            noteText = v.findViewById(R.id.activity_note_list_text);
            noteCard = v.findViewById(R.id.activity_note_list_card);
        }

        private View.OnClickListener onNoteClick = itemView -> {
            Context cxt = itemView.getContext();
            Intent intent = new Intent(cxt, NoteActivity.class);
            intent.putExtra("intent_note_id", noteID);
            cxt.startActivity(intent);
        };
    }
}
