package com.jeffersonsoloman.kinalapp.repository;

import com.jeffersonsoloman.kinalapp.entity.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
    List<DetalleVenta> findByVenta_CodigoVenta(Long codigoVenta);

    List<DetalleVenta> findByProducto_CodigoProducto(Long codigoProducto);
}