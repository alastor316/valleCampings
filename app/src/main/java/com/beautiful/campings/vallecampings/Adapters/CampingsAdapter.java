package com.beautiful.campings.vallecampings.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beautiful.campings.vallecampings.DataBaseManager;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import com.beautiful.campings.vallecampings.Clases.Campings;


import com.beautiful.campings.vallecampings.DbHelper;
import com.beautiful.campings.vallecampings.R;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by root on 30-05-17.
 */

public class CampingsAdapter extends  RecyclerView.Adapter<CampingsAdapter.ViewHolder>
        implements View.OnClickListener{

    private List<Campings> usuarioList;
    private View.OnClickListener listener;
    private Context context;
    static int lastPosition = -1;
    private Cursor fila;

    public CampingsAdapter(List<Campings> usuarioList, Context context){
        this.usuarioList = usuarioList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,viewGroup,false);
        v.setOnClickListener(this);
        return new  ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder  holder, int position) {
        holder.title.setText(usuarioList.get(position).getNombre());
        holder.descripcion.setText(usuarioList.get(position).getDescripcion());
        Picasso.with(context).load(usuarioList.get(position).getCover()).into(holder.imageView);

     //   holder.favorite.setFavorite(false, true);
        holder.favorite.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {

                        DbHelper helper = new DbHelper(context);
                        SQLiteDatabase bd = helper.getWritableDatabase();
                        String nombre = holder.title.getText().toString();
                        String descripcion = holder.descripcion.getText().toString();


                                if (favorite) {
                                    Cursor fila = bd.rawQuery(  //devuelve 0 o 1 fila //es una consulta
                                            "select nombre from Favorito where nombre='" + nombre + "'", null);
                                    if (fila.moveToFirst()) {

                                        //capturamos los valores del cursos y lo almacenamos en variable
                                        fila.getString(0);
                                            Toast.makeText(context, "Ya esta en el camping en favoritos !", Toast.LENGTH_SHORT).show();
                                    }else{

                                     DataBaseManager dat = new DataBaseManager(context);
                                    ContentValues registro = new ContentValues();  //es una clase para guardar datos
                                    registro.put("nombre", nombre);
                                        registro.put("password", descripcion);
                                    bd.insert(DataBaseManager.TABLE_NAME, null, dat.generarContentValues(nombre,descripcion));

                                    bd.close();
                                    }

                                } else {


                                    //   bd.delete(DataBaseManager.TABLE_NAME, DataBaseManager.USR_NOM+ "="+ nombre, null); // (votantes es la nombre de la tabla, condición)

                                    bd.execSQL("DELETE FROM favorito WHERE nombre='" + nombre + "'");
                                     Toast.makeText(context, "Se ha borrado el camping !", Toast.LENGTH_SHORT).show();
                                    bd.close();

                                }
                            }
                });



        setAnimation(holder.cardView,position);

    }

    @Override
    public int getItemCount() {
        return usuarioList.size();
    }



    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }


    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView title,descripcion;
        ImageView imageView;
        MaterialFavoriteButton favorite;

        CardView cardView;
        public ViewHolder(View item){
            super(item);


            cardView = (CardView) item.findViewById(R.id.cardview);
            title = (TextView) item.findViewById(R.id.titleees);
            descripcion = (TextView) item.findViewById(R.id.tvDescripcion);

            imageView = (ImageView) item.findViewById(R.id.imageview);

            favorite = (MaterialFavoriteButton) item.findViewById(R.id.buttonFavotite);




        }
    }

    private void setAnimation(View viewToAnimate, int position ){

        if(position > lastPosition){
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;

        }
    }
}
