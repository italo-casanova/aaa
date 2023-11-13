package pe.edu.uni.pag_inicio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.uni.pag_inicio.controller.dto.MetodoPagoDto;
import pe.edu.uni.pag_inicio.repository.MetodoPagoRepository;

@Service
public class MetodoPagoService {

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;


    public int agregarMetodoPago(MetodoPagoDto metodoPagoDto) {
        UsuariosService service = new UsuariosService();
        return service.getUserById(metodoPagoDto.getId_usuario()) != null?
                metodoPagoRepository.save(metodoPagoDto):0;
    }
}
