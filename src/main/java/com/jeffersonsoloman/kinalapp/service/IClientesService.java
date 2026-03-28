package com.jeffersonsoloman.kinalapp.service;

import com.jeffersonsoloman.kinalapp.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface IClientesService {
    //Interfaz. Es un contrato que dice QUE metodos debe tener
    //cualquier servicio de Cliente No tiene
    //implementacion, solo la definicion de los métodos

    //Metodo que devuelve una lista de todos los clientes
    List<Cliente> ListarTodos();
    //List<Cliente> lo que hace es devolver una lista
    // de objetos de la entidad Clientes

    //Metodo que guarda un Cliente en la BD
    Cliente guarda(Cliente cliente);
    //Parametro. Recibe un onjeto de tipo cliente en los datos a guardar

    //Optional - Contenedor que puede o no tener un valor
    //evitar error de NullPointarException
    Optional<Cliente> buscarPorDPI(String dpi);

    //Metodo que actualiza un Cliente
    Cliente actualizar(String  depi, Cliente cliente);
    //parametro dpi : DPI del CLiente a actualizar
    //Cliente cliente: Objeto con los datos nuevos
    //Retorna un objeto de tipo cliente ya actulizado

    //Metodo de tipo void para eliminar un cliente
    //void: No retorna ningun dato
    //Elimina un cliente por su DPI
    void eliminar(String dpi);

    //boolean retorna verdadero si existe o false si no existe
    boolean existePorDPI(String dpi);

    // --- Metodo para listar clientes por su estado (Activo/Inactivo) ---
    List<Cliente> listarPorEstado(int estado);

}
