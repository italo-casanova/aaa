package pe.edu.uni.pag_inicio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.uni.pag_inicio.controller.dto.Mensajedto;
import pe.edu.uni.pag_inicio.controller.dto.UsuarioDTO;
import pe.edu.uni.pag_inicio.service.UsuariosService;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
class UsuariosController {

    private final UsuariosService  usuariosService;
    @Autowired
    public UsuariosController(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    @GetMapping("/{userId}")
    public UsuarioDTO getUsuarioById(@PathVariable Long userId) {
        return usuariosService.getUserById(Math.toIntExact(userId));
    }

    @GetMapping("/email/{email}")
    public UsuarioDTO getUsuarioByEmail(@PathVariable String email) {
        return usuariosService.getUserByEmail(email);
    }

    @GetMapping("/all")
    public List<UsuarioDTO> getAllUsuarios() {
        return usuariosService.getAllUsers();
    }

    @PostMapping("/create")
    public UsuarioDTO createUsuario(@RequestBody UsuarioDTO usuario) {
        return usuariosService.createUser(usuario);
    }

    @PutMapping("/update/{idUsuario}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(
            @PathVariable int idUsuario,
            @RequestBody UsuarioDTO usuarioDTO) {

        UsuarioDTO usuarioActualizado = usuariosService.updateUser(idUsuario,usuarioDTO);

        if (usuarioActualizado != null) {
            return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Mensajedto> deleteUsuario(@PathVariable int userId) {
        Mensajedto resultado =usuariosService.deleteUser(userId);
        if (resultado.getCodigo() == 1) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
