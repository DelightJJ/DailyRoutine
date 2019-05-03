package de.hs_kl.thomasjager.dailyroutine.SubActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hs_kl.thomasjager.dailyroutine.MainActivity;
import de.hs_kl.thomasjager.dailyroutine.R;
import de.hs_kl.thomasjager.dailyroutine.model.DatabaseHelper;

public class RoutineErstellen extends AppCompatActivity {

    EditText etTitle;
    EditText etDescription;
    Button btnBack;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_erstellen);


        etTitle = (EditText) findViewById(R.id.editTextTitle);
        etDescription = (EditText) findViewById(R.id.editTextDescription);
        btnAdd = (Button) findViewById(R.id.buttonAdd);
        btnBack = (Button) findViewById(R.id.buttonBack);

        final DatabaseHelper myDatabaseHelper = new DatabaseHelper(this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String description = etDescription.getText().toString();
                int counter = 0;
                String date = getDatum();





                if (title.isEmpty()){
                    Toast.makeText(RoutineErstellen.this, "Bitte geben Sie einen Namen an!", Toast.LENGTH_LONG).show();
                } else if (myDatabaseHelper.checkIfAlreadyInDB(title)){
                    Toast.makeText(RoutineErstellen.this, "Diese Routine gibts schon!", Toast.LENGTH_LONG).show();
                } else {
                    myDatabaseHelper.addRoutine(title, description, counter, date);
                    Toast.makeText(RoutineErstellen.this, "Glückwunsch, Routine erfolgreich erstellt!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);

                }




            }
        });




    }  // Oncreate

public String getDatum() {
    SimpleDateFormat sdf = new SimpleDateFormat("  dd MM yyyy   HH:mm");
    String dateAndTime = sdf.format(new Date());


    return  dateAndTime;
}


}
