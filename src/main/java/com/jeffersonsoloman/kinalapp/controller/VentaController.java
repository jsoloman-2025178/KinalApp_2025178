package com.jeffersonsoloman.kinalapp.controller;

import com.jeffersonsoloman.kinalapp.service.IVentasService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    private final IVentasService ventaService;

    public VentaController(IVentasService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping
    public String listar(Model model) {
        System.out.println("=== Cargando ventas ===");
        var ventas = ventaService.listarTodos();
        System.out.println("Número de ventas encontradas: " + ventas.size());
        model.addAttribute("ventas", ventas);
        return "ventas/listar";
    }
}