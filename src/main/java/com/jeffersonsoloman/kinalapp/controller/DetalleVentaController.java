package com.jeffersonsoloman.kinalapp.controller;

import com.jeffersonsoloman.kinalapp.entity.DetalleVenta;
import com.jeffersonsoloman.kinalapp.service.IDetalleVentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detalles-venta")
public class DetalleVentaController {

    private final IDetalleVentaService detalleVentaService;

    public DetalleVentaController(IDetalleVentaService detalleVentaService) {
        this.detalleVentaService = detalleVentaService;
    }

    @GetMapping
    public ResponseEntity<List<DetalleVenta>> listar() {
        List<DetalleVenta> detalles = detalleVentaService.listarTodos();
        return ResponseEntity.ok(detalles);
    }

    @GetMapping("/{codigoDetalleVenta}")
    public ResponseEntity<DetalleVenta> buscarPorCodigo(@PathVariable Long codigoDetalleVenta) {
        return detalleVentaService.buscarPorCodigo(codigoDetalleVenta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody DetalleVenta detalleVenta) {
        try {
            DetalleVenta nuevoDetalle = detalleVentaService.guardar(detalleVenta);
            return new ResponseEntity<>(nuevoDetalle, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{codigoDetalleVenta}")
    public ResponseEntity<Void> eliminar(@PathVariable Long codigoDetalleVenta) {
        try {
            if (!detalleVentaService.existePorCodigo(codigoDetalleVenta)) {
                return ResponseEntity.notFound().build();
            }
            detalleVentaService.eliminar(codigoDetalleVenta);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{codigoDetalleVenta}")
    public ResponseEntity<?> actualizar(@PathVariable Long codigoDetalleVenta, @RequestBody DetalleVenta detalleVenta) {
        try {
            if (!detalleVentaService.existePorCodigo(codigoDetalleVenta)) {
                return ResponseEntity.notFound().build();
            }
            DetalleVenta detalleActualizado = detalleVentaService.actualizar(codigoDetalleVenta, detalleVenta);
            return ResponseEntity.ok(detalleActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/venta/{codigoVenta}")
    public ResponseEntity<List<DetalleVenta>> listarPorVenta(@PathVariable Long codigoVenta) {
        List<DetalleVenta> detalles = detalleVentaService.listarPorVenta(codigoVenta);
        return ResponseEntity.ok(detalles);
    }

    @GetMapping("/producto/{codigoProducto}")
    public ResponseEntity<List<DetalleVenta>> listarPorProducto(@PathVariable Long codigoProducto) {
        List<DetalleVenta> detalles = detalleVentaService.listarPorProducto(codigoProducto);
        return ResponseEntity.ok(detalles);
    }
}