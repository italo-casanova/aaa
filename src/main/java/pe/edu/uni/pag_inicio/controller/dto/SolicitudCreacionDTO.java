package pe.edu.uni.pag_inicio.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudCreacionDTO {
    private String email;
    private String asunto;
    private String mensaje;
    // Constructor, getters, and setters
}
