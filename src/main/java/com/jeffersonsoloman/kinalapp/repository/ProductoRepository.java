package com.jeffersonsoloman.kinalapp.repository;

import com.jeffersonsoloman.kinalapp.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByEstado(int estado);

    List<Producto> findByStockLessThan(int stock);

    List<Producto> findByNombreProductoContainingIgnoreCase(String nombre);
}