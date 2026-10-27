package com.example.dam_aplicatie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Add_Activity extends AppCompatActivity {

    public static final String ADD_STUDENT = "add_student";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);


            Spinner spinnerFacultati = findViewById(R.id.spinnerFacultate);
            String[] facultati = {"CSIE", "REI", "CIG", "MARKETING"};

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, facultati);
            spinnerFacultati.setAdapter(adapter);

            EditText etNume = findViewById(R.id.editTextNume);
            EditText etDataNasterii = findViewById(R.id.editTextDataNasterii);
            EditText etMedie = findViewById(R.id.editTextMedie);
            RadioGroup radioGroupBursa = findViewById(R.id.radioGroup);
            Button btnAdaugaStudent = findViewById(R.id.btnAdaugaStudent);

            Intent intent = getIntent();

            btnAdaugaStudent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(etNume.getText().toString().isEmpty())
                    {
                        etNume.setError("Introduceti numele");
                    }
                    else
                        if(etDataNasterii.getText().toString().isEmpty())
                        {
                            etDataNasterii.setError("Introduceti data nasterii");
                        }
                        else
                            if(etMedie.getText().toString().isEmpty())
                            {
                                etMedie.setError("Introduceti media");
                            }
                            else {
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                                try {
                                    Date dataNasterii = sdf.parse(etDataNasterii.getText().toString());
                                    String nume = etNume.getText().toString();
                                    float medie = Float.parseFloat(etMedie.getText().toString());
                                    String facultate = spinnerFacultati.getSelectedItem().toString();
                                    RadioButton radioButton = findViewById(radioGroupBursa.getCheckedRadioButtonId());
                                    boolean bursa = false;
                                    if(radioButton.getText().toString().equals("Da"))
                                    {
                                        bursa = true;
                                    }

                                    Student student = new Student(nume, dataNasterii, medie, facultate, bursa) ;
                                    intent.putExtra(ADD_STUDENT, student);
                                    setResult(RESULT_OK, intent);
                                    finish();

                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                                catch (Exception e)
                                {
                                    Log.e("AddACtivity", e.getMessage().toString());
                                    Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG);
                                }
                            }

                }
            });
            return insets;
        });
    }
}