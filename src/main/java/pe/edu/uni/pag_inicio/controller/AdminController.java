package pe.edu.uni.pag_inicio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.uni.pag_inicio.controller.dto.*;
import pe.edu.uni.pag_inicio.service.AdminService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/proyectos")
    public List<ProyectoDTO> getAllProyectos() {
        return adminService.findAllProyectos();
    }

    @GetMapping("/usuarios")
    public List<UsuarioDTO> getAllUsuarios() {
        return adminService.findAllUsuarios();
    }

    @GetMapping("/creadores")
    public List<CreadorDTO> getAllCreadores() {
        return adminService.findAllCreadores();
    }

    @GetMapping("/metodospago")
    public List<MetodoPagoDTO> getAllMetodosPago() {
        return adminService.findAllMetodosPago();
    }

    @PostMapping("/crearproyecto")
    public ProyectoDTO crearProyecto(@RequestBody ProyectoDTO dto) {
        return adminService.crearProyecto(dto);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ProyectoDTO> actualizarProyecto(@PathVariable int id, @RequestBody ProyectoDTO proyectoDTO) {

        ProyectoDTO proyectoActualizado = adminService.actualizarProyecto(id, proyectoDTO);
        return new ResponseEntity<>(proyectoActualizado, HttpStatus.OK);

    }

    @DeleteMapping("/borrarproyecto/{id}")
    public ResponseEntity<String> borrarProyecto(@PathVariable int id) {
        String mensaje = adminService.borrarProyecto(id);
        return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }

    @GetMapping("/operacionesfinancieras")
    public List<OperacionesFinancierasDTO> getAllOperacionesFinancieras() {
        return adminService.findAllOperacionesFinancieras();
    }

    @GetMapping("/recompensas")
    public List<RecompensaDTO> getAllRecompensas() {
        return adminService.findAllRecompensas();
    }

    @PutMapping("/responderContacto/{idContacto}")
    public ResponseEntity<String> responderContacto(@PathVariable int idContacto, @RequestBody MensajeAdminDTO mensajeAdminDTO) {
        try {
            MensajeAdminDTO respuesta = adminService.responderContacto(idContacto, mensajeAdminDTO);
            if (respuesta != null) {
                return new ResponseEntity<>("Respuesta enviada exitosamente", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No se encontró un contacto con ID " + idContacto, HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            // Manejo de errores, puedes personalizar según tus necesidades
            return new ResponseEntity<>("Error al procesar la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/insertarRecompensa/{idProyecto}")
    public Mensajedto crearRecompensa(@RequestBody RecompensaDTO dto, @PathVariable int idProyecto) {
        Mensajedto mensaje;
        try {
            dto = adminService.crearRecompensa(dto, idProyecto);
            mensaje = new Mensajedto(1, "Recompensa creada correctamente: " + dto.getTipo_nivel());
        } catch (RuntimeException e) {
            mensaje = new Mensajedto(-1, "Error al crear la recompensa: " + e.getMessage());
        }
        return mensaje;
    }
    @DeleteMapping("/borrarRecompensa/{idRecompensa}")
    public ResponseEntity<Mensajedto> borrarRecompensa(@PathVariable int idRecompensa) {
        Mensajedto resultado = adminService.borrarRecompensa(idRecompensa);

        if (resultado.getCodigo() == 1) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
