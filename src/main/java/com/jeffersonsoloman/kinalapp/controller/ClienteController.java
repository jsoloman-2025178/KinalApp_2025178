package com.jeffersonsoloman.kinalapp.controller;

import com.jeffersonsoloman.kinalapp.entity.Cliente;
import com.jeffersonsoloman.kinalapp.service.IClientesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RestController = @Controller + @ResponseBody
@RequestMapping("/clientes")
//Todas las rutas en este controlador deben de empezar con /cliente
public class ClienteController {

    //Inyectamos el Servicion y no el Repositorio
    //El controller solo debe tener conexion con el Service
    private final IClientesService clienteService;

    //Como buena practica la Inyeccion de dependencias debe hacerse por el constructor
    public ClienteController(IClientesService clienteService) {
        this.clienteService = clienteService;
    }

    //Responde apeticiones GET
    @GetMapping
    //ResponseEntity nos permite controlar el codigo HTTP y el cuerpo
    public ResponseEntity<List<Cliente>> listar() {
        List<Cliente> clientes = clienteService.ListarTodos();
        //delegamos al servicio
        return ResponseEntity.ok(clientes);
        //200 OK con la lista de clientes
    }

    //{dpi} es una variable de ruta(valor a buscar)
    @GetMapping("/{dpi}")
    public ResponseEntity<Cliente> buscarPorDPI(@PathVariable String dpi) {
        //@PahVariable Toma el valor de la URL y lo asigna al dpi
        return clienteService.buscarPorDPI(dpi)
                //Si el opcional tiene valor devuelve 200 OK con el Cliente
                .map(ResponseEntity::ok)
                //Si opcional esta vacio devuelve 404 NOT FUND
                .orElse(ResponseEntity.notFound().build());
    }

    //Post crear un nuevo cliente
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Cliente cliente) {
        //@RequestBody: Toma el JSON del cuerpo y lo convierte a unobjeto de tipo Cliente
        //<?> significa "tipo generico puede ser un CLiente o un String"
        try {
            Cliente nuevoCliente = clienteService.guarda(cliente);
            //Intentamos guardar el cliente pero puede lanzar un excepcion
            return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
            //201 created(mucho mas especifico que el 2200 para la cracion de un cliente)
        } catch (IllegalArgumentException e) {
            //Si hay error de validacion
            return ResponseEntity.badRequest().body(e.getMessage());
            //400 BAD REQUES con el mensaje de error
        }
    }

    //DELETE elimina un cliente
    @DeleteMapping("/{dpi}")
    public ResponseEntity<Void> eliminar(@PathVariable String dpi) {
        //ResponseEntity no devuelve cuerpo en la respuesta
        try {
            if (!clienteService.existePorDPI(dpi)) {
                return ResponseEntity.notFound().build();
                //404 si no existe
            }
            clienteService.eliminar(dpi);
            return ResponseEntity.noContent().build();
            //204 No Content (Se ejecuto correctamente y no devulve cuepro)
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
            //404
        }
    }

    //Actualiza un cliente
    @PutMapping("/{dpi}")
    public ResponseEntity<?> actualizar(@PathVariable String dpi, @RequestBody Cliente cliente) {
        try {
            if(!clienteService.existePorDPI(dpi)){
                //verficar si existe antes de poder actualizar
                //404 NOT FOUND
                return ResponseEntity.notFound().build();
            }
            //Actualizar el cliente pero esto puede lanzar una excepcion
            Cliente clienteActualizado = clienteService.actualizar(dpi, cliente);
            return ResponseEntity.ok(clienteActualizado);
            //200 ok con el cliente ya actualizado
        } catch (IllegalArgumentException e) {
            //Error cuando los dados son incorrectos
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            //Posiblemente cualquier otro error como: cliente no encontrado, etc.
            //404 NOT FOUND
            return ResponseEntity.notFound().build();

        }
    }

    /// GET listar solo clientes activos
    @GetMapping("/activos")
    public ResponseEntity<List<Cliente>> listarActivos() {
        // CAMBIO: Usamos 'clienteService' (la variable inyectada), no la Clase.
        List<Cliente> activos = clienteService.listarPorEstado(1);

        // Si la lista está vacía, podemos devolver 204 o simplemente la lista vacía con 200
        return ResponseEntity.ok(activos);
        // 200 OK con la lista de clientes que tienen estado activo
    }




}