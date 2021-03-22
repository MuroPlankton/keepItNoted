package com.choicely.keepitnoted;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.UUID;

import io.realm.Realm;
import io.realm.Sort;

public class NoteActivity extends AppCompatActivity {

    private final static String TAG = "NoteActivity";

    private Button red, green, blue, black, white;
    private EditText editTitle, noteField;
    private LinearLayout baseLayout;
    private String id;
    private int color;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        editTitle = findViewById(R.id.activity_note_title);
        noteField = findViewById(R.id.activity_note_text_field);
        red = findViewById(R.id.activity_note_color_red);
        green = findViewById(R.id.activity_note_color_green);
        blue = findViewById(R.id.activity_note_color_blue);
        black = findViewById(R.id.activity_note_color_black);
        white = findViewById(R.id.activity_note_color_white);
        baseLayout = findViewById(R.id.activity_note_base_layout);

        loadNote();

        red.setOnClickListener(v -> {
            changeNoteColor(getResources().getColor(R.color.note_red));
        });

        green.setOnClickListener(v -> {
            changeNoteColor(getResources().getColor(R.color.note_green));
        });

        blue.setOnClickListener(v -> {
            changeNoteColor(getResources().getColor(R.color.note_blue));
        });

        black.setOnClickListener(v -> {
            changeNoteColor(Color.parseColor("#000000"));
        });

        white.setOnClickListener(v -> {
            changeNoteColor(Color.parseColor("#FFFFFF"));
            editTitle.setTextColor(Color.parseColor("#000000"));
            noteField.setTextColor(Color.parseColor("#000000"));
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.note_activity_menu_save) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        saveNote();
        super.onBackPressed();
    }

    private void saveNote() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        NoteData newNote = new NoteData();
        newNote.setId(id);
        newNote.setTitle(editTitle.getText().toString());
        newNote.setText(noteField.getText().toString());
        newNote.setColor(color);
        realm.insertOrUpdate(newNote);
        realm.commitTransaction();
        MainActivity.changeItem(newNote);
    }

    private void loadNote() {
        Realm realm = Realm.getDefaultInstance();
        id = getIntent().getStringExtra("intent_note_id");
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
            changeNoteColor(Color.parseColor("#FFFFFF"));
            editTitle.setTextColor(Color.parseColor("#000000"));
            noteField.setTextColor(Color.parseColor("#000000"));
        } else {
            NoteData note = realm.where(NoteData.class).equalTo("id", id).findFirst();
            editTitle.setText(note.getTitle());
            noteField.setText(note.getText());
            if (note.getColor() == Color.parseColor("#FFFFFF")) {
                changeNoteColor(note.getColor());
                editTitle.setTextColor(Color.parseColor("#000000"));
                noteField.setTextColor(Color.parseColor("#000000"));
            } else {
                changeNoteColor(note.getColor());
            }
        }
        Log.d(TAG, "loaded a note with id: " + id);
    }

    private void changeNoteColor(int color) {
        baseLayout.setBackgroundColor(color);
        editTitle.setTextColor(Color.parseColor("#ffffff"));
        noteField.setTextColor(Color.parseColor("#ffffff"));
        this.color = color;
    }
}
