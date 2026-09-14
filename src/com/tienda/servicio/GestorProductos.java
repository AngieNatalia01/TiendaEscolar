package com.tienda.servicio;

import com.tienda.modelo.Producto;
import java.util.ArrayList;
import java.util.List;


public class GestorProductos {
    private List<Producto> productos;
    private int proximoId;

    public GestorProductos() {
        this.productos = new ArrayList<>();
        this.proximoId = 1;
    }

    /**
     * Registra un nuevo producto con nombre y precio
     */
    public void registrarProducto(String nombre, double precio) {
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("Error: El nombre del producto no puede estar vacío");
            return;
        }
        if (precio < 0) {
            System.out.println("Error: El precio no puede ser negativo");
            return;
        }
        
        Producto producto = new Producto(proximoId++, nombre, precio);
        productos.add(producto);
        System.out.println("Producto registrado: " + producto);
    }

    /**
     * Busca un producto por su ID
     */
    public Producto buscarProducto(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    /**
     * Obtiene todos los productos registrados
     */
    public List<Producto> obtenerTodos() {
        return new ArrayList<>(productos);
    }

    /**
     * Muestra todos los productos disponibles
     */
    public void mostrarProductos() {
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados aún");
            return;
        }
        System.out.println("\n=== PRODUCTOS DISPONIBLES ===");
        for (Producto p : productos) {
            System.out.println(p);
        }
        System.out.println("================================\n");
    }

    /**
     * Elimina un producto por su ID
     */
    public boolean eliminarProducto(int id) {
        return productos.removeIf(p -> p.getId() == id);
    }

    /**
     * Obtiene la cantidad de productos registrados
     */
    public int obtenerCantidad() {
        return productos.size();
    }
}
