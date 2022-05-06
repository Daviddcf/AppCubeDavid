package com.example.appcubedavid;

public class ModelCuentos {

    String nombre;
    int foto;
    int fotoPerfil;

    public ModelCuentos(String nombre, int foto, int fotoPerfil) {
        this.nombre = nombre;
        this.foto = foto;
        this.fotoPerfil = fotoPerfil;
    }

    public String getNombre() {
        return nombre;
    }

    public int getFoto() {
        return foto;
    }

    public int getFotoPerfil() {
        return fotoPerfil;
    }
}
