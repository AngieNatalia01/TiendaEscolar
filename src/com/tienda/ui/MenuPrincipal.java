package com.tienda.ui;

import com.tienda.modelo.Producto;
import com.tienda.servicio.GestorProductos;
import com.tienda.servicio.GestorCompras;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {
    private GestorProductos gestorProductos;
    private GestorCompras gestorCompras;
    private Scanner scanner;
    private boolean ejecutando;

    public MenuPrincipal() {
        this.gestorProductos = new GestorProductos();
        this.gestorCompras = new GestorCompras(gestorProductos);
        this.scanner = new Scanner(System.in);
        this.ejecutando = true;
    }

    /**
     * Muestra el menú principal y maneja las opciones
     */
    public void mostrar() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║     TIENDA ESCOLAR - MENÚ PRINCIPAL    ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("1. Registrar nuevo producto");
        System.out.println("2. Ver todos los productos");
        System.out.println("3. Realizar una compra");
        System.out.println("4. Ver historial de compras");
        System.out.println("5. Ver total de ventas");
        System.out.println("6. Salir");
        System.out.println("══════════════════════════════════════");
        System.out.print("Selecciona una opción: ");
    }

    /**
     * Ejecuta el programa principal
     */
    public void ejecutar() {
        System.out.println("\n¡Bienvenido a la Tienda Escolar!");
        
        while (ejecutando) {
            mostrar();
            String opcion = scanner.nextLine().trim();
            
            switch (opcion) {
                case "1":
                    registrarProducto();
                    break;
                case "2":
                    gestorProductos.mostrarProductos();
                    break;
                case "3":
                    realizarCompra();
                    break;
                case "4":
                    gestorCompras.mostrarHistorial();
                    break;
                case "5":
                    mostrarTotalVentas();
                    break;
                case "6":
                    salir();
                    break;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }
    }

    /**
     * Solicita datos para registrar un nuevo producto
     */
    private void registrarProducto() {
        System.out.println("\n--- Registrar Nuevo Producto ---");
        System.out.print("Nombre del producto: ");
        String nombre = scanner.nextLine().trim();
        
        System.out.print("Precio del producto: $");
        try {
            double precio = Double.parseDouble(scanner.nextLine().trim());
            gestorProductos.registrarProducto(nombre, precio);
        } catch (NumberFormatException e) {
            System.out.println("Error: Debes ingresar un precio válido (número)");
        }
    }

    /**
     * Permite al usuario realizar una compra
     */
    private void realizarCompra() {
        if (gestorProductos.obtenerCantidad() == 0) {
            System.out.println("No hay productos disponibles para comprar");
            return;
        }

        System.out.println("\n--- Realizar Compra ---");
        gestorProductos.mostrarProductos();
        
        List<Producto> itemsCompra = new ArrayList<>();
        boolean agregarMas = true;

        while (agregarMas) {
            System.out.print("Ingresa el ID del producto a comprar (0 para terminar): ");
            try {
                int id = Integer.parseInt(scanner.nextLine().trim());
                
                if (id == 0) {
                    agregarMas = false;
                } else {
                    Producto producto = gestorProductos.buscarProducto(id);
                    if (producto != null) {
                        itemsCompra.add(producto);
                        System.out.println(producto.getNombre() + " agregado al carrito");
                    } else {
                        System.out.println("Producto no encontrado");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debes ingresar un número válido");
            }
        }

        if (!itemsCompra.isEmpty()) {
            double total = gestorCompras.calcularTotal(itemsCompra);
            System.out.println("\n" + "─".repeat(40));
            System.out.println(" Items: " + itemsCompra.size());
            for (Producto p : itemsCompra) {
                System.out.println("   • " + p.getNombre() + " - $" + String.format("%.2f", p.getPrecio()));
            }
            System.out.println("─".repeat(40));
            System.out.println("TOTAL A PAGAR: $" + String.format("%.2f", total));
            System.out.println("─".repeat(40));
            
            gestorCompras.registrarCompra(itemsCompra, total);
        } else {
            System.out.println("Compra cancelada (sin items)");
        }
    }

    /**
     * Muestra el total de ventas del día
     */
    private void mostrarTotalVentas() {
        double totalVentas = gestorCompras.calcularTotalVentas();
        System.out.println("\n" + "═".repeat(40));
        System.out.println(" TOTAL DE VENTAS DEL DÍA: $" + String.format("%.2f", totalVentas));
        System.out.println("═".repeat(40) + "\n");
    }

    /**
     * Finaliza la aplicación
     */
    private void salir() {
        System.out.println("\n Gracias por usar la Tienda Escolar!");
        System.out.println("Total de ventas: $" + String.format("%.2f", gestorCompras.calcularTotalVentas()));
        ejecutando = false;
        scanner.close();
    }
}
