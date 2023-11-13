package pe.edu.uni.pag_inicio.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.uni.pag_inicio.controller.dto.*;
import pe.edu.uni.pag_inicio.repository.CreadorRepository;

import java.util.List;

@Service
public class CreadorService {

    @Autowired
    private CreadorRepository creadorRepository;
    public void solicitarCreacion(SolicitudCreacionDTO solicitudCreacionDTO) {
        creadorRepository.solicitarguardarProyecto(solicitudCreacionDTO);
    }
    public void solicitarmodificion(SolicitudModificacionDTO solicitudModificacionDTO) {
        creadorRepository.solicitarmodificarProyecto(solicitudModificacionDTO);
    }

    public List<IdproyectoDTO> obtenerProyectosActivosPorCreador(Long idCreador) {
        return creadorRepository.obtenerProyectosActivosPorCreador(idCreador);
    }

}
