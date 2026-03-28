package com.jeffersonsoloman.kinalapp.service;

import com.jeffersonsoloman.kinalapp.entity.Venta;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IVentasService {
    List<Venta> listarTodos();

    Venta guardar(Venta venta);

    Optional<Venta> buscarPorCodigo(Long codigoVenta);

    Venta actualizar(Long codigoVenta, Venta venta);

    void eliminar(Long codigoVenta);

    boolean existePorCodigo(Long codigoVenta);

    List<Venta> listarPorEstado(int estado);

    List<Venta> listarPorCliente(String dpiCliente);

    List<Venta> listarPorRangoFechas(Date fechaInicio, Date fechaFin);
}