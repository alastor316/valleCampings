package com.beautiful.campings.vallecampings.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beautiful.campings.vallecampings.Clases.Campings;
import com.beautiful.campings.vallecampings.R;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import java.util.List;

/**
 * Created by root on 28-06-17.
 */

public class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.ViewHolder> {

    private List<Campings> usuarioList;
    private Context myContext;
    CursorAdapter myCursorAdapter;



    public BaseAdapter(Context context, Cursor cursor){
        myContext = context;
        myCursorAdapter = new CursorAdapter(myContext,cursor,0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View retView = inflater.inflate(R.layout.item_favoritos,parent,false);

                return retView;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView titulox = (TextView) view.findViewById(R.id.titleeesFav);
                TextView descipcionx = (TextView) view.findViewById(R.id.tvDescripcionFav);

                titulox.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
                descipcionx.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
            }
        };


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_favoritos,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        myCursorAdapter.getCursor().moveToPosition(position);
        myCursorAdapter.bindView(holder.itemView,myContext,myCursorAdapter.getCursor());

    }

    @Override
    public int getItemCount() {
        return myCursorAdapter.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,descripcion;
        ImageView imageView;


        CardView cardView;

        public ViewHolder(View item){
            super(item);

            cardView = (CardView) item.findViewById(R.id.cardviewFav);
            title = (TextView) item.findViewById(R.id.titleees);
            descripcion = (TextView) item.findViewById(R.id.tvDescripcionFav);

            imageView = (ImageView) item.findViewById(R.id.imageviewFav);





        }
    }
}
