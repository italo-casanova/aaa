package pe.edu.uni.pag_inicio.repository;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.uni.pag_inicio.controller.dto.Mensajedto;
import pe.edu.uni.pag_inicio.controller.dto.UsuarioDTO;

import java.util.List;

@Repository
public interface UsuariosRepository {

    UsuarioDTO findByEmail(String email);


    UsuarioDTO findById(long userId);

    UsuarioDTO save(UsuarioDTO usuario);

    UsuarioDTO update(int idUsuario, UsuarioDTO usuarioDTO);

    Mensajedto deleteById(int userId);

    List<UsuarioDTO> findAll();
}
