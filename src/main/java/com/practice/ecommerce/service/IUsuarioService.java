
package com.practice.ecommerce.service;

import com.practice.ecommerce.model.Usuario;
import java.util.Optional;


public interface IUsuarioService {
    Optional<Usuario> findById(Integer id);
}
