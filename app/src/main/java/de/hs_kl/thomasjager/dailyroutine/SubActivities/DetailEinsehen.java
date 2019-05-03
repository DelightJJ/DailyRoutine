package de.hs_kl.thomasjager.dailyroutine.SubActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hs_kl.thomasjager.dailyroutine.MainActivity;
import de.hs_kl.thomasjager.dailyroutine.R;
import de.hs_kl.thomasjager.dailyroutine.model.DatabaseHelper;


public class DetailEinsehen extends AppCompatActivity {

    Button btnErledigt, btnBearbeiten, btnDelete, btnZu;
    TextView textName, textDesc, textCounter, textDate;
    String itemName;
    String itemDesc;
    String itemDate;
    int itemCounter;
    int itemId;
    DatabaseHelper mDatabaseHelper;
    boolean fromMainActivity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_einsehen);

        mDatabaseHelper = new DatabaseHelper(this);


        // TextViews
        textName = (TextView) findViewById(R.id.textName);
        textDesc = (TextView) findViewById(R.id.textDesc);
        textCounter = (TextView) findViewById(R.id.textCounter);
        textDate = (TextView) findViewById(R.id.textDate);


        // Button
        btnErledigt = (Button) findViewById(R.id.btnErledigt);
        btnBearbeiten = (Button) findViewById(R.id.btnBearbeiten);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnZu = (Button) findViewById(R.id.btnZu);

        // Intent empfangen
         Intent empfangsIntent = getIntent();
         itemName = empfangsIntent.getStringExtra("name");
         itemDesc = empfangsIntent.getStringExtra("description");

         itemId = mDatabaseHelper.getItemId(itemName);

         itemCounter = mDatabaseHelper.getCounter(itemId);
         itemDate = mDatabaseHelper.getDate(itemId);



        // Text für Textviews
        textName.setText(itemName.toString());
        textDesc.setText(" " + itemDesc.toString());
        textCounter.setText("Du hast die Routine bereits " + itemCounter + " mal erledigt");
        textDate.setText("Zuletzt erledigt: " + itemDate.toString());




        // Funktion Button

        btnBearbeiten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RoutineBearbeiten.class);
                intent.putExtra("name",itemName);
                intent.putExtra("description",itemDesc);
                intent.putExtra("fromMainActivity",fromMainActivity);
                startActivity(intent);
            }
        });




        btnErledigt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int erfolgreich = mDatabaseHelper.updateCounter(itemId);

                // aktuelles Datum
                itemDate = getDatum();
                int nochMehrErfolg = mDatabaseHelper.updateDate(itemId,itemDate);


                if(erfolgreich == 1 && nochMehrErfolg == 1){
                    Toast.makeText(DetailEinsehen.this,"Super!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getBaseContext(), DetailEinsehen.class);
                    intent.putExtra("name",itemName);
                    intent.putExtra("description",itemDesc);
                    startActivity(intent);

                }else {
                    Toast.makeText(DetailEinsehen.this,"Ups, da lief irgendetwas schief",Toast.LENGTH_LONG).show();
                };


            }
        });


        btnZu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });













    } // onCreate

    public String getDatum() {
        SimpleDateFormat sdf = new SimpleDateFormat("  dd MM yyyy   HH:mm");
        String dateAndTime = sdf.format(new Date());


        return  dateAndTime;
    }




    public void delete(View view){

        mDatabaseHelper.deleteRoutine(itemId);


        Toast.makeText(DetailEinsehen.this,"Löschen erfolgreich",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getBaseContext(),MainActivity.class);
        startActivity(intent);
    }



}
