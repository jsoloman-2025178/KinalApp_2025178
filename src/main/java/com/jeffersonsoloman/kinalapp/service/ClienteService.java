package com.jeffersonsoloman.kinalapp.service;

import com.jeffersonsoloman.kinalapp.entity.Cliente;
import com.jeffersonsoloman.kinalapp.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//Anotacion que registra un Bean como un Bean de Spring
//Que la clase contiene la logica del negocio
@Service
//Por defecto todos los metodos de esta clase seran transaccionales
//transaccionales
// Una transaccion es que puede o no ocurrir algo
@Transactional
public class ClienteService implements IClientesService {
    /*private: solo accesible dentro de la clase
        clienteRepository: Es el repositorio para acceder a la DB
        inyeccion de Dependencias Spring nos da el repositorio

     */
    private final ClienteRepository clienteRepository;

    /*
    *Constructor
    * Parametro: Spring pasa para el repositorio autamaticamente
     */

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /*
    *@Override: Indica que estamos implementando un metodo de la intefaz
     **/
    @Override
    /*
    *readOnly = true: Lo que hace es optimizar la consulta, no bloquea la BD
    */
    @Transactional(readOnly = true)
    public List<Cliente>ListarTodos(){
        return clienteRepository.findAll();
        /*
        *Este metodo llama al findAll del repositoio de JPA
        * este metodo hace las consultas de select * from y la tabla
        */
    }


    @Override
    public Cliente guarda(Cliente cliente) {
        /*
        * metodo de guardar crea un cliente
        * aca ees donde colocamos la logica del negocio antes de guardar
         */
        validarCliente(cliente);
        if (cliente.getEstado()==0){
            cliente.setEstado(1);
        }
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorDPI(String dpi) {
        //Busacar un cliente por DPI
        return clienteRepository.findById(dpi);
        //Opcional nos evita el NullPointerException
    }

    @Override
    public Cliente actualizar(String dpi, Cliente cliente) {
        //Actualiza un cliente existente
        if(!clienteRepository.existsById(dpi)){
            throw new RuntimeException("Cliente no se encontro con DPI" + dpi );
            //Si no existe se lanza una excepcion (error controlado)
        }
        /*
        *1. Asegurar que el DPI del objeto coincida con el de la URL
        * 2. Por seguridad usamos el DPI de la URL y no el que viene en JSON
        */
        cliente.setDPICliente(dpi);
        validarCliente(cliente);
        return clienteRepository.save(cliente);
    }

    @Override
    public void eliminar(String dpi) {
    //Elimanar un cliente
        if(!clienteRepository.existsById(dpi)){
            throw new RuntimeException("El cliente no se encontro con el DPI" + dpi);
        }
        clienteRepository.deleteById(dpi);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorDPI(String dpi) {
        //Verficar si existe el cliente
        return clienteRepository.existsById(dpi);
        //Retornar true o false
    }

    //Metodo privado(Solo puede utilizarse dentro de la clase)
    private void validarCliente(Cliente cliente) {
        /*
         *Validaciones del negocio: este medoto se hara privado por que
         * es algo interno del servicio
         */
        if (cliente.getDPICliente() == null || cliente.getDPICliente().trim().isEmpty()) {
            //Si el DPI es nu;; o esta vacio despues de quitar espacios
            //lanza una exception con un mensaje
            throw new IllegalArgumentException("El DPI es un dato obligatorio");
        }
        if (cliente.getNombreCliente() == null || cliente.getNombreCliente().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es un dato obligatorio");
        }
        if (cliente.getApellidoCliente() == null || cliente.getApellidoCliente().trim().isEmpty()) {
            throw new IllegalArgumentException("El Apellido es un dato obligatorio");
        }

    }
    @Transactional(readOnly = true)
    public List<Cliente> listarPorEstado(int estado) {
        // Llama al repositorio para filtrar clientes por estado (1 = activo, 0 = inactivo)
        return clienteRepository.findByEstado(estado);
        /*
         * Spring Data JPA genera automáticamente el SQL:
         * SELECT * FROM clientes WHERE estado = ?
         * */
    }
}
