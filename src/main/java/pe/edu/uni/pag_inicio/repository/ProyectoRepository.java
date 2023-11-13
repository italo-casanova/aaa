package pe.edu.uni.pag_inicio.repository;

import org.springframework.stereotype.Repository;
import pe.edu.uni.pag_inicio.controller.dto.ProyectoDTO;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProyectoRepository {
    List<ProyectoDTO> obtenerProyectosPorCategoria(String categoria);

    List<ProyectoDTO> findAllProyecto();

    void deleteById(int idProyecto);

    Optional<ProyectoDTO> findById(int id_proyecto);
}
