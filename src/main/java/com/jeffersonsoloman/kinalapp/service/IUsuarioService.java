package com.jeffersonsoloman.kinalapp.service;

import com.jeffersonsoloman.kinalapp.entity.Usuario;
import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    List<Usuario> listarTodos();

    Usuario guardar(Usuario usuario);

    Optional<Usuario> buscarPorCodigo(Long codigoUsuario);

    Usuario actualizar(Long codigoUsuario, Usuario usuario);

    void eliminar(Long codigoUsuario);

    boolean existePorCodigo(Long codigoUsuario);

    List<Usuario> listarPorEstado(int estado);

    Optional<Usuario> buscarPorUsername(String username);

    Optional<Usuario> buscarPorEmail(String email);

    List<Usuario> listarPorRol(String rol);
}