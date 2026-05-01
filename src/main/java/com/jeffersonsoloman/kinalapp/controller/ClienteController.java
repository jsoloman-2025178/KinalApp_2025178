package com.jeffersonsoloman.kinalapp.controller;

import com.jeffersonsoloman.kinalapp.entity.Cliente;
import com.jeffersonsoloman.kinalapp.service.IClientesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final IClientesService clienteService;

    public ClienteController(IClientesService clienteService) {
        this.clienteService = clienteService;
    }

    // Listar todos los clientes
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteService.ListarTodos());
        return "clientes/listar";
    }

    // Formulario para nuevo cliente
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("titulo", "Nuevo Cliente");
        return "clientes/form";
    }

    // Guardar nuevo cliente
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cliente cliente) {
        clienteService.guarda(cliente);
        return "redirect:/clientes";
    }

    // Formulario para editar cliente
    @GetMapping("/editar/{dpi}")
    public String mostrarFormularioEditar(@PathVariable String dpi, Model model) {
        Cliente cliente = clienteService.buscarPorDPI(dpi).orElse(null);
        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Editar Cliente");
        return "clientes/form";
    }

    // Actualizar cliente
    @PostMapping("/actualizar/{dpi}")
    public String actualizar(@PathVariable String dpi, @ModelAttribute Cliente cliente) {
        clienteService.actualizar(dpi, cliente);
        return "redirect:/clientes";
    }

    // Eliminar cliente
    @GetMapping("/eliminar/{dpi}")
    public String eliminar(@PathVariable String dpi) {
        clienteService.eliminar(dpi);
        return "redirect:/clientes";
    }
}