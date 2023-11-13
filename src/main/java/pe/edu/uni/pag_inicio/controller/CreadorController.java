package pe.edu.uni.pag_inicio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.uni.pag_inicio.controller.dto.*;
import pe.edu.uni.pag_inicio.service.*;

import java.util.List;

@RestController
@RequestMapping("/solicitud")
public class CreadorController {

    private final CreadorService creadorService;

    @Autowired
    public CreadorController(CreadorService creadorService) {
        this.creadorService = creadorService;
    }

    // Metodos de Proyecto
    @PostMapping("/crear")
    public ResponseEntity<String> solicitarCreacion(@RequestBody SolicitudCreacionDTO solicitudCreacionDTO) {
        creadorService.solicitarCreacion(solicitudCreacionDTO);
        return ResponseEntity.ok("Proyecto creado correctamente. Pendiente de aprobación por parte del admin.");
    }
    @PostMapping("/modificar")
    public ResponseEntity<String> solicitarmodificion(@RequestBody SolicitudModificacionDTO solicitudModificacionDTO) {

        // Llamar al servicio para solicitar la modificación del proyecto
        creadorService.solicitarmodificion(solicitudModificacionDTO);

        return new ResponseEntity<>("Solicitud de modificación exitosa", HttpStatus.OK);
    }

    @GetMapping("/activos/{idCreador}")
    public ResponseEntity<List<IdproyectoDTO>> obtenerProyectosActivosPorCreador(@PathVariable Long idCreador) {
        List<IdproyectoDTO> proyectos = creadorService.obtenerProyectosActivosPorCreador(idCreador);
        if (proyectos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(proyectos, HttpStatus.OK);
    }

}
