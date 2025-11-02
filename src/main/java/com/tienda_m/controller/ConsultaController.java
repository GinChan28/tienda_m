/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda_m.controller;

import com.tienda_m.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigDecimal;

@Controller
@RequestMapping("/consultas")
public class ConsultaController {

    private final ProductoService productoService;

    // ✅ El constructor debe tener el mismo nombre que la clase
    public ConsultaController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var productos = productoService.getProductos(false);
        model.addAttribute("productos", productos);
        return "consultas/listado";
    }

    @PostMapping("/consultaDerivada")
    public String consultaDerivada(
            @RequestParam BigDecimal precioInf,
            @RequestParam BigDecimal precioSup,
            Model model) {

        var productos = productoService.consultaDerivada(precioInf, precioSup);
        model.addAttribute("productos", productos);
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "consultas/listado";
    }

    @PostMapping("/consultaJPQL")
    public String consultaJPQL(
            @RequestParam BigDecimal precioInf,
            @RequestParam BigDecimal precioSup,
            Model model) {

        var productos = productoService.consultaJPQL(precioInf, precioSup);
        model.addAttribute("productos", productos);
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "consultas/listado";
    }

    @PostMapping("/consultaSQL")
    public String consultaSQL(
            @RequestParam BigDecimal precioInf,
            @RequestParam BigDecimal precioSup,
            Model model) {

        var productos = productoService.consultaSQL(precioInf, precioSup);
        model.addAttribute("productos", productos);
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "consultas/listado";
    }
}
