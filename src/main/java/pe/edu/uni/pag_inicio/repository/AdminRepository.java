package pe.edu.uni.pag_inicio.repository;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.uni.pag_inicio.controller.dto.*;

import java.util.List;

public interface AdminRepository {
    List<ProyectoDTO> findAllProyectos();
    List<UsuarioDTO> findAllUsuarios();
    List<CreadorDTO> findAllCreadores();
    List<MetodoPagoDTO> findAllMetodosPago();

    ProyectoDTO Crearproyecto(ProyectoDTO dto);
    ProyectoDTO actualizarProyecto(int idProyecto, ProyectoDTO proyectoDTO);

    String borrarProyecto(int id);

    List<OperacionesFinancierasDTO> findAllOperacionesFinancieras();
    List<RecompensaDTO> findAllRecompensas();
    MensajeAdminDTO responderContacto(int idcreador, MensajeAdminDTO mensajeAdminDTO);

    RecompensaDTO crearRecompensa(RecompensaDTO recompensaDto, int idProyecto);

    Mensajedto borrarRecompensa(int idRecompensa);
}
