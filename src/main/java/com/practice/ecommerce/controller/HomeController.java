

package com.practice.ecommerce.controller;

import com.practice.ecommerce.model.DetalleOrden;
import com.practice.ecommerce.model.Orden;
import com.practice.ecommerce.model.Producto;
import com.practice.ecommerce.service.ProductoService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class HomeController {
    
    private final Logger LOG= LoggerFactory.getLogger(HomeController.class);
    
    @Autowired
    private ProductoService productoService;

    //para almacenar los detalles de la orden
    List<DetalleOrden> detalles= new ArrayList();
    
    //datos de la orden
    Orden orden= new Orden();
    
    @GetMapping("")
    public String home(Model model){
        model.addAttribute("productos", productoService.findAll());
        return "/usuario/home";
    }
    
    @GetMapping("/productohome/{id}")
    public String productoHome(@PathVariable Integer id, Model model) throws Exception{
        
        LOG.info("Id producto enviado como parametro {}", id);
        Producto producto= productoService.findById(id);
        model.addAttribute("producto", producto);
        return "/usuario/productohome";
    }
    
    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad) throws Exception{
        DetalleOrden detalleOrden= new DetalleOrden();
        Producto producto= new Producto();
        double sumaTotal=0;
        producto= productoService.findById(id);
        LOG.info("Producto añadido {}", producto);
        LOG.info("Cantidad {}", cantidad);
        
        return "/usuario/carrito";
    }
}
