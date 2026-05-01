package com.jeffersonsoloman.kinalapp.service;

import com.jeffersonsoloman.kinalapp.entity.Venta;
import com.jeffersonsoloman.kinalapp.repository.VentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VentaService implements IVentasService {

    private final VentaRepository ventaRepository;

    public VentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarTodos() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta guardar(Venta venta) {
        validarVenta(venta);
        if (venta.getEstado() == 0) {
            venta.setEstado(1);
        }
        if (venta.getFechaVenta() == null) {
            venta.setFechaVenta(new Date());
        }
        return ventaRepository.save(venta);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Venta> buscarPorCodigo(Long codigoVenta) {
        return ventaRepository.findById(codigoVenta);
    }

    @Override
    public Venta actualizar(Long codigoVenta, Venta venta) {
        if (!ventaRepository.existsById(codigoVenta)) {
            throw new RuntimeException("Venta no encontrada con código: " + codigoVenta);
        }
        venta.setCodigoVenta(codigoVenta);
        validarVenta(venta);
        return ventaRepository.save(venta);
    }

    @Override
    public void eliminar(Long codigoVenta) {
        if (!ventaRepository.existsById(codigoVenta)) {
            throw new RuntimeException("Venta no encontrada con código: " + codigoVenta);
        }
        ventaRepository.deleteById(codigoVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorCodigo(Long codigoVenta) {
        return ventaRepository.existsById(codigoVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarPorEstado(int estado) {
        return ventaRepository.findByEstado(estado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarPorCliente(String dpiCliente) {
        return ventaRepository.findByCliente_DPICliente(dpiCliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarPorRangoFechas(Date fechaInicio, Date fechaFin) {
        return ventaRepository.findByFechaVentaBetween(fechaInicio, fechaFin);
    }

    private void validarVenta(Venta venta) {
        if (venta.getCliente() == null) {
            throw new IllegalArgumentException("El cliente es obligatorio");
        }
        if (venta.getUsuario() == null) {
            throw new IllegalArgumentException("El usuario es obligatorio");
        }
        if (venta.getTotal() == null || venta.getTotal().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El total debe ser mayor a cero");
        }
    }
}