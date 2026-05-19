package com.jeffersonsoloman.kinalapp.controller;

import com.jeffersonsoloman.kinalapp.entity.Producto;
import com.jeffersonsoloman.kinalapp.service.IProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final IProductoService productoService;

    public ProductoController(IProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        return "productos/listar";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("titulo", "Nuevo Producto");
        return "productos/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Producto producto) {
        productoService.guardar(producto);
        return "redirect:/productos";
    }

    @GetMapping("/editar/{codigo}")
    public String mostrarFormularioEditar(@PathVariable Long codigo, Model model) {
        Producto producto = productoService.buscarPorCodigo(codigo).orElse(null);
        model.addAttribute("producto", producto);
        model.addAttribute("titulo", "Editar Producto");
        return "productos/form";
    }

    @PostMapping("/actualizar/{codigo}")
    public String actualizar(@PathVariable Long codigo, @ModelAttribute Producto producto) {
        productoService.actualizar(codigo, producto);
        return "redirect:/productos";
    }

    @GetMapping("/eliminar/{codigo}")
    public String eliminar(@PathVariable Long codigo) {
        productoService.eliminar(codigo);
        return "redirect:/productos";
    }
}