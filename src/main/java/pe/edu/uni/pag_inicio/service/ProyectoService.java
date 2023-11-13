package pe.edu.uni.pag_inicio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.uni.pag_inicio.controller.dto.ProyectoDTO;
import pe.edu.uni.pag_inicio.repository.ProyectoRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;

    @Autowired
    public ProyectoService(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
    }

    public List<ProyectoDTO> obtenerProyectosPorCategoria(String categoria) {
        return proyectoRepository.obtenerProyectosPorCategoria(categoria);
    }

    public List<ProyectoDTO> obtenerTodosLosProyectos() {
        return proyectoRepository.findAllProyecto();
    }

    public void eliminarProyectoPorId(int idProyecto) {
        proyectoRepository.deleteById(idProyecto);
    }

    public Optional<ProyectoDTO> obtenerProyectoPorId(int idProyecto) {
        return proyectoRepository.findById(idProyecto);
    }

    // Puedes agregar más métodos según las necesidades de tu aplicación

}
