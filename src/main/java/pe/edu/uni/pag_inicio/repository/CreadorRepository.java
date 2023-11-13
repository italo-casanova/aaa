package pe.edu.uni.pag_inicio.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.uni.pag_inicio.controller.dto.*;


import java.util.List;

@Repository
public interface CreadorRepository {

    void solicitarguardarProyecto(SolicitudCreacionDTO solicitudCreacionDTO);

    void solicitarmodificarProyecto(SolicitudModificacionDTO solicitudModificacionDTO);

    List<IdproyectoDTO> obtenerProyectosActivosPorCreador(Long idCreador);

}
