package com.jeffersonsoloman.kinalapp.service;

import com.jeffersonsoloman.kinalapp.entity.DetalleVenta;
import com.jeffersonsoloman.kinalapp.entity.Producto;
import com.jeffersonsoloman.kinalapp.repository.DetalleVentaRepository;
import com.jeffersonsoloman.kinalapp.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DetalleVentaService implements IDetalleVentaService {

    private final DetalleVentaRepository detalleVentaRepository;
    private final ProductoRepository productoRepository;

    public DetalleVentaService(DetalleVentaRepository detalleVentaRepository, ProductoRepository productoRepository) {
        this.detalleVentaRepository = detalleVentaRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleVenta> listarTodos() {
        return detalleVentaRepository.findAll();
    }

    @Override
    public DetalleVenta guardar(DetalleVenta detalleVenta) {
        validarDetalleVenta(detalleVenta);

        if (detalleVenta.getSubtotal() == null) {
            BigDecimal subtotal = detalleVenta.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(detalleVenta.getCantidad()));
            detalleVenta.setSubtotal(subtotal);
        }

        Producto producto = detalleVenta.getProducto();
        int nuevoStock = producto.getStock() - detalleVenta.getCantidad();
        if (nuevoStock < 0) {
            throw new IllegalArgumentException("Stock insuficiente para el producto: " + producto.getNombreProducto());
        }
        producto.setStock(nuevoStock);
        productoRepository.save(producto);

        return detalleVentaRepository.save(detalleVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DetalleVenta> buscarPorCodigo(Long codigoDetalleVenta) {
        return detalleVentaRepository.findById(codigoDetalleVenta);
    }

    @Override
    public DetalleVenta actualizar(Long codigoDetalleVenta, DetalleVenta detalleVenta) {
        if (!detalleVentaRepository.existsById(codigoDetalleVenta)) {
            throw new RuntimeException("Detalle de venta no encontrado con código: " + codigoDetalleVenta);
        }
        detalleVenta.setCodigoDetalleVenta(codigoDetalleVenta);
        validarDetalleVenta(detalleVenta);

        if (detalleVenta.getSubtotal() == null) {
            BigDecimal subtotal = detalleVenta.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(detalleVenta.getCantidad()));
            detalleVenta.setSubtotal(subtotal);
        }

        return detalleVentaRepository.save(detalleVenta);
    }

    @Override
    public void eliminar(Long codigoDetalleVenta) {
        if (!detalleVentaRepository.existsById(codigoDetalleVenta)) {
            throw new RuntimeException("Detalle de venta no encontrado con código: " + codigoDetalleVenta);
        }

        Optional<DetalleVenta> detalleOpt = detalleVentaRepository.findById(codigoDetalleVenta);
        if (detalleOpt.isPresent()) {
            DetalleVenta detalle = detalleOpt.get();
            Producto producto = detalle.getProducto();
            producto.setStock(producto.getStock() + detalle.getCantidad());
            productoRepository.save(producto);
        }

        detalleVentaRepository.deleteById(codigoDetalleVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorCodigo(Long codigoDetalleVenta) {
        return detalleVentaRepository.existsById(codigoDetalleVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleVenta> listarPorVenta(Long codigoVenta) {
        return detalleVentaRepository.findByVenta_CodigoVenta(codigoVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleVenta> listarPorProducto(Long codigoProducto) {
        return detalleVentaRepository.findByProducto_CodigoProducto(codigoProducto);
    }

    private void validarDetalleVenta(DetalleVenta detalleVenta) {
        if (detalleVenta.getVenta() == null) {
            throw new IllegalArgumentException("La venta es obligatoria");
        }
        if (detalleVenta.getProducto() == null) {
            throw new IllegalArgumentException("El producto es obligatorio");
        }
        if (detalleVenta.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
        if (detalleVenta.getPrecioUnitario() == null || detalleVenta.getPrecioUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio unitario debe ser mayor a cero");
        }
    }
}