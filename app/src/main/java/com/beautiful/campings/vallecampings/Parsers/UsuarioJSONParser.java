package com.beautiful.campings.vallecampings.Parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.beautiful.campings.vallecampings.Clases.Campings;

/**
 * Created by marxelo on 04-05-17.
 */

public class UsuarioJSONParser {
    public static List<Campings> parse(String content) {
        try {
            JSONArray jsonArray = new JSONArray(content);
            List<Campings> usuarioList = new ArrayList<>();

            for (int i=0; i <jsonArray.length(); i ++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Campings usuario = new Campings();

                usuario.setUsuadioId(jsonObject.getInt("id"));
                usuario.setNombre(jsonObject.getString("nombre"));
                usuario.setDirecion(jsonObject.getString("direccion"));
                usuario.setTelefono(jsonObject.getString("telefono"));
                usuario.setEmail(jsonObject.getString("email"));
                usuario.setDescripcion(jsonObject.getString("descripcion"));
                usuario.setCover(jsonObject.getString("cover"));


                usuarioList.add(usuario);
            }

            return usuarioList;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}