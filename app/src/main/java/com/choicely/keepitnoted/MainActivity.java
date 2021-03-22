package com.choicely.keepitnoted;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";
    private RecyclerView notesRecycler;
    private static NoteListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesRecycler = findViewById(R.id.activity_main_recycler_view);
        notesRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteListAdapter(this);
        notesRecycler.setAdapter(adapter);

        updateContent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.main_activity_menu_new) {
            startActivity(new Intent(this, NoteActivity.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public static void changeItem(NoteData updated) {
        adapter.updateItem(updated);
        adapter.notifyDataSetChanged();
    }

    private void updateContent() {
        adapter.clear();

        Realm realm = Realm.getDefaultInstance();
        RealmResults<NoteData> notes = realm.where(NoteData.class).findAll();
        for (NoteData note : notes) {
            adapter.add(note);
            Log.d(TAG, "sent note to adapter with id: " + note.getId());
        }

        adapter.notifyDataSetChanged();
    }
}