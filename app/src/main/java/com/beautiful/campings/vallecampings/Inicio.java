package com.beautiful.campings.vallecampings;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.beautiful.campings.vallecampings.Adapters.CampingsAdapter;
import com.beautiful.campings.vallecampings.Parsers.UsuarioJSONParser;

import java.util.List;

/**
 * Created by root on 19-04-17.
 */

public class Inicio extends AppCompatActivity  {

   // Button boton;
   // TextView texView;
    ProgressBar progressBar;

    List<com.beautiful.campings.vallecampings.Clases.Campings> campingsList;


 //   ListView listview;
 //   MyAdapter adapter;

    RecyclerView recyclerView;
    CampingsAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

     //   boton = (Button) findViewById(R.id.boton);
       // texView = (TextView) findViewById(R.id.text);

        progressBar = (ProgressBar) findViewById(R.id.progessbar);
        progressBar.setVisibility(View.INVISIBLE);
    //    texView.setMovementMethod(new ScrollingMovementMethod());

        recyclerView = (RecyclerView)  findViewById(R.id.recyclerviews);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(linearLayoutManager);



                if(isOnLine()) {

                }else{
                    Toast.makeText(getApplicationContext(),"Sin conexión", Toast.LENGTH_SHORT).show();
                }


        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {

            case R.id.favoritos:
                Intent intent = new Intent(Inicio.this, ListaFavoritos.class);
                startActivity(intent);
                break;

            case R.id.set_donate:
                AlertDialog.Builder alertDialogDonate = new AlertDialog.Builder(Inicio.this);
                // Setting Dialog Message
                alertDialogDonate.setTitle("Donar");
                alertDialogDonate.setMessage("Tu donación ayuda a que esta aplicación mejore. \nDesde ya muchas gracias!");
                alertDialogDonate.setCancelable(true);
                alertDialogDonate.setPositiveButton("DONATE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Uri.Builder uriBuilder = new Uri.Builder();
                        uriBuilder.scheme("https").authority("www.paypal.com").path("cgi-bin/webscr");
                        uriBuilder.appendQueryParameter("cmd", "_donations");

                        uriBuilder.appendQueryParameter("business", "marceloc007@hotmail.com");
                        uriBuilder.appendQueryParameter("lc", "US");
                        uriBuilder.appendQueryParameter("item_name", "heygringolearnspanish");
                        uriBuilder.appendQueryParameter("no_note", "1");
                        uriBuilder.appendQueryParameter("no_shipping", "1");
                        uriBuilder.appendQueryParameter("currency_code", "USD");
                        Uri payPalUri = uriBuilder.build();

                        Intent viewIntent = new Intent(Intent.ACTION_VIEW, payPalUri);
                        startActivity(viewIntent);
                    }
                });

                alertDialogDonate.show();
                break;



            case R.id.valorar:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Inicio.this);
                // Setting Dialog Message
                alertDialog.setTitle("Valora");
                alertDialog.setMessage("Te comentario es muy importante para mi, por favor VALORA " +
                        "o deja un comentario para poder mejorar esta alicación " +
                        "Beautiful peeeople!");

                alertDialog.setCancelable(true);
                alertDialog.setPositiveButton("RATE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {

                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {}
                    }
                });
                alertDialog.show();
                return true;

            /*     case R.id.salir:
                AlertDialog.Builder a_builder = new AlertDialog.Builder(Campings.this);
                a_builder.setMessage("Quieres salir de esta Aplicacion !!!")
                        .setCancelable(false)
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = a_builder.create();
                alert.setTitle("Alert !!!");
                alert.show();
                break;*/


        }
        return true;
    }







    public void cargarDatos() {
        adapter = new CampingsAdapter(campingsList, getApplicationContext());
        adapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = campingsList.get(recyclerView.getChildAdapterPosition(v)).getUsuadioId();
                    Intent intent  = new Intent(Inicio.this, Campings.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
        });
        recyclerView.setAdapter(adapter);


    }

    public void pedirDatos(String uri){
        MyTask task = new MyTask();
        task.execute(uri);
    }

    public boolean isOnLine(){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
            return true;
        }else{
            return false;
        }
    }

    private class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {

            String content = HttpManager.getData(params[0],"pepito","pepito");


            return content;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(result == null) {
                Toast.makeText(Inicio.this, "No se pudo conectar",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                return;
            }

            campingsList = UsuarioJSONParser.parse(result);

            cargarDatos();
            progressBar.setVisibility(View.INVISIBLE);



        }

        @Override
        protected void onProgressUpdate(String... values) {


        }
    }

}
