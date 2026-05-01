package com.jeffersonsoloman.kinalapp.repository;

import com.jeffersonsoloman.kinalapp.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente,String> {
    // Método que busca clientes por su estado (1 = activo, 0 = inactivo)
    List<Cliente> findByEstado(int estado);
}
