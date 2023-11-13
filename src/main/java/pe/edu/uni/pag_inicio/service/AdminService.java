package pe.edu.uni.pag_inicio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.uni.pag_inicio.controller.dto.*;
import pe.edu.uni.pag_inicio.repository.AdminRepository;

import java.util.List;


@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public List<ProyectoDTO> findAllProyectos() {
        return adminRepository.findAllProyectos();
    }
    public List<UsuarioDTO> findAllUsuarios() {
        return adminRepository.findAllUsuarios();
    }
    public List<CreadorDTO> findAllCreadores() {
        return adminRepository.findAllCreadores();
    }
    public List<MetodoPagoDTO> findAllMetodosPago() {
        return adminRepository.findAllMetodosPago();
    }

    public ProyectoDTO crearProyecto(ProyectoDTO dto) {
        return adminRepository.Crearproyecto(dto);
    }
    public ProyectoDTO actualizarProyecto(int idProyecto, ProyectoDTO proyectoDTO) {
        return adminRepository.actualizarProyecto(idProyecto, proyectoDTO);
    }
    public String borrarProyecto(int id) {
        return adminRepository.borrarProyecto(id);
    }

    public List<OperacionesFinancierasDTO> findAllOperacionesFinancieras() {
        return adminRepository.findAllOperacionesFinancieras();
    }
    public List<RecompensaDTO> findAllRecompensas() {
        return adminRepository.findAllRecompensas();
    }

    public MensajeAdminDTO responderContacto(int idContacto,MensajeAdminDTO mensajeAdminDTO) {
       return adminRepository.responderContacto(idContacto,mensajeAdminDTO);
    }
    public RecompensaDTO crearRecompensa(RecompensaDTO recompensaDto, int idProyecto){
        return  adminRepository.crearRecompensa(recompensaDto,idProyecto);
    }
    public Mensajedto borrarRecompensa(int idRecompensa){
        return adminRepository.borrarRecompensa(idRecompensa);
    }
}
