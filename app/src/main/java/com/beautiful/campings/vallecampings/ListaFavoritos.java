package com.beautiful.campings.vallecampings;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.beautiful.campings.vallecampings.Adapters.BaseAdapter;

/**
 * Created by root on 27-06-17.
 */

public class ListaFavoritos extends AppCompatActivity {

    RecyclerView recyclerView;

    BaseAdapter adapter;
    DbHelper myDBHelper;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorito);

        recyclerView = (RecyclerView)  findViewById(R.id.recyclerviewsFav);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(linearLayoutManager);

        myDBHelper = new DbHelper(this.getApplicationContext());


        try {


            Cursor cursor = myDBHelper.fetchAllList();
            if (cursor != null) {
                adapter = new BaseAdapter(this, cursor);
                recyclerView.setAdapter(adapter);


            } else {
                Toast.makeText(getApplicationContext(), "error !!!", Toast.LENGTH_SHORT).show();
            }

        }catch (SQLException e){

        }






    }
}
