package com.tienda.servicio;

import com.tienda.modelo.Producto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GestorCompras {
    private GestorProductos gestorProductos;
    private List<Compra> historialCompras;

    public GestorCompras(GestorProductos gestorProductos) {
        this.gestorProductos = gestorProductos;
        this.historialCompras = new ArrayList<>();
    }

    /**
     * Calcula el total de una compra basada en los productos seleccionados
     */
    public double calcularTotal(List<Producto> items) {
        if (items == null || items.isEmpty()) {
            return 0;
        }
        return items.stream()
                   .mapToDouble(Producto::getPrecio)
                   .sum();
    }

    /**
     * Calcula el total con cantidad de unidades por producto
     */
    public double calcularTotalConCantidades(List<Producto> items, List<Integer> cantidades) {
        if (items == null || items.isEmpty() || cantidades == null || cantidades.isEmpty()) {
            return 0;
        }
        
        double total = 0;
        for (int i = 0; i < items.size() && i < cantidades.size(); i++) {
            total += items.get(i).getPrecio() * cantidades.get(i);
        }
        return total;
    }

    /**
     * Registra una compra en el historial
     */
    public void registrarCompra(List<Producto> items, double total) {
        if (items != null && !items.isEmpty()) {
            Compra compra = new Compra(items, total);
            historialCompras.add(compra);
            System.out.println("Compra registrada exitosamente");
        }
    }

    /**
     * Obtiene el historial de todas las compras
     */
    public List<Compra> obtenerHistorial() {
        return new ArrayList<>(historialCompras);
    }

    /**
     * Muestra el historial de compras
     */
    public void mostrarHistorial() {
        if (historialCompras.isEmpty()) {
            System.out.println("No hay compras registradas aún");
            return;
        }
        System.out.println("\n === HISTORIAL DE COMPRAS ===");
        for (int i = 0; i < historialCompras.size(); i++) {
            System.out.println("Compra #" + (i + 1) + ": " + historialCompras.get(i));
        }
        System.out.println("================================\n");
    }

    /**
     * Calcula el total de ventas
     */
    public double calcularTotalVentas() {
        return historialCompras.stream()
                              .mapToDouble(Compra::getTotal)
                              .sum();
    }

    /**
     * Clase representa una compra
     */
    public class Compra {
        private List<Producto> items;
        private double total;
        private LocalDateTime fecha;

        public Compra(List<Producto> items, double total) {
            this.items = new ArrayList<>(items);
            this.total = total;
            this.fecha = LocalDateTime.now();
        }

        public List<Producto> getItems() {
            return new ArrayList<>(items);
        }

        public double getTotal() {
            return total;
        }

        public LocalDateTime getFecha() {
            return fecha;
        }

        @Override
        public String toString() {
            String itemsStr = items.stream()
                                  .map(p -> p.getNombre() + " ($" + String.format("%.2f", p.getPrecio()) + ")")
                                  .reduce((a, b) -> a + ", " + b)
                                  .orElse("Sin items");
            return String.format("%s | Total: $%.2f | Fecha: %s", 
                    itemsStr, total, fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
    }
}
