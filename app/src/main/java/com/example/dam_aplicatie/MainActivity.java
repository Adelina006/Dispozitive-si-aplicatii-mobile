package com.example.dam_aplicatie;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 100;
    public ListView listViewStudenti;

    List<Student> studenti = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Add_Activity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            });


            listViewStudenti.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Student student = studenti.get(position);
                    ArrayAdapter adapter = (ArrayAdapter) listViewStudenti.getAdapter();
                    AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Confirmare stergere")
                            .setMessage("Doriti sa stergeti?")
                            .setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    studenti.remove(student);
                                    adapter.notifyDataSetChanged();
                                   dialog.cancel();
                                }
                            })
                            .create();
                    dialog.show();
                    return true;
                }
            });

            return insets;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
            Student student = (Student) data.getSerializableExtra(Add_Activity.ADD_STUDENT);
            if(student != null)
            {
                studenti.add(student);
            }
            ArrayAdapter<Student> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, studenti );
            listViewStudenti.setAdapter(adapter);
        }
    }


}