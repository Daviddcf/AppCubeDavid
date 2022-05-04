package com.example.appcubedavid;

public class ModelChatRecientes {
    String nombre;
    int foto;

    public ModelChatRecientes(String nombre, int foto) {
        this.nombre = nombre;
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public int getFoto() {
        return foto;
    }
}
