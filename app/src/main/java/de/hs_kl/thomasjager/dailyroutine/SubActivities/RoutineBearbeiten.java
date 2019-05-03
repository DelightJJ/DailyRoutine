package de.hs_kl.thomasjager.dailyroutine.SubActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import de.hs_kl.thomasjager.dailyroutine.MainActivity;
import de.hs_kl.thomasjager.dailyroutine.R;
import de.hs_kl.thomasjager.dailyroutine.model.DatabaseHelper;

public class RoutineBearbeiten extends AppCompatActivity {

    Button btnZu;
    Button btnFertig;
    EditText etName;
    EditText etDescription;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_bearbeiten);

        final DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);

        btnZu = (Button) findViewById(R.id.btnZu);
        btnFertig = (Button) findViewById(R.id.btnFertig);

        etName = (EditText) findViewById(R.id.editTextNameBearbeiten);
        etDescription = (EditText) findViewById(R.id.editTextDescBearbeiten);



        Intent empfangsIntent = getIntent();
        final String itemName = empfangsIntent.getStringExtra("name");
        final String itemDesc = empfangsIntent.getStringExtra("description");
        final boolean fromMainActivity = empfangsIntent.getBooleanExtra("fromMainActivity", true);
        int itemId = empfangsIntent.getIntExtra("id",-1);


        etName.setText(itemName);
        etDescription.setText(itemDesc);


        final int id = mDatabaseHelper.getItemId(itemName);


        // Onclick Listener für Zurück-Button
        btnZu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fromMainActivity) {
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getBaseContext(), DetailEinsehen.class);
                    intent.putExtra("name",itemName);
                    intent.putExtra("description",itemDesc);
                    startActivity(intent);
                }
            }
        });


        // Onclick Listener für Fertig-Button
        btnFertig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int erfolgreich = mDatabaseHelper.updateRoutine(id,etName.getText().toString(),etDescription.getText().toString());
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);

                if(erfolgreich==1){
                    Toast.makeText(RoutineBearbeiten.this,"Routine erfolgreich bearbeitet!",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RoutineBearbeiten.this,"Ups, da lief irgendetwas schief",Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
