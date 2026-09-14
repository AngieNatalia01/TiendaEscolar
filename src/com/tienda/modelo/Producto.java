package com.tienda.modelo;

public class Producto {
    private int id;
    private String nombre;
    private double precio;

    // Constructor
    public Producto(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        if (precio >= 0) {
            this.precio = precio;
        }
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Producto: %s | Precio: $%.2f", id, nombre, precio);
    }
}
