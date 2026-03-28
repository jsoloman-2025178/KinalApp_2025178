package com.jeffersonsoloman.kinalapp.controller;

import com.jeffersonsoloman.kinalapp.entity.Venta;
import com.jeffersonsoloman.kinalapp.service.IVentasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    private final IVentasService ventaService;

    public VentaController(IVentasService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping
    public ResponseEntity<List<Venta>> listar() {
        List<Venta> ventas = ventaService.listarTodos();
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/{codigoVenta}")
    public ResponseEntity<Venta> buscarPorCodigo(@PathVariable Long codigoVenta) {
        return ventaService.buscarPorCodigo(codigoVenta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Venta venta) {
        try {
            Venta nuevaVenta = ventaService.guardar(venta);
            return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{codigoVenta}")
    public ResponseEntity<Void> eliminar(@PathVariable Long codigoVenta) {
        try {
            if (!ventaService.existePorCodigo(codigoVenta)) {
                return ResponseEntity.notFound().build();
            }
            ventaService.eliminar(codigoVenta);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{codigoVenta}")
    public ResponseEntity<?> actualizar(@PathVariable Long codigoVenta, @RequestBody Venta venta) {
        try {
            if (!ventaService.existePorCodigo(codigoVenta)) {
                return ResponseEntity.notFound().build();
            }
            Venta ventaActualizada = ventaService.actualizar(codigoVenta, venta);
            return ResponseEntity.ok(ventaActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/activas")
    public ResponseEntity<List<Venta>> listarActivas() {
        List<Venta> activas = ventaService.listarPorEstado(1);
        return ResponseEntity.ok(activas);
    }

    @GetMapping("/cliente/{dpiCliente}")
    public ResponseEntity<List<Venta>> listarPorCliente(@PathVariable String dpiCliente) {
        List<Venta> ventas = ventaService.listarPorCliente(dpiCliente);
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/fechas")
    public ResponseEntity<List<Venta>> listarPorRangoFechas(
            @RequestParam Date fechaInicio,
            @RequestParam Date fechaFin) {
        List<Venta> ventas = ventaService.listarPorRangoFechas(fechaInicio, fechaFin);
        return ResponseEntity.ok(ventas);
    }

    // GET - Listar ventas inactivas (canceladas)
    @GetMapping("/inactivas")
    public ResponseEntity<List<Venta>> listarInactivas() {
        List<Venta> inactivas = ventaService.listarPorEstado(0);
        return ResponseEntity.ok(inactivas);
    }
}