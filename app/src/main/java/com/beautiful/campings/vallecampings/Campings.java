package com.beautiful.campings.vallecampings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by root on 14-06-17.
 */

public class Campings extends AppCompatActivity implements View.OnClickListener {

    private TextView nombre, direccion, telefono, email;

    private ImageView imagen;
    private Context context;
    private Button ubicarme;



    private Double latitud,longitud;
    private String titulo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camping);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);


        nombre = (TextView) findViewById(R.id.tvNombre);
        direccion = (TextView) findViewById(R.id.tvDireccion);
        telefono = (TextView) findViewById(R.id.tvTelefono);
        email = (TextView) findViewById(R.id.tvEmail);
        imagen = (ImageView) findViewById(R.id.imageviewCamping);
        ubicarme = (Button) findViewById(R.id.buttonUbicarme);



        ubicarme.setOnClickListener(this);

        String url = "https://aqueous-anchorage-98784.herokuapp.com/welcomes/" + id + ".json";


        if (isOnLine()) {
            pedirDatos(url);
        } else {
            Toast.makeText(getApplicationContext(), "Sin conexión", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(Campings.this, ListaFavoritos.class);
                startActivity(intent);
                break;

            case R.id.set_donate:
                AlertDialog.Builder alertDialogDonate = new AlertDialog.Builder(Campings.this);
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
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Campings.this);
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

    public boolean isOnLine() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }


    }

    public void pedirDatos(String uri) {
        MyTask task = new MyTask();
        task.execute(uri);
    }



    private class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            String content = HttpManager.getData(params[0]);


            return content;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                nombre.setText("Nombre: " + jsonObject.getString("nombre"));
                direccion.setText("Direccion: " + jsonObject.getString("direccion"));
                telefono.setText("Telefono: " + jsonObject.getString("telefono"));
                email.setText("Email: " + jsonObject.getString("email"));
                //     imagen.setText("Email: " + jsonObject.getString("email"));
                //   Picasso.with(context).load(usuarioList.get(position).getCover()).into(holder.imageView);
                Picasso.with(context).load(jsonObject.getString("cover")).into(imagen);
                latitud = (jsonObject.getDouble("latitud"));
                longitud = (jsonObject.getDouble("longitud"));
                titulo = (jsonObject.getString("titulo"));


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        @Override
        protected void onProgressUpdate(String... values) {


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonUbicarme:

                Intent intent = new Intent(Campings.this, MapsActivity.class);
                intent.putExtra("DATO",latitud );
                intent.putExtra("DATO2",longitud );
                intent.putExtra("DATO3",titulo );


                startActivity(intent);
                break;
        }
    }
}
