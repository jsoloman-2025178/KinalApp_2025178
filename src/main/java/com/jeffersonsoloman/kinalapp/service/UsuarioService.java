package com.jeffersonsoloman.kinalapp.service;

import com.jeffersonsoloman.kinalapp.entity.Usuario;
import com.jeffersonsoloman.kinalapp.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorCodigo(Long codigoUsuario) {
        return usuarioRepository.findById(codigoUsuario);
    }

    @Override
    public Usuario actualizar(Long codigoUsuario, Usuario usuario) {
        usuario.setCodigoUsuario(codigoUsuario);
        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminar(Long codigoUsuario) {
        usuarioRepository.deleteById(codigoUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorCodigo(Long codigoUsuario) {
        return usuarioRepository.existsById(codigoUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarPorEstado(int estado) {
        return usuarioRepository.findByEstado(estado);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarPorRol(String rol) {
        return usuarioRepository.findByRol(rol);
    }
}