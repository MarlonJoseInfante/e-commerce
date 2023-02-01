
package com.practice.ecommerce.service;

import com.practice.ecommerce.model.Usuario;
import org.springframework.stereotype.Service;
import com.practice.ecommerce.repository.IUsuarioRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UsuarioServiceImpl implements IUsuarioService{

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public Optional<Usuario> findById(Integer id) {
        
        return usuarioRepository.findById(id);
    }
    
    

    
}
