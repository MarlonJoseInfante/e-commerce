
package com.practice.ecommerce.controller;

import com.practice.ecommerce.model.Producto;
import com.practice.ecommerce.service.ProductoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private ProductoService productoService;
    
    @GetMapping("")
    public String home(Model model){
        List<Producto> productos= productoService.findAll();
        model.addAttribute("productos", productos);
        return "/administrador/home";
    }
}
