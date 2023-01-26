package com.practice.ecommerce.controller;

import com.practice.ecommerce.model.Producto;
import com.practice.ecommerce.model.Usuario;
import com.practice.ecommerce.service.ProductoService;
import com.practice.ecommerce.service.UploadFileService;
import java.io.IOException;
import java.util.logging.Level;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UploadFileService upload;

    @GetMapping("")
    public String show(Model model) {
        model.addAttribute("productos", productoService.findAll());
        return "/productos/show";
    }

    @GetMapping("/create")
    public String create() {
        return "/productos/create";
    }

    @PostMapping("/save")
    public String save(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
        LOGGER.info("Este es el producto{} ", producto);
        Usuario u = new Usuario(1, "", "", "", "", "", "", "", null, null);
        producto.setUsuario(u);
        //imagen
        if (producto.getId() == null) {// cuando se crea el producto
            String nombreImagen = upload.saveImage(file);
            producto.setImagen(nombreImagen);
        }
        productoService.save(producto);
        return "redirect:/productos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        try {
            Producto producto = productoService.findById(id);
            LOGGER.info("Producto buscado: {} ", producto);
            model.addAttribute("producto", producto);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return "/productos/edit";
    }

    @PostMapping("/update")
    public String update(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
        Producto p = productoService.get(producto.getId()).get();
        
        if (file.isEmpty()) {
            producto.setImagen(p.getImagen());
        } else {
            //cuando se modifica la imagen
            //eliminar cuando no sea la imagen por defecto
            if (!p.getImagen().equals("default.jpg")){
                upload.delete(p.getImagen());
            }
            String imagen = upload.saveImage(file);
            producto.setImagen(imagen);
        }
        producto.setUsuario(p.getUsuario());
        productoService.update(producto);
        return "redirect:/productos";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Producto producto = new Producto();
        producto = productoService.get(id).get();
        //eliminar cuando no sea la imagen por defecto
        if (!producto.getImagen().equals("default.jpg")) {
            upload.delete(producto.getImagen());
        }
        productoService.delete(id);
        return "redirect:/productos";
    }
}
