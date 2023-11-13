package pe.edu.uni.pag_inicio.repository;

import org.springframework.stereotype.Repository;
import pe.edu.uni.pag_inicio.controller.dto.OperacionesFinancierasDTO;

@Repository
public interface OperacionFinancieraRepository {

    int agregarDonacion(OperacionesFinancierasDTO dto);
}
