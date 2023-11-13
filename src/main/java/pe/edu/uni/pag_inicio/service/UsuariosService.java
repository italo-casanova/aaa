package pe.edu.uni.pag_inicio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.uni.pag_inicio.controller.dto.Mensajedto;
import pe.edu.uni.pag_inicio.controller.dto.UsuarioDTO;
import pe.edu.uni.pag_inicio.repository.UsuariosRepository;


import java.util.List;

@Service
public class UsuariosService{
    @Autowired
    private UsuariosRepository usuariosRepository;

    public UsuarioDTO createUser(UsuarioDTO usuario) {

        return usuariosRepository.save(usuario);
    }

    public UsuarioDTO updateUser(int idUsuario, UsuarioDTO usuarioDTO) {

        return usuariosRepository.update(idUsuario,usuarioDTO);
    }
    public UsuarioDTO getUserById(int userId) {
        return usuariosRepository.findById(userId);
    }

    public UsuarioDTO getUserByEmail(String email) {
        return usuariosRepository.findByEmail(email);
    }


    public List<UsuarioDTO> getAllUsers() {

        return usuariosRepository.findAll();
    }

    public Mensajedto deleteUser(int userId) {
        // Agregar lógica para eliminar un usuario por su ID desde el repositorio
        return usuariosRepository.deleteById(userId);

    }
}
