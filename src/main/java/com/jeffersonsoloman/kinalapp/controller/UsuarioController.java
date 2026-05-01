package com.jeffersonsoloman.kinalapp.controller;

import com.jeffersonsoloman.kinalapp.entity.Usuario;
import com.jeffersonsoloman.kinalapp.service.IUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final IUsuarioService usuarioService;

    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{codigoUsuario}")
    public ResponseEntity<Usuario> buscarPorCodigo(@PathVariable Long codigoUsuario) {
        return usuarioService.buscarPorCodigo(codigoUsuario)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioService.guardar(usuario);
            return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{codigoUsuario}")
    public ResponseEntity<Void> eliminar(@PathVariable Long codigoUsuario) {
        try {
            if (!usuarioService.existePorCodigo(codigoUsuario)) {
                return ResponseEntity.notFound().build();
            }
            usuarioService.eliminar(codigoUsuario);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{codigoUsuario}")
    public ResponseEntity<?> actualizar(@PathVariable Long codigoUsuario, @RequestBody Usuario usuario) {
        try {
            if (!usuarioService.existePorCodigo(codigoUsuario)) {
                return ResponseEntity.notFound().build();
            }
            Usuario usuarioActualizado = usuarioService.actualizar(codigoUsuario, usuario);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Usuario>> listarActivos() {
        List<Usuario> activos = usuarioService.listarPorEstado(1);
        return ResponseEntity.ok(activos);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Usuario> buscarPorUsername(@PathVariable String username) {
        return usuarioService.buscarPorUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> buscarPorEmail(@PathVariable String email) {
        return usuarioService.buscarPorEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<Usuario>> listarPorRol(@PathVariable String rol) {
        List<Usuario> usuarios = usuarioService.listarPorRol(rol);
        return ResponseEntity.ok(usuarios);
    }

    // GET - Listar usuarios inactivos
    @GetMapping("/inactivos")
    public ResponseEntity<List<Usuario>> listarInactivos() {
        List<Usuario> inactivos = usuarioService.listarPorEstado(0);
        return ResponseEntity.ok(inactivos);
    }
}