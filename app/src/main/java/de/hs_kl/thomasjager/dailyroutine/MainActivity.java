package de.hs_kl.thomasjager.dailyroutine;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hs_kl.thomasjager.dailyroutine.SubActivities.DetailEinsehen;
import de.hs_kl.thomasjager.dailyroutine.SubActivities.RoutineBearbeiten;
import de.hs_kl.thomasjager.dailyroutine.SubActivities.RoutineErstellen;
import de.hs_kl.thomasjager.dailyroutine.adapter.MyAdapter;
import de.hs_kl.thomasjager.dailyroutine.model.DatabaseHelper;

import static android.database.CursorJoiner.Result.LEFT;
import static android.database.CursorJoiner.Result.RIGHT;

public class MainActivity extends AppCompatActivity {



    AlertDialog dialogItemClick;

    ListView listView;
    Button routineErstellenBtn;
    DatabaseHelper mDatabaseHelper;
    TextView etName;
    TextView etDescription;
    String name;
    String description;

    boolean fromMainActivity = true;


    View viewElement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDatabaseHelper = new DatabaseHelper(this);

        routineErstellenBtn = (Button) findViewById(R.id.testButton);
        listView = (ListView) findViewById(R.id.listView);



        // ListView

        Context context = this;
        int itemLayout = R.layout.routine_list_layout;
        final Cursor cursor = mDatabaseHelper.selectAll();
        final String[] from = new String[]{mDatabaseHelper.COLUMN_NAME_TITLE,mDatabaseHelper.COLUMN_NAME_DESCRIPTION,mDatabaseHelper.COLUMN_NAME_COUNTER};
        int[] to = new int[]{R.id.textViewTitle,R.id.textViewDescription,R.id.textViewCounter};

        final MyAdapter myAdapter = new MyAdapter(context,itemLayout,cursor,from,to,0);

        listView.setAdapter(myAdapter);



        // OnItemClickListener

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewElement = view;
                etName = (TextView) view.findViewById(R.id.textViewTitle);
                name = etName.getText().toString();
                etDescription = (TextView) view.findViewById(R.id.textViewDescription);
                description = etDescription.getText().toString();
                dialogErstellen();
            }
        });

        routineErstellenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RoutineErstellen.class);
                startActivity(intent);

            }
        });



    }  // OnCreate


    @Override
    protected void onDestroy() {
        mDatabaseHelper.close();
        super.onDestroy();
    }




    public void dialogErstellen(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        // Layout laden
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_item_layout2,null);
        builder.setView(v);

        dialogItemClick = builder.create();
        dialogItemClick.show();


       final Button btnErledigt = (Button) findViewById(R.id.btnErledigt);
       final Button btnBearbeiten = (Button) findViewById(R.id.btnBearbeiten);
       final Button btnDelete = (Button) findViewById(R.id.btnDelete);
       final Button btnDetails = (Button) findViewById(R.id.btnDetails);

        return;
    }


    // Funktionen für Button aus dem Dialog



    public void delete(View view){

        int itemId = mDatabaseHelper.getItemId(name);
        mDatabaseHelper.deleteRoutine(itemId);


        Toast.makeText(MainActivity.this,"Löschen erfolgreich",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getBaseContext(),MainActivity.class);
        startActivity(intent);
    }


    public void bearbeiten(View view){
        Intent intent = new Intent(getBaseContext(), RoutineBearbeiten.class);
        intent.putExtra("name",name);
        intent.putExtra("description",description);
        intent.putExtra("fromMainActivity", fromMainActivity);
        startActivity(intent);
    }


    public void detailsEinsehen(View view){
        Intent intent = new Intent(getBaseContext(), DetailEinsehen.class);
        intent.putExtra("name",name);
        intent.putExtra("description",description);
        startActivity(intent);

    }




    public void erledigt(View view){

        boolean erledigt = true;
        int id = mDatabaseHelper.getItemId(name);
        int erfolgreich = mDatabaseHelper.updateCounter(id);

        if(erfolgreich == 1){
            Toast.makeText(MainActivity.this,"Super!",Toast.LENGTH_LONG).show();
        };

        Intent intent = new Intent(getBaseContext(),MainActivity.class);
        startActivity(intent);

    }
















}
