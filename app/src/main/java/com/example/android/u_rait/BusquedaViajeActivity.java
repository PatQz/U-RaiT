package com.example.android.u_rait;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BusquedaViajeActivity extends AppCompatActivity {
    private ArrayList<String> mItems = new ArrayList<>();
    private ArrayAdapter mAdapter;
    private DatabaseReference databaseReference;

    private ListView listViewViajes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_viaje);

        listViewViajes = (ListView) findViewById(R.id.viajeslistView);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://u-rait.firebaseio.com/viajesO");

        FirebaseListAdapter<String> adapter =
                new FirebaseListAdapter<String>(this, String.class, android.R.layout.simple_expandable_list_item_1, databaseReference) {
                    @Override
                    protected void populateView(View v, String model, int position) {
                        TextView textView = (TextView) v.findViewById(android.R.id.text1);
                        textView.setText(model);
                    }
                };
        listViewViajes.setAdapter(adapter);

    }
}