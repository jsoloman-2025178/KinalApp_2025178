package com.jeffersonsoloman.kinalapp.repository;

import com.jeffersonsoloman.kinalapp.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByEstado(int estado);

    List<Venta> findByCliente_DPICliente(String dpiCliente);

    List<Venta> findByFechaVentaBetween(Date fechaInicio, Date fechaFin);

    List<Venta> findByUsuario_CodigoUsuario(Long codigoUsuario);
}