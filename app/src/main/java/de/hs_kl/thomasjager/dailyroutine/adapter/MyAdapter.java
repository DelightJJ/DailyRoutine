package de.hs_kl.thomasjager.dailyroutine.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MyAdapter extends CursorAdapter {

    LayoutInflater myLayoutInflater;
    int itemLayout;
    String[] from;
    int[] to;



    public MyAdapter(Context context, int itemLayout, Cursor c,String[]from, int[] to, int flags){
        super(context,c,flags);
        myLayoutInflater = LayoutInflater.from(context);
        this.itemLayout = itemLayout;
        this.from = from;
        this.to = to;
    }



    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = myLayoutInflater.inflate(itemLayout,parent,false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        String title = cursor.getString(cursor.getColumnIndexOrThrow(from[0]));
        TextView textViewTitle = (TextView) view.findViewById(to[0]);
        textViewTitle.setText(title);

        String description = cursor.getString(cursor.getColumnIndexOrThrow(from[1]));
        TextView textViewDescription = (TextView) view.findViewById(to[1]);
        textViewDescription.setText(description);

        String counter = cursor.getString(cursor.getColumnIndexOrThrow(from[2]));
        TextView textViewCounter = (TextView) view.findViewById(to[2]);
        textViewCounter.setText(counter + " mal erledigt");

    }
}
