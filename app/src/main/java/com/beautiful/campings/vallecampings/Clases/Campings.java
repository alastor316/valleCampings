package com.beautiful.campings.vallecampings.Clases;

/**
 * Created by root on 04-05-17.
 */

public class Campings {

    private int usuadioId;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private String descripcion;
    private String created_at;
    private String updated_at;
    private String cover;

    public int getUsuadioId(){
        return usuadioId;
    }

    public String getNombre(){
        return nombre;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public String getCover(){
        return cover;
    }

    public String getDireccion(){
        return direccion;
    }

    public void setUsuadioId(int usuario){
        this.usuadioId = usuario;
    }

    public void setNombre(String nombres){
        this.nombre = nombres;
    }

    public void setDirecion(String direccions){
        this.direccion = direccions;
    }

    public void setTelefono(String telefonos){
        this.telefono = telefonos;
    }

    public void setEmail(String emails){
        this.direccion = emails;
    }

    public void setDescripcion(String descripcions){
        this.descripcion = descripcions;
    }

    public void setCreated_at(String created_ats){
        this.created_at = created_ats;
    }

    public void setUpdated_at(String updated_ats){
        this.updated_at = updated_ats;
    }


    public void setCover(String covers){
        this.cover = covers;
    }






}
